rootProject.name = "MyMessage"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

include("main")
include("common")
include("v1_20_R1")
include("v1_20_R2")
include("v1_20_R3")
include("v1_20_R5")
//include("v1_21_R1")
//include("v1_21_R2")
include("v1_21_R3")
include("v1_21_R5")
