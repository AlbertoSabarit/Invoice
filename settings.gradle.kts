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
include(":features:account")
//include(":features:accountsignup")
include(":infrastructure:firebase")
include(":infrastructure:printer")
include(":features:customer")
include(":base:ui")
include(":base:utils")
include(":domain:invoice")
include(":features:invoicemodule")
include(":features:itemmodule")
include(":features:task")
