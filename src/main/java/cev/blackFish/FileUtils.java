package cev.blackFish;


/**
 *
 */
public class FileUtils {

    /**
     * Determines the size of a file on the local system
     *
     * @param filename: the file passed in
     * @return size: the byte size of the file
     */
    public static int getFileBytes(String fileName, String applicationLocation) {

        String fullFilePath = "";
        int size = -1;
        fullFilePath = applicationLocation + fileName;

        try {
            java.io.File myFile = new java.io.File(fullFilePath);
            size = (int) myFile.length();
        } catch (Exception ex) {
            System.out.println("Exception caught attempting to determine the size of " + fileName + ": " + ex.getMessage());
        }

        return size;
    }

    /**
     * Determines the units used to measure the file size
     *
     * @param size: the byte size of the file passed in
     * @return filesize:  the size of the file in bytes or kb or mb
     */
    public static String getFileSize(int size) {

        String filesize = "";
        int counter = 0;

        while (size > 1000) {
            size = size / 1024;
            counter++;
        }

        filesize = "" + size + "";
        if (counter == 0) {
            filesize += " bytes";
        } else if (counter == 1) {
            filesize += " kb";
        } else if (counter == 2) {
            filesize += " mb";
        } else {
            filesize = "More than a Gigabyte";
        }
        return (filesize);
    }

    /**
     * Used to clean up file names.  Makes the name lower case and strips problematic characters
     *
     * @param filename: A (String) name of a file
     * @return filename: The "stripped" name
     */
    public static String stripCharacters(String filename) {

        filename = filename.toLowerCase();
        filename = filename.replaceAll("[^\\d\\w\\.\\-]", "");
        return filename;
    }

    /**
     * Attempts to create a directory
     *
     * @param directoryName: the (String) directory path
     * @return: makeDirectory (boolean)
     */
    public static boolean MakeDirectory(String directoryName) {

        java.io.File f = new java.io.File(directoryName);
        if (!f.mkdir()) {
            System.out.println("The directory at " + f.toString() + " cannot be created");
        }
        if (!f.canWrite()) {
            System.out.println("The file at " + f.toString() + " is write protected");
        }
        boolean makeDirectory = f.mkdir();
        return makeDirectory;
    }

    /**
     * Attempts to delete a directory or file
     *
     * @param directory
     * @return
     */
    public static boolean deleteDirectory(String directory) {

        boolean deletion = false;

        if (directory != null && !directory.equals("")) {
            java.io.File f = new java.io.File(directory);
            if (!f.exists()) {
                System.out.println("The file at " + f.toString() + " does not exist and cannot be deleted.");
            }
            if (!f.canWrite()) {
                System.out.println("The file at " + f.toString() + " is write protected and cannot be deleted.");
            }
            deletion = f.delete();
            System.out.println("The file at " + f.toString() + " has been deleted.");
        }
        return deletion;
    }
}
