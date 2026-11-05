
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
        stage('Check Docker') {
             steps {
                bat 'where docker'
                bat 'docker version'
                bat 'docker info'
            }
        }

        stage('Secret Scan') {
            steps {
                bat '''
                    "C:\\Users\\pradi\\AppData\\Local\\Microsoft\\WinGet\\Packages\\Gitleaks.Gitleaks_Microsoft.Winget.Source_8wekyb3d8bbwe\\gitleaks.exe" git . --redact --verbose
                '''
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
            echo 'CloudForge secret scan, build and tests passed!'
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
