pipeline {
    agent any

    stages {
        // stage('Checkout') {
        //     steps {
        //         checkout scm
        //     }
        // }
        stage('Bring Container Down'){
            echo 'Stopping the Container...'
            sh 'docker-compose down'
        }
        stage('Remove Docker Image') {
            steps {
                script {
                    // Remove the specified Docker image
                    sh 'docker image rm cloud-instance-manager-core-service'
                }
            }
        }
        stage('Build') {
            steps {
                echo 'Building the application...'
                sh 'gradle build bootJar'
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
                sh 'docker-compose up --build --detach'
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