pipeline {
    agent any

    environment {
        // Define environment variables
        JAVA_HOME = '/usr/lib/jvm/java-11-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the repository
                git 'https://github.com/asadbek-571/ruya.git'
            }
        }
        
        stage('Build') {
            steps {
                // Example build step
                sh 'mvn clean install'
            }
        }
        
        stage('Test') {
            steps {
                // Run tests
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                // Deploy to a server or environment
                sh './deploy.sh'
            }
        }
    }

    post {
        always {
            // Clean up after the pipeline execution
            echo 'Cleaning up after pipeline.'
        }
        
        success {
            // Actions on success
            echo 'Build and tests succeeded!'
        }
        
        failure {
            // Actions on failure
            echo 'Build or tests failed!'
        }
    }
}
