package cev.blackFish

import javax.servlet.ServletContext
import org.springframework.web.multipart.MultipartFile
import grails.util.Holders
import grails.util.GrailsUtil
import grails.util.Environment
import grails.transaction.Transactional
import java.awt.Dimension
import javax.imageio.ImageReader
import javax.imageio.stream.FileImageInputStream
import javax.imageio.stream.ImageInputStream
import javax.imageio.ImageIO

@Transactional(readOnly = true)
class FileService {

    def config = Holders.config

    File readFile(Long id) {

        return File.lookup.read(id)
    }

    File findByTitle(String title) {

        File file = File.lookup.withCriteria(uniqueResult: true) {

            or {
                eq("urlTitle", title)
                eq("title", title.replaceAll("%20", " "))
            }
            setReadOnly true
        }
    }

    def list(
            Integer max = 10,
            Integer offset = 0,
            boolean titleOnly = false,
            String type = "",
            String q = "",
            String bool = "",
            String sortBy = "",
            String orderBy = "",
            String category = "") {

        def query = {
            if (type) eq("type", type)
            if (bool) eq(bool, true)
            if (category) eq("category", category)
            if (q) {
                if (titleOnly) {
                    ilike("title", "%${q}%")
                } else {
                    File instance = new File()
                    or {
                        instance.searchableProps.each {
                            ilike(it, "%${q}%")
                        }
                    }
                }
            }
            order(sortBy ?: 'date', orderBy ?: 'desc')
            maxResults(max)
            firstResult(offset)
            setReadOnly true
        }

        def results = File.createCriteria().list(['max': max, 'offset': offset], query)


        return results
    }

    /**
     * Deletes files from the system via the ServletContext while iterating through the 
     * generatedFiles list
     * @param file
     * @param context
     */
    def deletePhysicalFiles(File file, ServletContext context) {

        if (file && context) {

            if (file.type == 'Image' || file.type == 'Quicktime') {

                file.generatedFiles.each {

                    // if 'it' is not null and the method invoked on the file is not null, delete it

                    if (it && file."${it}") {
                        deleteDirectory(context.getRealPath(file."${it}"))
                    } // slick Groovy method invocation
                }
            } else deleteDirectory(context.getRealPath(file.path))
        }
    }

    def magickDir = {

        /*switch(GrailsUtil.environment) {
        	case "development":
        	   return config.magickDir
        	break
        	case "production":
        	   return config.magickDir
        	break 
        }*/

        switch (Environment.current) {
            case Environment.DEVELOPMENT:
                return config.magickDir
                break
            case Environment.PRODUCTION:
                return config.magickDir
                break
        }
    }

    def imgcnvDir = {

        switch (Environment.current) {
            case Environment.DEVELOPMENT:
                return config.imgcnvDir
                break
            case Environment.PRODUCTION:
                return config.imgcnvDir
                break
        }
    }

    void createThumbnails(String filePath) {

        String arg = """-thumbnail ${config.smIconWidth}X${config.smIconHeight}^ -gravity center -crop ${
            config.smIconWidth
        }X${config.smIconHeight}-0-0"""
        createThumbnail(filePath, "_smsq.jpg", arg)

        arg = "-thumbnail " + config.smIconWidth.toString()
        createThumbnail(filePath, "_sm.jpg", arg)

        arg = """-thumbnail ${config.tinyIconWidth}X${config.tinyIconHeight}^ -gravity center -crop ${
            config.tinyIconWidth
        }X${config.tinyIconHeight}-0-0"""
        createThumbnail(filePath, "_smaller.jpg", arg)

        arg = """-thumbnail ${config.smallSlideWidth}X${config.smallSlideHeight}^ -gravity center -crop ${
            config.smallSlideWidth
        }X${config.smallSlideHeight}-0-0"""
        createThumbnail(filePath, "_sm_slide.jpg", arg)

        arg = """-thumbnail ${config.slideWidth}X${config.slideHeight}^ -gravity center -crop ${config.slideWidth}X${
            config.slideHeight
        }-0-0"""
        createThumbnail(filePath, "_slide.jpg", arg)

        //arg = """-thumbnail ${config.mediumSlideWidth}X${config.mediumSlideHeight}^ -gravity center -crop ${config.mediumSlideWidth}X${config.mediumSlideHeight}-0-0"""
        arg = " -thumbnail " + config.mediumSlideWidth.toString()
        createThumbnail(filePath, "_mediumSlide.jpg", arg)

        //arg = """-thumbnail ${config.xSlideWidth}X${config.xSlideHeight}^ -gravity center -crop ${config.xSlideWidth}X${config.xSlideHeight}-0-0"""
        arg = " -thumbnail " + config.xSlideWidth.toString()
        createThumbnail(filePath, "_slide@2x.jpg", arg)

        arg = " -thumbnail " + config.inBetweenIconWidth.toString()
        createThumbnail(filePath, "_bigger.jpg", arg)

        arg = " -thumbnail " + config.medIconLength.toString()
        createThumbnail(filePath, "_med.jpg", arg)

        arg = " -thumbnail " + config.largeIconLength.toString()
        createThumbnail(filePath, "_large.jpg", arg)

        arg = " -thumbnail " + config.xLargeIconLength.toString()
        createThumbnail(filePath, "_large@2x.jpg", arg)

    }

    void createThumbnail(String pathName, String extension, String arg) {

        String pathWithoutExtension = pathName.substring(0, pathName.lastIndexOf('.'))
        //println("${magickDir()}convert ${pathName} ${arg} ${pathWithoutExtension}${extension}")
        def process = "${magickDir()}convert ${pathName} ${arg} ${pathWithoutExtension}${extension}".execute()
        process.waitFor()
    }

    void createSlideThumbnails(String pathName, String width, String extension) {

        String pathWithoutExtension = pathName.substring(0, pathName.lastIndexOf('.'))
        def process = "${magickDir()}convert ${pathName} -thumbnail ${width} -gravity center -extent ${width}X${config.slideHeight} ${pathWithoutExtension}${extension}".execute()
        process.waitFor()
    }

    void createTiles(String filePath, String directory, String sSize) {

        //println "${imgcnvDir()}imgcnv -i ${filePath} -o ${directory}/${sSize}.jpg -t jpeg -tile ${sSize}"
        def process = "${imgcnvDir()}imgcnv -i ${filePath} -o ${directory}/${sSize}.jpg -t jpeg -tile ${sSize}".execute()
        process.waitFor()
    }

    /**
     * Gets image dimensions for given file
     * @param imgFile image file
     * @return dimensions of image
     * @throws IOException if the file is not a known image
     */
    public static Dimension getImageDimension(java.io.File imgFile) throws IOException {

        int pos = imgFile.getName().lastIndexOf(".")
        if (pos == -1)
            throw new IOException("No extension for file: " + imgFile.getAbsolutePath())
        String suffix = imgFile.getName().substring(pos + 1)
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix)

        if (iter.hasNext()) {
            ImageReader reader = iter.next()
            try {
                ImageInputStream stream = new FileImageInputStream(imgFile)
                reader.setInput(stream)
                int width = reader.getWidth(reader.getMinIndex())
                int height = reader.getHeight(reader.getMinIndex())
                return new Dimension(width, height)
            } catch (IOException e) {
                println("Error reading: " + imgFile.getAbsolutePath(), e)
            } finally {
                reader.dispose();
            }
        }

        throw new IOException("Not a known image file: " + imgFile.getAbsolutePath());
    }

    /**
     * Determines the size of a file on the local system
     * @param filename : the file passed in
     * @return size: the byte size of the file
     */
    int getFileBytes(String fileName, String applicationLocation) {

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
     * @param size : the byte size of the file passed in
     * @return filesize:  the size of the file in bytes or kb or mb
     */
    String getFileSize(int size) {

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
     * Constructs a URI for a thumbnail.  If a thumbnail for the file exists, it is returned.
     * If a thumbnail that matches the filetype (which matches a file type in the properties file) can be found, it is returned.  
     * Otherwise a default thumbnail is returned.
     * @param path
     * @return
     */
    String getThumbnail(String path, String fullPath) {

        int index = -1;

        if (path != null && (index = path.lastIndexOf('.')) != -1) {

            String smjpg = path.substring(0, path.lastIndexOf(".")) + "_sm.jpg";

            if (getFileBytes(smjpg, fullPath) != 0) {
                return smjpg;
            }

            String[] s = path.split("[.]");

            if (s != null && s.length != 0) {

                String propertyType = config.(s[s.length - 1]);

                if (propertyType != null) {
                    return propertyType;
                }
            }
        }

        return config.default_thumbnail;
    }

    String getTinyThumbnail(String path, String fullPath) {

        int index = -1;

        if (path != null && (index = path.lastIndexOf('.')) != -1) {

            String[] s = path.split("[.]");

            if (s != null && s.length != 0) {

                String propertyType = config.("sq_" + s[s.length - 1]);

                if (propertyType != null) {
                    return propertyType;
                }
            }
        }

        return config.default_sm_icon;
    }

    /**
     * Constructs a URI for a thumbnail
     * @param path
     * @return
     */
    String getMedImage(String path, String fullPath, String extension) {

        String default_thumbnail = "";

        if (path == null || path.lastIndexOf('.') == -1) {
            return default_thumbnail;
        } else {

            String medjpg = path.substring(0, path.lastIndexOf(".")) + extension;

            if (getFileBytes(medjpg, fullPath) != 0) {
                return medjpg;
            }
        }
        return default_thumbnail;
    }

    /**
     * Used to clean up file names.  Makes the name lower case and strips problematic characters
     * @param filename : A (String) name of a file
     * @return filename: The "stripped" name
     */
    String stripCharacters(String filename) {

        filename = filename.toLowerCase()
        filename = filename.replaceAll("[^\\d\\w\\.\\-]", "")
        return addDate(filename)
    }

    String addDate(String filename) {

        def today = new Date()
        def sdf = new java.text.SimpleDateFormat("yyyyMMddhhmmss")
        filename = filename.replace(".", "-" + sdf.format(today) + ".")
        return filename
    }

    /**
     * Attempts to create a directory
     * @param directoryName : the (String) directory path
     * @return: makeDirectory (boolean)
     */
    boolean MakeDirectory(String directoryName) {

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
     * @param directory
     * @return
     */
    boolean deleteDirectory(String directory) {

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
