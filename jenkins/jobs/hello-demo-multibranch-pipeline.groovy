multibranchPipelineJob('hello-demo') {
    branchSources {
        branchSource {
            source {
                github {
                    id('hello-demo-id')
                    repoOwner('Quashed')
                    repository('hello-demo')
//                    scanCredentialsId('github')
                    traits {
                        gitBranchDiscovery()
                    }
                }
            }

            strategy {
                defaultBranchPropertyStrategy {
                    props {
                        noTriggerBranchProperty()
                    }
                }
            }

            buildStrategies {
                buildRegularBranches()
                buildChangeRequests {
                    ignoreTargetOnlyChanges(true)
                }
            }
        }
    }

    triggers {
        periodicFolderTrigger {
            interval('1h')
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