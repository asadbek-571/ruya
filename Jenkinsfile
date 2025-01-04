pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-11-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the latest code from the prod branch
               git 'https://github.com/your-repo/project.git'
            }
        }

        stage('Stop Running Application') {
            steps {
                // Stop the currently running Spring Boot application on port 8080
                script {
                    def pid = sh(script: "lsof -t -i:8080", returnStdout: true).trim()
                    if (pid) {
                        sh "kill -9 ${pid}"
                        echo "Successfully stopped process with PID: ${pid}"
                    } else {
                        echo "No application running on port 8080."
                    }
                }
            }
        }

        stage('Build') {
            steps {
                // Build the application using Maven
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Run the unit tests
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deploy the Spring Boot application on port 8080
                    sh "nohup java -jar target/ruya-*.jar > target/app.log 2>&1 &"
                    echo "Application deployed on port 8080"
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up after pipeline.'
        }
        
        success {
            echo 'Build, tests, and deploy succeeded!'
        }
        
        failure {
            echo 'Build, tests, or deploy failed!'
        }
    }
}
