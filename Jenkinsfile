
pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Check Tools') {
            steps {
                bat 'git --version'
                bat 'java -version'
                bat 'mvn -version'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn --batch-mode clean verify'
            }
        }
    }

    post {
        success {
            echo 'CloudForge build and tests passed!'
        }

        failure {
            echo 'CloudForge pipeline failed. Review the console output.'
        }

        always {
            archiveArtifacts(
                artifacts: 'target/app.jar',
                allowEmptyArchive: true,
                fingerprint: true
            )
        }
    }
}
