final String publicRepo = 'https://github.com/kemitix/'
final String mvn = "mvn --batch-mode --update-snapshots --errors"
final dependenciesSupportJDK = 10

pipeline {
    agent any
    stages {
        stage('Build & Test') {
            steps {
                withMaven(maven: 'maven', jdk: 'JDK 1.8') {
                    sh "${mvn} clean test"
                    // PMD to Jenkins
                    pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
                }
            }
        }
        stage('Report Coverage') {
            steps {
                withMaven(maven: 'maven', jdk: 'JDK 1.8') {
                    // Code Coverage to Jenkins
                    jacoco exclusionPattern: '**/*{Test|IT|Main|Application|Immutable}.class'
                    // Code Coverage to Codacy
                    sh "${mvn} -pl builder jacoco:report com.gavinmogan:codacy-maven-plugin:coverage " +
                            "-DcoverageReportFile=target/site/jacoco/jacoco.xml " +
                            "-DprojectToken=`$JENKINS_HOME/codacy/token` " +
                            "-DapiToken=`$JENKINS_HOME/codacy/apitoken` " +
                            "-Dcommit=`git rev-parse HEAD`"
                }
            }
        }
        stage('Report Checkstyle') {
            steps {
                withMaven(maven: 'maven', jdk: 'JDK 1.8') {
                    // Checkstyle to Jenkins
                    step([$class: 'hudson.plugins.checkstyle.CheckStylePublisher',
                          pattern: '**/target/checkstyle-result.xml',
                          healthy:'20',
                          unHealthy:'100'])
                }
            }
        }
        stage('Verify & Install') {
            steps {
                withMaven(maven: 'maven', jdk: 'JDK 1.8') {
                    sh "${mvn} --activate-profiles verify install"
                }
            }
        }
        stage('SonarQube (published)') {
            when { expression { isPublished(publicRepo) } }
            steps {
                withSonarQubeEnv('sonarqube') {
                    withMaven(maven: 'maven', jdk: 'JDK 1.8') {
                        sh "${mvn} org.sonarsource.scanner.maven:sonar-maven-plugin:3.4.0.905:sonar"
                    }
                }
            }
        }
        stage('Deploy (published release branch)') {
            when {
                expression {
                    (isReleaseBranch() &&
                            isPublished(publicRepo) &&
                            notSnapshot())
                }
            }
            steps {
                withMaven(maven: 'maven', jdk: 'JDK 1.8') {
                    sh "${mvn} -pl ruleset,tile --activate-profiles release deploy"
                }
            }
        }
        stage('Build Java 10') {
            when { expression { dependenciesSupportJDK >= 10 } }
            steps {
                withMaven(maven: 'maven', jdk: 'JDK 10') {
                    sh "${mvn} clean --activate-profiles verify verify -Djava.version=10"
                }
            }
        }
        stage('Build Java 11') {
            when { expression { dependenciesSupportJDK >= 10 } }
            steps {
                withMaven(maven: 'maven', jdk: 'JDK 11') {
                    sh "${mvn} clean --activate-profiles verify verify -Djava.version=11"
                }
            }
        }
    }
}

private boolean isReleaseBranch() {
    return branchStartsWith('release/')
}

private boolean branchStartsWith(final String branchName) {
    startsWith(env.GIT_BRANCH, branchName)
}

private boolean isPublished(final String repo) {
    startsWith(env.GIT_URL, repo)
}

private static boolean startsWith(final String value, final String match) {
    value != null && value.startsWith(match)
}

private boolean notSnapshot() {
    return !(readMavenPom(file: 'pom.xml').version).contains("SNAPSHOT")
}
