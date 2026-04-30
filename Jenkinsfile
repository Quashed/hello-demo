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
                    docker run -d --name hello-demo-api -p 8081:8080 hello-demo-api:latest
                '''
            }
        }

        stage('Smoke Test') {
            steps {
                sh '''
                    sleep 10
                    curl -f http://localhost:8081/hello
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