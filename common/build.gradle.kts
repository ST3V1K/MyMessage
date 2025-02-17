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
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("net.luckperms:api:5.4")
    compileOnly ("me.clip:placeholderapi:2.11.6")
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT") // The Spigot API with no shadowing. Requires the OSS repo.
    compileOnly("org.spigotmc:spigot:1.20.2-R0.1-SNAPSHOT") // The full Spigot server with no shadowing. Requires mavenLocal.
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}