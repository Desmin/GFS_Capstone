package gui;

/**
 * A class that represents the various options that exist for the Tesseract.
 *
 * @author Desmin Little
 */
public class TesseractOptions {

    /**
     * The various options, automatically set to their defaults.
     */
    private Language language = Language.ENGLISH;
    private PageSegmentation pageSegmentation = PageSegmentation.AUTOMATED;
    private EngineMode engineMode = EngineMode.BOTH;

    public TesseractOptions(TesseractOptions options) {
        this.language = options.getLanguage();
        this.pageSegmentation = options.getPageSegmentation();
        this.engineMode = options.getEngineMode();
    }

    public TesseractOptions() { }

    /**
     * A utility method that formats enum ordinal names. THIS_IS_A_STRING becomes This Is A String.
     *
     * @param string The string to be formatted.
     * @return A formatted string.
     */
    public static String formatEnumName(String string) {
        string = string.toLowerCase();
        for (int i = 0; i < string.length(); i++) {
            if (i == 0) {
                string = String.format("%s%s", Character.toUpperCase(string.charAt(0)),
                        string.substring(1));
            }
            if (!Character.isLetterOrDigit(string.charAt(i))) {
                if (i + 1 < string.length()) {
                    string = String.format("%s%s%s", string.subSequence(0, i + 1),
                            Character.toUpperCase(string.charAt(i + 1)),
                            string.substring(i + 2));
                }
            }
        }
        return string.replace("_", " ").trim();
    }

    public Language getLanguage() {
        return language;
    }

    public void updateOptions(Language language, PageSegmentation pageSegmentation, EngineMode engineMode) {
        this.language = language;
        this.pageSegmentation = pageSegmentation;
        this.engineMode = engineMode;
    }

    public PageSegmentation getPageSegmentation() {
        return pageSegmentation;
    }

    public EngineMode getEngineMode() {
        return engineMode;
    }

    /**
     * Languages made available for use with the Tesseract. Shortened for use in this project.
     *
     * @author Desmin Little
     */
    public enum Language {

        ARABIC("ara"),
        SIMPLIFIED_CHINESE("chi_sim"),
        TRADITIONAL_CHINESE("chi_tra"),
        DANISH("dan"),
        GERMAN("deu"),
        GREEK("ell"),
        ENGLISH("eng"),
        OLD_ENGLISH("enm"),
        FINNISH("fin"),
        FRENCH("fra"),
        ITALIAN("ita"),
        JAPANESE("jpn"),
        KOREAN("kor"),
        DUTCH("nld"),
        RUSSIAN("rus"),
        SPANISH("spa");

        private String tesseractString;

        Language(String tesseractString) {
            this.tesseractString = tesseractString;
        }

        public String getTesseractString() {
            return tesseractString;
        }

        @Override
        public String toString() {
            return TesseractOptions.formatEnumName(this.name());
        }
    }


    /**
     * Contains all the available page segmentation modes for the Tesseract.
     *
     * @author Desmin Little
     */
    public enum PageSegmentation {
        OSD("Orientation and script detection (OSD) only."),
        AUTOMATED_OSD("Automatic page segmentation with OSD."),
        AUTOMATED("Automatic page segmentation, but no OSD or OCR."),
        AUTOMATED_OCR("Fully automatic page segmentation, but no OSD. (Default)"),
        COLUMN("Assume a single column of text of variable sizes."),
        BLOCK_VERTICLE("Assume a single uniform block of vertically aligned text."),
        BLOCK("Assume a single uniform block of text."),
        SINGLE_LINE("Treat the image as a single text line."),
        SINGLE_WORD("Treat the image as a single word."),
        SINGLE_WORD_CIRCLE("Treat the image as a single word in a circle."),
        SINGLE_CHARACTER("Treat the image as a single character."),
        SPARSE_TEXT("Sparse text. Find as much text as possible in no particular order."),
        SPARSE_TEXT_OSD("Sparse text with OSD."),
        RAW_LINE("Raw line. Treat the image as a single text line, bypassing hacks that are Tesseract-specific.");

        private String description;

        PageSegmentation(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return TesseractOptions.formatEnumName(this.name());
        }
    }

    /**
     * Engine modes available for the Tesseract.
     *
     * @author Desmin Little
     */
    public enum EngineMode {
        TESSERACT("Original Tesseract only."),
        LSTM("Neural nets LSTM only."),
        BOTH("Tesseract + LSTM."),
        DEFAULT("Default, based on what is available.");

        private String description;

        EngineMode(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return TesseractOptions.formatEnumName(this.name());
        }
    }
}
