pipeline
{
    agent any

    parameters {
        booleanParam(name : 'BUILD_DOCKER_IMAGE', defaultValue : true, description : 'BUILD_DOCKER_IMAGE')
        booleanParam(name : 'PUSH_DOCKER_IMAGE', defaultValue : true, description : 'PUSH_DOCKER_IMAGE')
    }
    environment {
    REPOSITORY = "howdi2000/edu-msa-comment"
    DOCKERHUB_CREDENTIALS = credentials('docker-hub')
    DOCKER_IMAGE = "${REPOSITORY}/${params.DOCKER_IMAGE_NAME}"
    DOCKER_TAG = "${params.DOCKER_TAG}"
  }
    tools {
        maven 'maven'
    }
    stages {
    	stage('parameter check')
    	{
    		steps
    		{
    			 echo "Current workspace : ${workspace}"
    			 sh 'mvn -version'
    		}
    	}
        stage('build')
        {
            steps {
            	sh 'pwd'
                sh 'mvn -f pom.xml clean install -P release'
              	archive '**/target/*.war'
            }
        }

        stage('build docker image')
        {
            when {
                expression { return params.BUILD_DOCKER_IMAGE }
            }
            steps {
                dir("${env.WORKSPACE}") { // /java_home/workspace/10/
                    sh 'docker build -t $REPOSITORY:$BUILD_NUMBER .'
                }
            }
            post {
                always {
                    echo "Docker build  success!"
                }
            }
        }
        stage('============ docker image push ============') {
        when { expression { return params.PUSH_DOCKER_IMAGE } }
        agent any
        steps {
            sh'''
                echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin
                docker push $REPOSITORY:$BUILD_NUMBER
            '''
        }
    }
        }
    }