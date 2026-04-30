multibranchPipelineJob('hello-demo') {
    branchSources {
        branchSource {
            source {
                github {
                    id('hello-demo-github')
                    repoOwner('Quashed')
                    repository('hello-demo')
                    configuredByUrl(false)

                    traits {
                        gitHubBranchDiscovery {
                            strategyId(1)
                        }
                        gitHubPushTrigger()
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

    triggers {
        periodicFolderTrigger {
            interval('1m')
        }
    }

    orphanedItemStrategy {
        discardOldItems {
            numToKeep(10)
        }
    }
}