// non-standard Jenksinfile
// * only publish code coverage to codacy for builder module
// * only deploy artifacts for ruleset and tile modules

final String mvn = "mvn --batch-mode --update-snapshots"

pipeline {
    agent any
    stages {
        stage('Environment') {
            steps {
                sh 'set'
            }
        }
        stage('no SNAPSHOT in master') {
            // checks that the pom version is not a snapshot when the current or target branch is master
            when {
                expression {
                    (env.GIT_BRANCH == 'master' || env.CHANGE_TARGET == 'master') &&
                            (readMavenPom(file: 'pom.xml').version).contains("SNAPSHOT")
                }
            }
            steps {
                error("Build failed because SNAPSHOT version")
            }
        }
        stage('Static Code Analysis') {
            when { expression { findFiles(glob: '**/src/main/java/*.java').length > 0 } }
            steps {
                withMaven(maven: 'maven 3.5.2', jdk: 'JDK 1.8') {
                    sh "${mvn} compile checkstyle:checkstyle pmd:pmd"
                }
                pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
            }
        }
        stage('Build Java 9') {
            steps {
                withMaven(maven: 'maven 3.5.2', jdk: 'JDK 9') {
                    sh "${mvn} clean install"
                }
            }
        }
        stage('Build Java 8') {
            steps {
                withMaven(maven: 'maven 3.5.2', jdk: 'JDK 1.8') {
                    sh "${mvn} clean install"
                }
            }
        }
        stage('Test Results') {
            when { expression { findFiles(glob: '**/target/surefire-reports/*.xml').length > 0 } }
            steps {
                junit '**/target/surefire-reports/*.xml'
                jacoco exclusionPattern: '**/*{Test|IT|Main|Application|Immutable}.class'
                withMaven(maven: 'maven 3.5.2', jdk: 'JDK 1.8') {
                    sh "${mvn} -pl builder com.gavinmogan:codacy-maven-plugin:coverage " +
                            "-DcoverageReportFile=target/site/jacoco/jacoco.xml " +
                            "-DprojectToken=`$JENKINS_HOME/codacy/token` " +
                            "-DapiToken=`$JENKINS_HOME/codacy/apitoken` " +
                            "-Dcommit=`git rev-parse HEAD`"
                }
            }
        }
        stage('Archiving') {
            when { expression { findFiles(glob: '**/target/*.jar').length > 0 } }
            steps {
                archiveArtifacts '**/target/*.jar'
            }
        }
        stage('Deploy') {
            when { expression { (env.GIT_BRANCH == 'master' && env.GIT_URL.startsWith('https://')) } }
            steps {
                withMaven(maven: 'maven 3.5.2', jdk: 'JDK 1.8') {
                    sh "${mvn} -pl ruleset,tile deploy --activate-profiles release -DskipTests=true"
                }
            }
        }
    }
}
