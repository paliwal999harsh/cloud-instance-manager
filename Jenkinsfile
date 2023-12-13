pipeline {
    agent any

    stages {
        stage('Bring Container Down'){
            echo 'Stopping the Container...'
            sh 'doker-compose down'
        }
        stage('Build') {
            steps {
                echo 'Building the application...'
                sh 'gradle build'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'gradle test'
            }
        }
        stage('Deploy the Container') {
            steps {
                echo 'Deploying the application...'
                sh 'docker-compose up --detach'
            }
        }
        stage('Cleanup'){
            steps{
                echo 'Performing Cleanup...'
                sh 'docker image prune -f'
            }
        }
    }
}