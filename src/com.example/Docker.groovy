#!/usr/bin/env groovy

package com.example

class Docker implements Serializable {
    
    class BuildImage implements Serializable {
        String imageName
        String dockerfilePath

        BuildImage(String imageName, String dockerfilePath) {
            this.imageName = imageName
            this.dockerfilePath = dockerfilePath
        }

        void build() {
            println "Building Docker image ${imageName} from ${dockerfilePath}"
            withCredentials([usernamePassword(credentialsId: 'dockerhubs', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                sh "docker build -t ${imageName} ${dockerfilePath}"
                sh 'echo $PASS | docker login -u $USER --password-stdin'
                sh "docker push ${imageName}"
            }
        }
    }
}