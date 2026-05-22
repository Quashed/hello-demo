pipeline {
  agent none
  stages {

    stage("Clone") {
      agent { kubernetes { label 'k8s-agent' } }
      steps {
        container('maven') {
          git branch: "brandon-branch",
              url: "https://github.com/Quashed/hello-demo.git"
          stash name: "source-code", includes: "**/*"
        }
      }
    }

    stage("Build") {
      agent { kubernetes { label 'k8s-agent' } }
      steps {
        container('maven') {
          unstash "source-code"
          sh "mvn clean package -DskipTests"
          stash name: "built-app", includes: "**/*"
        }
      }
    }

    stage("Build Image") {
      agent { kubernetes { label 'k8s-agent' } }
      steps {
        container('docker') {
          unstash "built-app"
          sh "docker build -t hello-demo:latest ."
        }
      }
    }

    stage("Deploy") {
      agent { kubernetes { label 'k8s-agent' } }
      steps {
        container('maven') {
          writeFile file: "hello-demo.yml", text: """
apiVersion: apps/v1
kind: Deployment
metadata:
  name: hello-demo
  namespace: default
spec:
  replicas: 1
  selector:
    matchLabels:
      app: hello-demo
  template:
    metadata:
      labels:
        app: hello-demo
    spec:
      containers:
        - name: hello-demo
          image: hello-demo:latest
          imagePullPolicy: Never
          ports:
            - containerPort: 8081
---
apiVersion: v1
kind: Service
metadata:
  name: hello-demo-service
  namespace: default
spec:
  selector:
    app: hello-demo
  ports:
    - port: 8081
      targetPort: 8081
"""
          sh "curl -LO https://dl.k8s.io/release/v1.29.0/bin/linux/amd64/kubectl"
          sh "chmod +x kubectl"
          sh "./kubectl apply -f hello-demo.yml"
          sh "./kubectl rollout status deployment/hello-demo --timeout=60s"
        }
      }
    }

    stage("Bruno Tests") {
      agent { kubernetes { label 'k8s-agent' } }
      steps {
        container('node') {
          unstash "source-code"
          sh "npm install -g @usebruno/cli"
          sh "cd bruno && bru run . --env ci"
        }
      }
    }

  }
}