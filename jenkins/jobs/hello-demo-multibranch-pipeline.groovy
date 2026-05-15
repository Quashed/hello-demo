multibranchPipelineJob('hello-demo') {
    branchSources {
        github {
            id('hello-demo-github')
            repoOwner('Quashed')
            repository('hello-demo')
            scanCredentialsId('github-creds')
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

    triggers {
        periodicFolderTrigger {
            interval('1m')
        }
    }
}