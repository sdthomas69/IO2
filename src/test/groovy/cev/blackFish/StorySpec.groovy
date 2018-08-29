package cev.blackFish


import grails.test.mixin.TestFor


import spock.lang.Specification

@TestFor(Story)

class StorySpec extends Specification {

    void "test that the layout constraint is valid"() {

        when: "the layout is a valid Layout"

        Story story1 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description")

        then: "the validation should pass"

        story1.validate()

        !story1.hasErrors()

        story1.errors.errorCount == 0

        when: "the layout is blank"

        Story story2 = new Story(layout: " ", title: "test", urlTitle: "urltest", description: "description")

        then: "the validation should fail"

        !story2.validate()

        story2.hasErrors()

        story2.errors.errorCount == 1

        when: "the layout is more than 55 characters long"

        Story story3 = new Story(layout: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie in magna nec ", title: "test", urlTitle: "urltest", description: "description")

        then: "the validation should fail"

        !story3.validate()

        story3.hasErrors()

        story3.errors.errorCount == 1

        when: "the layout is a non valid layout"

        Story story4 = new Story(layout: "test", title: "test", urlTitle: "urltest", description: "description")

        then: "the validation should fail"

        !story4.validate()

        story4.hasErrors()

        story4.errors.errorCount == 1

    }

    void "test that the primaryImage, embeddedImage1, embeddedImage2, and kmlFile constraints are valid"() {

        when: "the image and the kmlFile are valid and non-null"

        def primary = new File()
        def embedded1 = new File()
        def embedded2 = new File()
        def kml = new File()

        Story story5 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description", primaryImage: primary, embeddedImage1: embedded1, embeddedImage2: embedded2, kmlFile: kml)

        then: "the validation should pass"

        story5.validate()

        !story5.hasErrors()

        story5.errors.errorCount == 0

    }

    void "test that the parent constraint is valid"() {

        when: "the parent is a valid non-null story"

        def story = new Story()

        Story story6 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description", parent: story)

        then: "the validation should pass"

        story6.validate()

        !story6.hasErrors()

        story6.errors.errorCount == 0

    }

    void "test that the orderIndex constraint is valid"() {

        when: "the orderIndex is a valid non-null index"

        Story story7 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description", orderIndex: 2)

        then: "the validation should pass"

        story7.validate()

        !story7.hasErrors()

        story7.errors.errorCount == 0

        when: "the orderIndex is a nonvalid non-null index"

        Story story8 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description", orderIndex: "two")

        then: "the validation shoudl fail"

        !story8.validate()

        story8.hasErrors()

        story8.errors.errorCount == 1
    }

    void "test that the menuSet constraint is valid"() {

        when: "the menuSet is a valid non-null menuSet"

        def menu = new MenuSet()

        Story story9 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description", menuSet: menu)

        then: "the validation should pass"

        story9.validate()

        !story9.hasErrors()

        story9.errors.errorCount == 0
    }

    void "test that the endDate constraint is valid"() {

        when: "the endDate is a valid non-null date"

        def end = new Date()

        Story story10 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description", endDate: end)

        then: "the validation should pass"

        story10.validate()

        !story10.hasErrors()

        story10.errors.errorCount == 0
    }

    void "test that the headScript, bodyScript, frameworkScript, layerId, and dataURL constraints are valid"() {

        when: "the strings are valid non-null strings"


        Story story11 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description", headScript: "headString", bodyScript: "bodyString", frameworkScript: "frameString", layerId: "layerString", dataURL: "dataString")

        then: "the validation should pass"

        story11.validate()

        !story11.hasErrors()

        story11.errors.errorCount == 0
    }

    void "test that the depth constraint is valid"() {

        when: "the depth is a valid, non-null double"

        Story story12 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description", depth: 2.0)

        then: "the validation should pass"

        story12.validate()

        !story12.hasErrors()

        story12.errors.errorCount == 0

        when: "the depth is a nonvalid, non-null double"

        Story story13 = new Story(layout: "STANDARD", title: "test", urlTitle: "urltest", description: "description", depth: "two")

        then: "the validation should fail"

        !story13.validate()

        story13.hasErrors()

        story13.errors.errorCount == 1
    }
}
