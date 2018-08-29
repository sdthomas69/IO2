package cev.blackFish

import grails.test.mixin.TestFor

import spock.lang.Specification

@TestFor(MenuSet)

class MenuSetSpec extends Specification {

    def char100 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec laoreet. Aliquam id libero in ex imperdiet pellentesque ut vitae augue. Proin gravida "

    void "test that the title constraint is valid"() {

        when: "the title is a valid string"

        def menuSet1 = new MenuSet(title: "title")

        menuSet1.save(flush: true)

        then: "the validation should pass"

        menuSet1.validate()

        !menuSet1.hasErrors()

        menuSet1.errors.errorCount == 0

        when: "the title is not a unique string"

        def menuSet2 = new MenuSet(title: "title")

        then: "the validation should fail"

        !menuSet2.validate()

        menuSet2.hasErrors()

        menuSet2.errors.errorCount == 1

        when: "the title is blank"

        def menuSet3 = new MenuSet(title: " ")

        then: "the validation should fail"

        !menuSet3.validate()

        menuSet3.hasErrors()

        menuSet3.errors.errorCount == 1

        when: "the title is more than 100 characters"

        def menuSet4 = new MenuSet(title: char100)

        then: "the validation should fail"

        !menuSet4.validate()

        menuSet4.hasErrors()

        menuSet4.errors.errorCount == 1

        when: "the title containes an illegal character"

        def menuSet5 = new MenuSet(title: "@")

        then: "the validation should fail"

        !menuSet5.validate()

        menuSet5.hasErrors()

        menuSet5.errors.errorCount == 1
    }

    void "test that the children constraint is valid"() {

        Story children1 = new Story()

        /*def children2 = new Story()
        def children3 = new Story()
        def children4 = new Story()
        def children5 = new Story()
        def children6 = new Story()
        def children7 = new Story()
        def children8 = new Story()
        def children9 = new Story()
        
        Set<Story> childSet = new HashSet<Story>()
        
        childSet.add(children1)
        childSet.add(children2)
        childSet.add(children3)
        childSet.add(children4)
        childSet.add(children5)
        childSet.add(children6)
        childSet.add(children7)	
        childSet.add(children8)
        childSet.add(children9)*/

        Set<Story> childSet = new HashSet<Story>()

        (1..10).each {
            childSet.add(new Story())
        }

        when: "the children is a valid Set"

        //def menuSet6 = new MenuSet(title: "title", children: children1)

        def menuSet6 = new MenuSet(title: "title")
        menuSet6.addToChildren(children1)

        then: "the validation should pass"

        menuSet6.validate()

        !menuSet6.hasErrors()

        menuSet6.errors.errorCount == 0

        when: "the children is a Set larger than 8"

        def menuSet7 = new MenuSet(title: "title", children: childSet)

        then: "the validation should fail"

        !menuSet7.validate()

        menuSet7.hasErrors()

        menuSet7.errors.errorCount == 1
    }

    void "test that the Site constraint is valid"() {

        when: "the site is a valid Site"

        def site = new Site()

        def menuSet8 = new MenuSet(title: "title", site: site)

        then: "the validation should pass"

        menuSet8.validate()

        !menuSet8.hasErrors()

        menuSet8.errors.errorCount == 0
    }

}
