pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Invoice"
include(":app")
include(":features:accountsignin")
include(":features:accountsignup")
include(":features:customercreation")
include(":infrastructure:firebase")
include(":infrastructure:printer")
include(":features:customerdetail")
include(":features:customerlist")
include(":features:invoicecreation")
include(":base:ui")
include(":base:utils")
include(":domain:invoice")
include(":features:invoicedetail")
include(":features:invoicelist")
include(":features:itemcreation")
include(":features:itemdetail")
include(":features:itemlist")
include(":features:taskcreation")
include(":features:taskdetail")
include(":features:tasklist")
