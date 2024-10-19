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
                username = property("triumph.repo.user")
                password = property("triumph.repo.token")
            }

            releasesRepo(PublishConfigure.CENTRAL) {
                username = property("central.repo.user")
                password = property("central.repo.password")
            }

            signing {
                sign(publishing.publications["maven"])
            }
        }
    }
}
