import dev.triumphteam.root.configure.PublishConfigure

plugins {
    `maven-publish`
    signing
    id("dev.triumphteam.root")
}

root {
    configurePublishing {
        configure {

            from(components["java"])

            snapshotsRepo(PublishConfigure.TRIUMPH_SNAPSHOTS) {
                username = providers.gradleProperty("triumph.repo.user").orNull ?: ""
                password = providers.gradleProperty("triumph.repo.token").orNull ?: ""
            }

            releasesRepo(PublishConfigure.CENTRAL) {
                username = providers.gradleProperty("central.repo.user").orNull ?: ""
                password = providers.gradleProperty("central.repo.password").orNull ?: ""
            }

            /*signing {
                sign(publishing.publications["maven"])
            }*/
        }
    }
}
