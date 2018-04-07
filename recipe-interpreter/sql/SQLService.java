package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to handle sql connections and queries for our test database.
 *
 * @author Desmin
 */
public class SQLService {

    /**
     * Here's an example:
     *
     *    SQLService sqlService = new SQLService();
     *    sqlService.addRecipe("Chocolate Chip Cookies", 16, 1, "cookie", "Mix everything together and bake at 350 degree for 14 minutes.");
     *    sqlService.addIngredient("Chocolate Chip Cookies", "Flour", "cups", 3);
     *    sqlService.addIngredient("Chocolate Chip Cookies", "Sugar", "cups", 2);
     *    sqlService.addIngredient("Chocolate Chip Cookies", "Eggs", "large", 2);
     */

    private static final String DATABASE_ADDRESS = "jdbc:mysql://localhost:3306/recipes", USERNAME = "gfs_capstone", PASSWORD = "gfs_capston";
    private static final String GET_NEXT_RECIPE_ID = "select max(recipe_id)+1 as id from recipe", GET_RECIPE_BY_TITLE = "select recipe_id as r from recipe where title ='";
    private Connection databaseConnection;
    private Logger logger = Logger.getLogger("SQLService");

    public SQLService() {
        startService();
    }

    private boolean startService() {
        if (Objects.nonNull(databaseConnection))
            return true;

        logger.info("Starting SQL service...");
        logger.info("Creating database connection...");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseConnection = DriverManager.getConnection(DATABASE_ADDRESS, USERNAME, PASSWORD);
            logger.info("Connected to database!");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Issue connecting to database!", e);
            return false;
        }
        return true;
    }

    public boolean addRecipe(String title, int yield, int servingSize, String servingSizeType, String instructions) {
        try {
            ResultSet rs = executeSQLStatement(SQLService.GET_NEXT_RECIPE_ID);
            while (rs.next()) {
                String recipe = "insert into recipe values (" + rs.getInt("id") + ", '" + title + "', " + yield + ", " + servingSize + ", '" + servingSizeType + "', '" + instructions + "')";
                updateDatabase(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addIngredient(int recipeId, String name, String unitOfMeasure, int quantity) {
        try {
            updateDatabase("insert into ingredient values (" + recipeId + ", '" + name + "', '" + unitOfMeasure + "', " + quantity + ")");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addIngredient(String recipeTitle, String name, String unitOfMeasure, int quantity) {
        try {
            ResultSet rs = executeSQLStatement(GET_RECIPE_BY_TITLE + recipeTitle + "'");
            while (rs.next()) {
                addIngredient(rs.getInt("r"), name, unitOfMeasure, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private ResultSet executeSQLStatement(String query) throws SQLException {
        if (Objects.isNull(databaseConnection))
            if (!startService())
                return null;
        return databaseConnection.createStatement().executeQuery(query);
    }

    private void updateDatabase(String query) throws SQLException {
        if (Objects.isNull(databaseConnection))
            if (!startService())
                return;
        databaseConnection.createStatement().executeUpdate(query);
    }
}
