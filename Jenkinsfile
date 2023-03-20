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
    DOCKER_TAG = "1.0.${BUILD_NUMBER}"
    DEPLOY_GITREPO_USER = "limes22"
    DEPLOY_GITREPO_URL = "github.com/limes22/edu-msa-comment.git"
    DEPLOY_GITREPO_BRANCH = "main"
    DEPLOY_GITREPO_TOKEN = credentials('github-secret')
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
                    sh 'docker build -t $REPOSITORY:$DOCKER_TAG .'
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
                docker push $REPOSITORY:$DOCKER_TAG
            '''
        }
    }

        stage('K8S Manifest Update') {
        steps {
            withCredentials([gitUsernamePassword(credentialsId: 'github-login', gitToolName: 'github-tool')]) {
                sh '''
                git config --global user.email howdi2002@naver.com
                git config --global user.name limes22
                sed -i 's|howdi2000/edu-msa-commnet:1.0.*|howdi2000/edu-msa-commnet:1.0.$BUILD_NUMBER|' ./yaml/edu-msa-comment.yaml
                        git add ./yaml/edu-msa-comment.yaml
                        git commit -m '[UPDATE] $REPOSITORY $BUILD_NUMBER image versioning'
                        git push -uf origin main
                    '''
                }    
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