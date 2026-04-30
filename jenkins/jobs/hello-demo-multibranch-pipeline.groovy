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

            buildStrategies {
                buildRegularBranches()
                buildChangeRequests {
                    ignoreTargetOnlyChanges(true)
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
            interval('1h')
        }
    }
}