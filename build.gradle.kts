plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies{
    implementation("com.github.jkcclemens:khttp:-SNAPSHOT")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
}
