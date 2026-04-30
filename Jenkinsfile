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
                sh 'docker build -t hello-demo-api:latest ./app'
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                    docker rm -f hello-demo-api || true
                    docker run -d --name hello-demo-api -p 8081:8081 hello-demo-api:latest
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
    }

    post {
        always {
            sh 'docker rm -f hello-demo-api || true'
        }
    }
}