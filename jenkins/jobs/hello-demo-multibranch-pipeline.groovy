multibranchPipelineJob('hello-demo') {
    branchSources {
        branchSource {
            source {
                git {
                    id('hello-demo-id')
                    remote('https://github.com/Quashed/hello-demo.git')
                    traits {
                        gitBranchDiscovery() // This is the magic line
                    }
                }
            }
        }
    }
    // This tells Jenkins where to look for the build steps
    factory {
        workflowBranchProjectFactory {
            scriptPath('Jenkinsfile')
        }
    }
}