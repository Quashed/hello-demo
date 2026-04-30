multibranchPipelineJob('hello-demo') {
    branchSources {
        branchSource {
            source {
                github {
                    id('hello-demo-id')
                    repoOwner('Quashed')
                    repository('hello-demo')
                    traits {
                        gitBranchDiscovery()
                    }
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