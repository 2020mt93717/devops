pipeline {
  agent {label 'slave'}
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean install'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
      post {
        always {
            junit 'target/surefire-reports/*.xml'
          }
       }
    }
    stage('Deploy') {
      steps {
        sh '''echo Deploy steps'''
      }
    }
  }
    post {
        always {
            echo 'One way or another, I have finished'
        }
        success {
            mail to: '2020mt93717@wilp.bits-pilani.ac.in',
             subject: "Success Pipeline: ${currentBuild.fullDisplayName}",
             body: "Good with ${env.BUILD_URL}"
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            mail to: '2020mt93717@wilp.bits-pilani.ac.in',
             subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
        }
        changed {
            echo 'Things were different before...'
        }
    }
}
