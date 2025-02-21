#!/usr/bin/env groovy

package com.example

class Docker implements Serializable {
    def steps
    
    Docker(steps) {
        this.steps = steps
    }
    
    class BuildImage implements Serializable {
        String imageName
        String dockerfilePath
        def steps

        BuildImage(String imageName, String dockerfilePath, steps) {
            this.imageName = imageName
            this.dockerfilePath = dockerfilePath
            this.steps = steps
        }

        void build() {
            println "Building Docker image ${imageName} from ${dockerfilePath}"
            steps.withCredentials([steps.usernamePassword(credentialsId: 'dockerhubs', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                steps.sh "docker build -t ${imageName} ${dockerfilePath}"
                steps.sh 'echo $PASS | docker login -u $USER --password-stdin'
                steps.sh "docker push ${imageName}"
            }
        }
    }
    
    BuildImage buildDockerImage(String imageName) {
        return new BuildImage(imageName, '.', steps)
    }
}