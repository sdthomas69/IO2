
environments {

    production {

        serverIP = "000.000.00.00"

        streamURL = "fooservice.com"

        streamUpload = "http://fooservice.com/saveFile"

        magickDir = "/usr/bin/"

        imgcnvDir = "/usr/bin/"
    }

    development {

        serverIP = "127.0.0.1"

        streamURL = "localhost:1935"

        streamUpload = "http://localhost:8080/Stream/streamService/saveFile"

        magickDir = "/opt/local/bin/"

        imgcnvDir = "/usr/local/"
    }
}

grails.scaffolding.templates.domainSuffix = 'Instance'


//Additional items
grails.gsp.enable.reload = true
perPage = "16"
maxPerPage = "64"
minPerPage = "4"

tinyIconWidth = "100"
tinyIconHeight = "100"
smIconWidth = "480"
smIconHeight = "480"
inBetweenIconWidth = "400"
inBetweenIconHeight = "400"
medIconLength = "800"
largeIconLength = "1064"
xLargeIconLength = "2128"

smallSlideHeight = "165"
smallSlideWidth = "300"
slideHeight = "330"
slideWidth = "800"
mediumSlideHeight = "500"
mediumSlideWidth = "1500"
xSlideHeight = "700"
xSlideWidth = "2128"

flashFileHeight = 360
flashFileWidth = 640
imagesDir = "/images"
filesDir = "/files"
default_thumbnail = "${imagesDir}/no-primary-image.png"
default_sm_icon = "${imagesDir}/default-square.png"
baseKmlFile = "${filesDir}/basekml.kml"

maxFileSize = 1000 * 1024 * 100 //100 MB

maxImageSize = 1000 * 1024 * 5 //50 MB

streamURL = "stream.ocean.washington.edu:1935"

flashURL = "rtmp://${streamURL}/rsn/mp4:"
iPhoneURL = "http://${streamURL}/rsn/mp4:"
iPhoneSuffix = "/playlist.m3u8"

html = "${imagesDir}/icon-html.png"
htm = "${imagesDir}/icon-html.png"
xml = "${imagesDir}/icon-html.png"
kml = "${imagesDir}/icon-kml.png"
kmz = "${imagesDir}/icon-kml.png"
doc = "${imagesDir}/icon-word.png"
docx = "${imagesDir}/icon-word.png"
ppt = "${imagesDir}/icon-ppt.png"
pptx = "${imagesDir}/icon-ppt.png"
pptm = "${imagesDir}/icon-ppt.png"
mov = "${imagesDir}/icon-mov.png"
avi = "${imagesDir}/icon-mov.png"
mp4 = "${imagesDir}/icon-mov.png"
m4v = "${imagesDir}/icon-mov.png"
pdf = "${imagesDir}/icon-pdf.png"
xls = "${imagesDir}/icon-xls.png"
csv = "${imagesDir}/icon-xls.png"
zip = "${imagesDir}/icon-zip.png"
mp3 = "${imagesDir}/icon-mp3.png"
key = "${imagesDir}/icon-key.png"

sq_html = "${imagesDir}/icon-html-square.png"
sq_htm = "${imagesDir}/icon-html-square.png"
sq_xml = "${imagesDir}/icon-html-square.png"
sq_kml = "${imagesDir}/icon-kml-square.png"
sq_kml = "${imagesDir}/icon-kml-square.png"
sq_doc = "${imagesDir}/icon-word-square.png"
sq_docx = "${imagesDir}/icon-word-square.png"
sq_ppt = "${imagesDir}/icon-ppt-square.png"
sq_pptx = "${imagesDir}/icon-ppt-square.png"
sq_pptm = "${imagesDir}/icon-ppt-square.png"
sq_mov = "${imagesDir}/icon-mov-square.png"
sq_avi = "${imagesDir}/icon-mov-square.png"
sq_mp4 = "${imagesDir}/icon-mov-square.png"
sq_m4v = "${imagesDir}/icon-mov-square.png"
sq_pdf = "${imagesDir}/icon-pdf-square.png"
sq_xls = "${imagesDir}/icon-xls-square.png"
sq_csv = "${imagesDir}/icon-xls-square.png"
sq_key = "${imagesDir}/icon-key-square.png"
sq_zip = "${imagesDir}/icon-zip-square.png"
sq_mp3 = "${imagesDir}/icon-mp3-square.png"

genericPerson = "${imagesDir}/icon-male.jpg"

adminRole = "Administrator"
adminName = "admin"
adminPermissions = "*:*:*"

userRole = "User"
userRolePermissions = "userAdmin,fileAdmin,storyAdmin,tagAdmin:create,createText,save,login"

userDomains = "fileAdmin,storyAdmin,tagAdmin,userAdmin"
userActions = "create,edit,update,delete"
userPermission = "*"

menuSetName = "Main"

searchableObjects = ['story', 'file']

remoteServer = "http://remoteServer.com"

jwID = ""

writeHtmlFiles = false

forceConfirmation = false

springcache {
    defaults {
        // set default cache properties that will apply to all caches that do not override them
        eternal = false
        diskPersistent = false
        memoryStoreEvictionPolicy = "LRU"
    }
    caches {
        remoteAddress {}
        attempts {}
    }
}

security.shiro.authentication.strategy = org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy

//security.shiro.session.mode="native"

grails {
    mail {
        host = "000.000.00.00"
        port = 25
        username = "youracount@live.com"
        password = "yourpassword"
    }
}

grails.mail.default.from="mailer@000.000.00.00"