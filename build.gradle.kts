plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "me.kryz.mymessage"
version = "1.0.0-release"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.github.goooler.shadow")

    group = "me.kryz.mymessage"
    version = "1.0.1-RELEASE"

    dependencies {
        compileOnly("net.kyori:adventure-api:4.18.0")
        compileOnly("net.kyori:adventure-text-minimessage:4.18.0")
        compileOnly("net.kyori:adventure-platform-bukkit:4.3.4")
        compileOnly("net.kyori:adventure-text-serializer-plain:4.19.0")
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":main"))
    implementation(project(":v1_21_R1"))
    implementation(project(":v1_21_R2"))
    implementation(project(":v1_21_R3"))
    implementation(project(":v1_20_R5"))
    implementation(project(":v1_20_R3"))
    implementation(project(":v1_20_R2"))
    implementation(project(":v1_20_R1"))
//    implementation("net.kyori:adventure-api:4.18.0")
//    implementation("net.kyori:adventure-text-serializer-plain:4.19.0")
//    implementation("net.kyori:adventure-text-minimessage:4.18.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")

    implementation("org.slf4j:slf4j-api:2.0.7")

    implementation("org.jetbrains:annotations:24.0.0")
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}

//tasks {
//    shadowJar {
//        relocate("net.kyori", "me.kryz.mymessage.kyori")
//    }
//}

tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)

            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}