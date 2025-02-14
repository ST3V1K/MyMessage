plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
}

repositories {
    mavenCentral()
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":main"))
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
