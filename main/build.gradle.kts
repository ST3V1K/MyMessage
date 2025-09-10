plugins {
    id("java")
}

group = "me.kryz.mymessage"
version = "1.0.0-release"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.extendedclip.com/releases/")
    }
}

dependencies {
    compileOnly(project(":common"))
    compileOnly("org.jetbrains:annotations:24.0.0")

    compileOnly("net.kyori:adventure-api:4.18.0")
    compileOnly("net.kyori:adventure-text-minimessage:4.18.0")
    compileOnly("net.kyori:adventure-platform-bukkit:4.3.4")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.18.0")

    compileOnly ("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly ("me.clip:placeholderapi:2.11.6")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}


tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)

            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}