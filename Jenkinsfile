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
        stage('SonarQube analysis') {
            agent any
            steps 
            { 
              withSonarQubeEnv('sonarscanner') {
                sh "mvn clean verify sonar:sonar -Dsonar.projectKey=msa-sonarqube -Dsonar.login=sqp_b0983cf3e276f75424924b70a0971cd4ef8ff7e0"
              }
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

        stage('K8S Manifest Update') {
        steps {
            git credentialsId: '{github-secret}',
                url: 'https://github.com/limes22/edu-msa-comment.git',
                branch: 'main'

            sh '''
                sed -i 's/$REPOSITORY:.*\$/$REPOSITORY:$BUILD_NUMBER/g' ./yaml/edu-msa-comment.yaml
                git add ./yaml/edu-msa-comment.yaml
                git commit -m '[UPDATE] $REPOSITORY $BUILD_NUMBER image versioning'
                git remote set-url origin https://github.com/limes22/edu-msa-comment.git
                git push -u origin main
            '''
        }
        post {
                failure {
                  echo 'K8S Manifest Update failure !'
                }
                success {
                  echo 'K8S Manifest Update success !'
                }
        }
    }

        }
    }