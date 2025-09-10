plugins {
    id("java")
}

group = "me.kryz.mymessage"
version = "1.0.0-release"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":main"))
    compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}