multibranchPipelineJob('hello-demo') {
    description('Multibranch pipeline for hello-demo Spring Boot API')

    branchSources {
        branchSource {
            source {
                github {
                    id('hello-demo-github-source')
                    repoOwner('Quashed')
                    repository('hello-demo')
                    configuredByUrl(false)
                }
            }
        }
    }

    factory {
        workflowBranchProjectFactory {
            scriptPath('Jenkinsfile')
        }
    }

    orphanedItemStrategy {
        discardOldItems {
            numToKeep(10)
        }
    }
}