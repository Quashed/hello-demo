pipeline {
    agent any

    stages {
        stage('Build and Test') {
            steps {
                sh 'cd app && ./mvnw clean test'
            }
        }

        stage('Package') {
            steps {
                sh 'cd app && ./mvnw clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build -t quashed/hello-demo-api:${BUILD_NUMBER} ./app
                    docker tag quashed/hello-demo-api:${BUILD_NUMBER} quashed/hello-demo-api:latest
                '''
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                    docker rm -f hello-demo-api || true
                    docker run -d --name hello-demo-api -p 8081:8081 quashed/hello-demo-api:${BUILD_NUMBER}
                '''
            }
        }

        stage('Bruno API Tests') {
            steps {
                sh '''
                    sleep 10
                    cd bruno
                    npx @usebruno/cli run .
                '''
            }
        }

        stage('Push Docker Image') {
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