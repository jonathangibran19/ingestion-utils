package com.jonathan.delimitadores;

import com.paquete.commons;

public class Parser {

	public static final String SLASH_REGEX_CHAR = "//";
    private final static Logger LOGGER = Logger.getLogger(BDPFilesUtils.class.getName());


	/**
     * Normalize CSV or TXT file type, to ensure that the data is reliable and
     * that each line represents correct information, prevents adding blank
     * lines that could bring the file, or rows without data, that contain only
     * the separators and add sotore day column.
     *
     * @param file to normalize
     * @param delimiterType Delimiter with which the file is separated
     * @return clean file text ready to use
     * @throws FileNotFoundException
     * @throws com.mx.bdp.commons.exceptions.CommonsException
     */
    public static StringBuilder normalizeToValidContentFile(File file, DelimiterEnum delimiterType) throws FileNotFoundException, CommonsException {

        LOGGER.log(Level.INFO, "input file: {0}", file.getAbsolutePath());

        LOGGER.log(Level.INFO, "Normalizing ... ");

        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream);

        StringBuilder data = new StringBuilder();

        boolean rowIndexFirst = true;

        LOGGER.log(Level.INFO, "Reading file: {0}", file.getName());

        int numberLine = 0;
        StringBuilder line = new StringBuilder();
        String storeDay = getStoreDay();
        
        while (scanner.hasNext()) {

            line.delete(0, line.length());
            line.append(scanner.nextLine());

            if (rowIndexFirst) {
                /*asegura que inicialmente tenga un buen header*/
                if (isValidRow(line.toString(), delimiterType)) {

                    /*pendiente usar el metodo de limpiar headers*/
                    data.append(cleanHeadersAndFilesName(line.toString()));
                    data.append(delimiterType.getDelimiter());
                    data.append("STORE_DAY");
 
                    numberLine++;
                    rowIndexFirst = false;

                } else {
                    throw new CommonsException("Invalid header, not contains name columns.");
                }

            } else {

                if (isValidRow(line.toString(), delimiterType)) {
                    data.append("\n");
                    data.append(normalizerToASCII(line.toString()));
                    data.append(delimiterType.getDelimiter());
                    data.append(storeDay);
                    numberLine++;
                }
            }

        }
        LOGGER.log(Level.INFO, "Valid read lines: {0}", numberLine);
        LOGGER.log(Level.INFO, "Closing stream...");
        scanner.close();
        LOGGER.log(Level.INFO, "Stream closed");
        return data;
    }


    /**
     * Get the current system date with the format yyy-MM-dd
     *
     * @return Store day
     */
    public static String getStoreDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormat = sdf.format(new Date());

        return fechaFormat;
    }
}