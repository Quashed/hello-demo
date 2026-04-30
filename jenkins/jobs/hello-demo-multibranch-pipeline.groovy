multibranchPipelineJob('hello-demo') {
    branchSources {
        branchSource {
            source {
                git {
                    id('hello-demo-id')
                    remote('https://github.com/Quashed/hello-demo.git')
                    traits {
                        gitBranchDiscovery()
                    }
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