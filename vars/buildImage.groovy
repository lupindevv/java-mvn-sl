#!/usr/bin/env groovy

def call() {
    echo 'Building Docker image...'
    
    withCredentials([usernamePassword(credentialsId: 'dockerhubs', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t alexthm1/demo-app:jma-2.2 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push alexthm1/demo-app:jma-2.2'
    }
}
