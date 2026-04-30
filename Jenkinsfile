pipeline {
    agent any

    stages {
        stage('Maven Build & Test') {
            steps {
                dir('app') {
                    sh './mvnw clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t quashed/hello-demo-api:${BUILD_NUMBER} -t quashed/hello-demo-api:latest ./app'
            }
        }

        stage('Integration Tests (Bruno)') {
            steps {
                sh '''
                    docker rm -f hello-demo-api || true
                    docker run -d --name hello-demo-api -p 8081:8081 quashed/hello-demo-api:${BUILD_NUMBER}
                    sleep 10
                    cd bruno && npx @usebruno/cli run .
                '''
            }
        }

        stage('Push to Registry') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push quashed/hello-demo-api:${BUILD_NUMBER}
                        docker push quashed/hello-demo-api:latest
                    '''
                }
            }
        }
    }

    post {
        always {
            sh 'docker rm -f hello-demo-api || true'
        }
    }
}