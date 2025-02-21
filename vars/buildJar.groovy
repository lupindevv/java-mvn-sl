#!/usr/bin/env groovy

def call () {
    echo "building the app for brach $BRANCH_NAME"
    sh 'mvn package'
}
