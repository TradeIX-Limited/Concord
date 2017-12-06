pipeline {
    agent any

	environment {
		RABBIT_LOGIN = credentials('RabbitUserDev')		
	}

	stages {
	    stage('Init') {
			steps {
				timeout(time: 10, unit: 'MINUTES') {
					// Load system-specific environment variables
					load "/etc/tradeix/build-environment.dev.groovy"					

					// Set deployment scripts as executable
					sh script: 'chmod a+x ./Deployment/*.sh'
				}
			}
		}

		stage('Build') {
			steps {
				timeout(time: 25, unit: 'MINUTES') {
					sh 'Deployment/2-build.sh'
				}
			}
		}
	}
}