plugins {
    id("java")
}

group = "me.kryz.mymessage"
version = "1.0.0-release"

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://repo.extendedclip.com/releases/")
    }
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("net.luckperms:api:5.4")
    compileOnly ("me.clip:placeholderapi:2.11.6")
    compileOnly ("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}