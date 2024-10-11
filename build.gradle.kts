plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.jjjannik"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
}

dependencies {
    implementation("net.dv8tion", "JDA", "5.0.0") {
        exclude("club.minnced", "opus-java")
    }
    implementation("org.slf4j", "slf4j-log4j12", "2.0.1")
    implementation("org.apache.logging.log4j", "log4j-api", "2.19.0")
    implementation("org.apache.logging.log4j", "log4j-core", "2.19.0")
    implementation("me.carleslc.Simple-YAML", "Simple-Yaml", "1.8.3")
    implementation("com.github.JJJannik", "JGA", "1.2.0")

    implementation(files("./xchart-3.8.8.jar"))

    compileOnly("org.projectlombok", "lombok", "1.18.24")
    annotationProcessor("org.projectlombok", "lombok", "1.18.24")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        manifest {
            attributes["Main-Class"] = "de.jjjannik.Main"
        }
        archiveFileName.set("greev-stats-stalker-$version.jar")
    }
}