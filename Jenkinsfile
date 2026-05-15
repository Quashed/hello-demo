pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
spec:
  containers:
    - name: maven
      image: maven:3.9.6-eclipse-temurin-21
      command:
        - cat
      tty: true

    - name: node
      image: node:20-alpine
      command:
        - cat
      tty: true
'''
        }
    }

    stages {
        stage('Maven Build & Test') {
            steps {
                container('maven') {
                    dir('app') {
                        sh './mvnw clean package'
                    }
                }
            }
        }

        stage('Start Spring Boot App') {
            steps {
                container('maven') {
                    dir('app') {
                        sh '''
                            nohup java -jar target/*.jar > app.log 2>&1 &
                            sleep 10
                            cat app.log
                        '''
                    }
                }
            }
        }

        stage('Integration Tests (Bruno)') {
            steps {
                container('node') {
                    sh '''
                        cd bruno
                        npx @usebruno/cli run .
                    '''
                }
            }
        }
    }
}