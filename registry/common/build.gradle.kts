import dev.greenhouseteam.greenhouse_common.gradle.Properties
import dev.greenhouseteam.greenhouse_common.gradle.Versions
import dev.greenhouseteam.greenhouse_common.gradle.props

plugins {
    id("conventions.common")
    id("net.neoforged.moddev")
}

sourceSets {
    create("generated") {
        resources {
            srcDir("src/generated/resources")
        }
    }
}

val core = project(":core-common")
val event = project(":event-common")

dependencies {
    compileOnly(project(":core-common")) {
        capabilities {
            requireCapability("${Properties.GROUP}:${core.props.modId}-${core.props.moduleName}-common")
        }
        isTransitive = false
    }
    compileOnly(project(":event-common")) {
        capabilities {
            requireCapability("${Properties.GROUP}:${event.props.modId}-${event.props.moduleName}-common")
        }
        isTransitive = false
    }
}
neoForge {
    neoFormVersion = Versions.NEOFORM
    parchment {
        minecraftVersion = Versions.INTERNAL_MINECRAFT
        mappingsVersion = Versions.PARCHMENT
    }
    addModdingDependenciesTo(sourceSets["test"])

    val at = file("src/main/resources/${project.props.modId}.cfg")
    if (at.exists())
        setAccessTransformers(at)
    validateAccessTransformers = true
}

configurations {
    register("commonJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("commonTestJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("commonResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("commonTestResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
}

artifacts {
    add("commonJava", sourceSets["main"].java.sourceDirectories.singleFile)
    add("commonTestJava", sourceSets["test"].java.sourceDirectories.singleFile)
    add("commonResources", sourceSets["main"].resources.sourceDirectories.singleFile)
    add("commonTestResources", sourceSets["test"].resources.sourceDirectories.singleFile)
}