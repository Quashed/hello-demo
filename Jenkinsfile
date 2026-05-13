// pipeline {
//     agent any
//
//     stages {
//         stage('Maven Build & Test') {
//             steps {
//                 dir('app') {
//                     sh './mvnw clean package'
//                 }
//             }
//         }
//
//         stage('Build Docker Image') {
//             steps {
//                 sh 'docker build -t hello-demo-api ./app'
//             }
//         }
//
//         stage('Integration Tests (Bruno)') {
//             steps {
//                 sh '''
//                     docker rm -f hello-demo-api || true
//                     docker run -d --name hello-demo-api -p 8081:8081 hello-demo-api
//                     sleep 10
//                     cd bruno && npx @usebruno/cli run .
//                 '''
//             }
//         }
//     }
//
//     post {
//         always {
//             sh 'docker rm -f hello-demo-api || true'
//         }
//     }
// }
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