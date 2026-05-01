multibranchPipelineJob('hello-demo') {
    branchSources {
        github {
            id('hello-demo-github')
            repoOwner('Quashed')
            repository('hello-demo')
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