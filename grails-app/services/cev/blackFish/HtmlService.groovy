package cev.blackFish

import grails.transaction.Transactional
import grails.util.Holders
import grails.util.Environment
import java.util.concurrent.*
import javax.annotation.*

@Transactional(readOnly = true)
class HtmlService {

    def grailsApplication

    def config = Holders.config

    def getServerUrl = {

        switch (Environment.current) {
            case Environment.DEVELOPMENT:
                return config.grails.serverURL
                break
            case Environment.PRODUCTION:
                return config.grails.serverURL
                break
        }
    }

    ExecutorService executor = Executors.newSingleThreadExecutor()

    /**
     *
     * @param context
     * @return
     */
    public writeAllObjectsAsHtml(String context) {

        def stories = Story.list()

        for (story in stories) {

            if (story.published) {
                writeObjectAsHtml(story, "/story", context)
            }
        }

        def files = File.list()

        for (file in files) {
            if (file.published) {
                writeObjectAsHtml(file, "/file", context)
            }
        }

        def tags = Tag.list()

        for (tag in tags) {
            writeObjectAsHtml(tag, "/tag", context)
        }
    }

    /**
     *
     * @param context
     * @return
     */
    public updateObjects() {

        updateStories()

        updateFiles()

        updateTags()
    }

    /**
     *
     * @param object
     * @param directory
     * @param context
     * @return
     */
    public void writeObjectAsHtml(Object object, String directory, String context) {

        executor.execute {

            String uri = object.uri

            if (uri) {

                String link = "${directory}/${object.urlTitle}"

                if (directory == "/story" && object.isLink && object.dataURL) {

                    link = object.dataURL
                }

                String htmlString = getHtml(link)

                if (htmlString) {

                    String path = context + uri

                    //if(!path.contains("404")) {

                    writeFile(path, htmlString)
                    //}
                }
            }
        }
    }

    public String setUri(Object object, String directory) {

        String uri = "${directory}/${object.urlTitle}.html"

        if (directory == "/story" && object.isLink && object.dataURL) {
            uri = object.uri
        }

        return uri
    }

    /**
     *
     * @param path
     * @param html
     * @return
     */
    private void writeFile(String path, String html) {

        try {

            // delete the old
            org.apache.commons.io.FileUtils.write(new java.io.File(path), "")

            // write the new
            org.apache.commons.io.FileUtils.write(new java.io.File(path), html)

        } catch (IOException ex) {
            System.out.println("Exception caught attempting to write an html file: " + ex.getMessage());
        }
    }

    /**
     *
     * @param link
     * @return
     */
    private String getHtml(String link) {

        String html = ""

        def url = config.grails.serverURL + link

        try {

            html = url?.toURI()?.toURL()?.getText('UTF-8')

        } catch (Exception e) {
            System.out.println("Exception caught attempting to invoke a URL: " + e.getMessage())
        }
        return html
    }

    /**
     *
     * @param path
     */
    public void deleteFile(String path) {

        java.io.File file = new java.io.File(path)

        if (file.exists()) {

            try {
                file.delete()
            } catch (IOException ex) {
                System.out.println("Exception caught attempting to delete a file: " + ex.getMessage());
            }
        }
    }

    /**
     *
     * @param context
     * @return
     */
    @Transactional
    private updateStories() {

        def stories = Story.list()

        for (story in stories) {

            if (story.published) {
                story.setUri(setUri(story, "/story"))
                story.save()
            }
        }
    }

    /**
     *
     * @param context
     * @return
     */
    @Transactional
    private updateFiles() {

        def files = File.list()

        for (file in files) {

            if (file.published) {
                file.setUri(setUri(file, "/file"))
                file.save()
            }
        }
    }

    /**
     *
     * @param context
     * @return
     */
    @Transactional
    private updateTags() {

        def tags = Tag.list()

        for (tag in tags) {
            tag.setUri(setUri(tag, "/tag"))
            tag.save()
        }
    }

    @PreDestroy
    void shutdown() {
        executor.shutdownNow()
    }

}
