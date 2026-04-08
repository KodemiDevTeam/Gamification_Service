pipeline {

    agent any

    options {
        disableConcurrentBuilds()
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }

    environment {
        JAVA_HOME = '/opt/java/openjdk'
        MAVEN_HOME = '/usr/share/maven'
        PATH = "/opt/java/openjdk/bin:/usr/share/maven/bin:/usr/bin:/bin:/usr/local/bin"

        SONAR_PROJECT_KEY  = 'Gamification-Service'
        SONAR_PROJECT_NAME = 'Gamification-Service'
    }

    stages {

        /* ================= CLEAN ================= */

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        /* ================= CHECKOUT ================= */

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        /* ================= DEBUG ================= */

        stage('Debug Workspace') {
            steps {
                sh '''
                    echo "===== WORKSPACE DEBUG ====="
                    pwd
                    ls -la
                    find . -name pom.xml
                '''
            }
        }

        /* ================= BUILD ================= */

        stage('Build (No Tests)') {
            steps {
                sh '''
                    echo "===== BUILD WITHOUT TESTS ====="

                    mvn -B clean install \
                    -Dmaven.test.skip=true \
                    -Deureka.client.enabled=false \
                    -Dspring.cloud.discovery.enabled=false
                '''
            }
        }

        /* ================= SONAR ================= */

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube2') {
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        sh '''
                            echo "===== SONAR ANALYSIS ====="

                            mvn -B sonar:sonar \
                            -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                            -Dsonar.projectName=$SONAR_PROJECT_NAME \
                            -Dsonar.login=$SONAR_TOKEN \
                            -Dsonar.sources=src/main/java \
                            -Dsonar.exclusions=**/target/**,**/*.log \
                            -Dsonar.scm.disabled=true
                        '''
                    }
                }
            }
        }

        /* ================= QUALITY GATE ================= */

        stage('Quality Gate') {
            steps {
                script {
                    try {
                        timeout(time: 10, unit: 'MINUTES') {
                            def qg = waitForQualityGate()
                            echo "Quality Gate Status: ${qg.status}"

                            if (qg.status != 'OK') {
                                currentBuild.result = 'UNSTABLE'
                            }
                        }
                    } catch (Exception e) {
                        echo "Quality Gate skipped"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        /* ================= OWASP ================= */

        stage('OWASP Dependency Check') {
            steps {
                withCredentials([string(credentialsId: 'nvd-api-key', variable: 'NVD_API_KEY')]) {
                    dependencyCheck(
                        additionalArguments: "--nvdApiKey $NVD_API_KEY --format XML --format HTML",
                        odcInstallation: 'Default'
                    )
                }
            }
        }

        stage('Publish OWASP Report') {
            steps {
                dependencyCheckPublisher pattern: 'dependency-check-report.xml'
            }
        }

        /* ================= ARCHIVE ================= */

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'dependency-check-report.*',
                                 fingerprint: true
            }
        }
    }

    /* ================= POST ================= */

    post {
        success {
            echo 'SUCCESS: Build + Sonar + OWASP completed'
        }
        unstable {
            echo 'UNSTABLE: Quality Gate issues'
        }
        failure {
            echo 'FAILED: Check logs'
        }
        always {
            echo 'Pipeline execution finished'
        }
    }
}
