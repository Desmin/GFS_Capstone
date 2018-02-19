#!/bin/bash
# scrip needs to check that both the image and option files exist
# if either does not, send an error back to the calling program
# if they both exist, get the option flags and use tesseract on the image
if [ -e img.tiff ] || [ -e img.tif ];
then
    # image exists
    echo "IMAGE PROCESSING: img.tiff found."
else
    # image does not exist
    echo "$(date +"%T") ERROR: img.tiff not found." > logfile.log
    exit 1
fi

if [ -e options.txt ]
then
    # options file exists
    echo "IMAGE PROCESSING: options.txt found."
else
    # options file does not exist
    echo "$(date +"%T") ERROR: options.txt not found." > logfile.log
    exit 1
fi

# now process the image using tesseract OCR
tesseract out.tiff output.txt 
