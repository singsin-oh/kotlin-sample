plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.singsin.studio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}