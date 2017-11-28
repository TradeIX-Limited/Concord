pipeline {
    agent any

	stages {
		stage('Build') {
			steps {
				timeout(time: 25, unit: 'MINUTES') {
					sh 'Deployment/2-build.sh'
				}
			}
		}
	}
}