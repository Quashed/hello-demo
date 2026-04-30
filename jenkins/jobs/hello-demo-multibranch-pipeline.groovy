pipelineJob('hello-demo-single') {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/Quashed/hello-demo.git')
//                        credentials('github-credentials-id')
                    }
                    branches('main')
                }
            }
            scriptPath('Jenkinsfile')
        }
    }

    triggers {
        githubPush()
    }

    logRotator {
        numToKeep(10)
    }
}