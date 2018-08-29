package cev.blackFish

import org.apache.commons.lang.builder.HashCodeBuilder

class FileTagAssociation implements Serializable {

    File file

    Tag tag

    static mapping = {
        table 'file_tags'
        version false
        id composite: ['file', 'tag']
        cache 'true'
        datasources(['DEFAULT', 'lookup'])
    }

    static FileTagAssociation create(File file, Tag tag) {

        FileTagAssociation fileTagAssociation = new FileTagAssociation(file: file, tag: tag)
        fileTagAssociation.save(flush: true)
        return fileTagAssociation
    }

    static boolean remove(File file, Tag tag) {

        FileTagAssociation fileTagAssociation = FileTagAssociation.findByFileAndTag(file, tag)

        if (fileTagAssociation && fileTagAssociation.delete(flush: true)) return true

        return false
    }

    static void removeAllByFile(File file) {
        executeUpdate("DELETE FROM FileTagAssociation WHERE file=:file", [file: file])
    }

    static void removeAllByTag(Tag tag) {
        executeUpdate("DELETE FROM FileTagAssociation WHERE tag=:tag", [tag: tag])
    }

    boolean equals(other) {

        if (!(other instanceof FileTagAssociation)) {
            return false
        }
        return other.file.id == file.id && other.tag.id == tag.id
    }

    /*int hashCode() {
        return new HashCodeBuilder().append(tag.id).append(file.id).toHashCode()
    }*/
}
