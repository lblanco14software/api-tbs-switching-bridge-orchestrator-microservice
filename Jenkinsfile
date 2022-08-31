// Pipeline Proyectos Java Maven
def repositoryName = 'https://code.novopayment.com/novopayment/api-tbs-switching-bridge-orchestrator-microservice.git'
def devopsSettingJson = ''
def repositoryBranchApp = ''

def repositoryUser = ''
def processRepository = ''
def processPusher = ''
def getBuildUser = ''
def projectVersion = ''
def projectEnvironment = ''

def registryNovopaymentUrl = ''
def imageName = ''
def imageVersion = ''

def sonarNovopaymentUrl = ''
def sonarProjectKey = ''
def sonarProjectName = ''

def apmJarPath = ''
def apmJar  = ''
def apmServiceName = ''
def apmServerUrl = ''
def apmSecretToken = ''
def apmProdServerUrl = ''

node {
    properties([
    pipelineTriggers([
        [$class: 'GenericTrigger',
        genericVariables: [
            [ key: 'ref', value: '$.ref' ],
            [ key: 'repository', value: '$.repository' ],
            [ key: 'pusher', value: '$.pusher' ],
            [ key: 'committer_name', value: '$.pusher.name' ],
            [ key: 'commit', value: '$.after' ],
        ],
        causeString: '[$committer_name] pushed from branch [$ref] referencing [$commit]',
        token: 'novo-token-api-tbs-switching-bridge-orchestrator-microservice',
        printContributedVariables: true,
        printPostContent: true,
        ]
    ])
])
    wrap([$class: 'BuildUser']) {
        getBuildUser = env.BUILD_USER_ID
    }

    stage("Prepare Webhook") {
        deleteDir()
        if(env.ref) {
            echo "REF: $ref"
        }
        if(env.repository) {
            echo "REPOSITORY: $repository"
       }
       if(env.pusher) {
            echo "PUSHER: $pusher"
       }
    }

    stage('Load Webhook Data') {
        script {
            if(env.ref && env.repository && env.pusher) {

                echo "READING JSON"
                repositoryBranchApp   = ref.replaceAll("refs/heads/","")
                processRepository  = readJSON text: repository
                processPusher      = readJSON text: pusher
                repositoryName     = processRepository.clone_url.replaceAll("https://","");
                repositoryUser     = processPusher.name
                getBuildUser       = repositoryUser

                echo "USER: ${repositoryUser}";
                echo "EMAIL: ${processRepository.owner.email}";
                echo "BRANCH: ${repositoryBranchApp}";
                echo "REPOSITORY: ${repositoryName}";
            }
        }
    }
}

pipeline {
    agent any

    tools {
        maven 'maven_3.6.3'
        jdk 'openjdk11'
    }

    environment {

        versionProject = ''
        nameProject = ''
        buildNumber = ''
        publishPath = ''
        envGitHubCredentials = credentials('github-enterprise-cicd-account')
        envSonarCredentials = credentials('sonar-cicd-account')
        envRegistryCredentials = credentials('nexus-cicd-account')
    }

    stages {

        stage ('Initialize') {

            steps {
                echo "JAVA_HOME=${tool 'openjdk11'}"
                echo "PATH=${PATH}"
                echo "M2_HOME=${M2_HOME}"
                echo "MAVEN_HOME=${M2_HOME}"
            }
        }

        stage ('Get checksum git') {

            steps {
                deleteDir()
                echo "REPOSITORY BRANCH: ${repositoryBranchApp}"
                echo "REPOSITORY NAME: ${repositoryName}"
                echo "CREDENTIAL OF: ${repositoryUser}"
                echo "CREDENTIAL OF: ${getBuildUser}"

                script {

					try{
						git branch: "${repositoryBranchApp}", url: "https://${repositoryName}", credentialsId: "${getBuildUser}"
					}
					catch (err) {
						echo err.getMessage()
						echo "ERROR USUARIO QUE DISPARO EL EVENTO NO CUENTA CON CREDENECIALES EN JENKINS."
						echo "SE DESCARGA EL CODIGO FUENTE CON USUARIO GENERICO CICD."
						git branch: "${repositoryBranchApp}", url: "https://${envGitHubCredentials_USR}:${envGitHubCredentials_PSW}@${repositoryName}"
					}

                    buildNumber = sh(returnStdout: true, script: 'git rev-parse HEAD')
                }
                echo "BUILD NUMBER: ${buildNumber}"
            }
        }

		stage('Load DevOps Settings') {
			steps {
				script {

					devopsSettingJson = readJSON file: "$WORKSPACE//jenkins.devops.settings.json"

					sonarNovopaymentUrl = devopsSettingJson.SonnarSettings.serverUrl
					sonarProjectKey = devopsSettingJson.SonnarSettings.projectKey
					sonarProjectName = devopsSettingJson.SonnarSettings.projectName

					registryNovopaymentUrl = devopsSettingJson.DockerSettings.registryUrl
					imageName = devopsSettingJson.DockerSettings.imageName

                    apmServiceName = devopsSettingJson.ApmSettings.serviceName
                    apmServerUrl = devopsSettingJson.ApmSettings.serverUrl
                    apmProdServerUrl = devopsSettingJson.ApmSettings.prodServerUrl
                    apmSecretToken = devopsSettingJson.ApmSettings.secretToken


					switch(repositoryBranchApp) {
					  case "master":
						projectEnvironment = "prod"
						apmServerUrl = apmProdServerUrl
						break
					  case "deploy-uat":
						projectEnvironment = "uat"
						break
					  case "release":
						projectEnvironment = "test"
						break
					  case "develop":
						projectEnvironment = "dev"
						break
					  case "feature/feature-jenkins":
                        projectEnvironment = "dev"
                        break
					}
				}
			}
		}

		stage('Build Project') {

            steps {
				script {
					projectVersion = readMavenPom().getVersion()
				}

				echo 'COMPILE PROJECT'
                echo "$WORKSPACE"
                sh "java -version"

				 configFileProvider(
                    [configFile(fileId: 'novo-maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    sh 'mvn -U -s $MAVEN_SETTINGS clean install -Doracle.jdbc.timezoneAsRegion=false'
                }
            }
        }

        stage('Unit Tests') {
            steps {
                configFileProvider([configFile(fileId: 'novo-maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    sh "mvn test -DBUILD_NUMBER='${buildNumber}' -DBRANCH_NAME='${repositoryBranchApp}'"
                   }
            }
        }

        stage('Long-running Verification') {

        	parallel {
        		stage('Spot Bugs Analysis') {
        			steps {
        				configFileProvider([configFile(fileId: 'novo-maven-settings', variable: 'MAVEN_SETTINGS')]) {
        					sh "mvn spotbugs:spotbugs test -DBUILD_NUMBER='${buildNumber}' -DBRANCH_NAME='${repositoryBranchApp}'"
                        }
        			}
        		}

        		stage('SonarQube Code Analysis') {
        			steps {
        				withSonarQubeEnv('sonarqube-novopayment-net') {
        					sh "mvn sonar:sonar -Dsonar.projectName=${sonarProjectName} -Dsonar.projectKey=${sonarProjectKey}"
        				}
        			}
        		}
        	}
        }

        stage('Security Global Verification') {
            parallel {
                stage('SonarQube Quality Gate') {
                    steps {
                        withSonarQubeEnv('sonarqube-novopayment-net') {
                            sleep(30)
                            timeout(time: 5, unit: 'MINUTES') {
                                // PARAMETER INDICATES WHETHER TO SET PIPELINE TO UNSTABLE IF QUALITY GATE FAILS
                                // TRUE = SET PIPELINE TO UNSTABLE, FALSE = DON'T
                                waitForQualityGate (webhookSecretId: 'sonar-cicd-token', abortPipeline: true)
                            }
                        }
                    }
                }
                stage('OWASP Dependency-Check Vulnerabilities') {
                    steps {
                        configFileProvider([configFile(fileId: 'novo-maven-settings', variable: 'MAVEN_SETTINGS')]) {
                            sh "mvn verify -DBUILD_NUMBER='${buildNumber}' -DBRANCH_NAME='${repositoryBranchApp}'"
                        }

                        dependencyCheckPublisher pattern: 'target/dependency-check-report.xml'
                    }
                }
            }
        }

        stage('Docker image') {

            when {
                anyOf {
					expression{
                        repositoryBranchApp == 'master' ||
                        repositoryBranchApp == 'deploy-uat' ||
                        repositoryBranchApp == 'release' ||
                        repositoryBranchApp == 'develop'
					}
                }
			}
                steps {

					script {
                        echo 'projectVersion: ' + projectVersion
                        echo 'projectEnvironment ' + projectEnvironment
                        echo 'apmServerUrl ' + apmServerUrl
                        sh 'mkdir apm'
                        sh 'cp /var/jenkins_home/elastic-apm-agent-1.19.0.jar ./apm/'
                        sh "docker builder prune"
                        sh "docker build --build-arg SERVICE_NAME=${apmServiceName} --build-arg SERVER_URL=${apmServerUrl} --build-arg SECRET_TOKEN=${apmSecretToken} --build-arg ENVIRONMENT_APP=${projectEnvironment} -t ${imageName}:${projectEnvironment}-${projectVersion} ."
                    }
            }
        }

        stage('Push image Novopayment Registry') {

            when {
                anyOf {
                        expression{
                           repositoryBranchApp == 'master' ||
                           repositoryBranchApp == 'deploy-uat' ||
                           repositoryBranchApp == 'release' ||
                           repositoryBranchApp == 'develop'
						}
                    }
                }
                steps {
                    script {

                        sh "docker login -u ${envRegistryCredentials_USR} -p ${envRegistryCredentials_PSW} ${registryNovopaymentUrl}"
                        sh "docker image tag ${imageName}:${projectEnvironment}-${projectVersion} ${registryNovopaymentUrl}/${imageName}:${projectEnvironment}-${projectVersion}"
                        sh "docker push ${registryNovopaymentUrl}/${imageName}:${projectEnvironment}-${projectVersion}"
                    }
                }
        }

        stage ('Archive Files') {
            parallel {
                stage('Archive jars') {
                    steps {
                        archiveArtifacts(artifacts: 'target/*.jar', allowEmptyArchive: true)
                    }
                }
            }
        }
    }

    post {
        always {

            echo 'GET REPORT JUNIT'
            junit(testResults: 'target/test-results/jacoco/**/*.html', allowEmptyResults: true)
            junit(testResults: 'target/test-results/surefire/**/*.xml', allowEmptyResults: true)

            echo 'GET REPORTS GENERAL'
            archiveArtifacts(artifacts: 'target/test-results/jacoco/**/*.html', allowEmptyArchive: true)
            archiveArtifacts(artifacts: 'target/test-results/surefire/**/*.xml', allowEmptyArchive: true)
            archiveArtifacts(artifacts: 'target/test-results/surefire/**/*.xml', allowEmptyArchive: true)

            echo 'ZIPING ALL REPORTS'
            zip zipFile: 'test-reports.zip', archive: true, glob: 'target/**'
            archiveArtifacts(artifacts: 'target/release-*.zip', allowEmptyArchive: true)

            echo 'ZIPING OWASP REPORT'
            zip zipFile: 'owasp-reports.zip', archive: true, glob: 'target/dependency-check-report.html'
            archiveArtifacts(artifacts: 'target/release-*.zip', allowEmptyArchive: true)

            emailext body: '${DEFAULT_CONTENT}', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: '${DEFAULT_SUBJECT}', to: 'lblanco@novopayment.com, devops@novopayment.com, infra@novopayment.com'
        }
    }
}
