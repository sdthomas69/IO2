package cev.blackFish

class MenuSet {

    String title
    Date date = new Date()
    Site site
    Set<Story> children

    static hasMany = [children: Story]

    static constraints = {

        title(blank: false, maxSize: 100, unique: true, validator: {
            if (it.matches('^.*[!@#$%^&?/;_()*-+-].*$')) {
                return 'title.illegal.character'
            }
        })
        children(maxSize: 8)
        site(nullable: true)
    }

    static mapping = {
        id generator: 'sequence', params: [sequence: 'menuSet_seq']
        cache true
        datasources(['DEFAULT', 'lookup'])
        children cache: true
    }

    String toString() {
        return "${title}"
    }
}
