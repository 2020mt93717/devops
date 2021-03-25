pipeline {

  /* Configure this pipeline to run in slave node*/
  agent {
    label 'slave'
  }

  /* Pipeline stages
   * 1. Build - build using Maven war package
   * 2. Test  - run maven surefire junit unit tests
   * 3. Deploy to Local Tomcat Staging server and perform smoke test
   * 4. Wait for user to finish sanity test on Stage
   * 5. Promote the war to production runnin on amazon ec2 tomcat
   */
  stages {

    /* 1. Build - build using Maven war package*/
    stage('Build') {

      steps {

        /* run maven clean install and skip tests */
        sh 'mvn -B -DskipTests clean install'
      }

    }

    /* 2. Test  - run maven surefire junit unit tests and collect report*/
    stage('Unit Test') {

      steps {
        /* run maven test goal. This runs surefire mavan plugin for unit tests */
        sh 'mvn test'

      }

      post {

        always {

          junit 'target/surefire-reports/*.xml'

        }
      }
    }

    /* 3. Deploy to Local Tomcat Staging server and perform smoke test */
    stage('Deploy - Staging') {

      steps {
        sh "cp target/jenkins-0.0.1-SNAPSHOT.war ${env.CATALINA_HOME}/webapps/"
        sh 'chmod +x /target/test-classes/scripts/run_smoke_test.sh'
        sh './target/test-classes/scripts/run_smoke_test.sh'
      }
    }

    stage('Sanity check') {

      steps {

        input "Does the staging environment look ok?"

      }
    }

    stage('Deploy - Production') {

      steps {

        sh '''echo deploy to production'''
        /*sh './deploy production'*/
      }
    }
  }

  post {
    success {
      mail to: '2020mt93717@wilp.bits-pilani.ac.in',
        subject: "Success Pipeline: ${currentBuild.fullDisplayName}",
        body: "Good with ${env.BUILD_URL}"
    }
    unstable {
      mail to: '2020mt93717@wilp.bits-pilani.ac.in',
        subject: "Unstable Pipeline: ${currentBuild.fullDisplayName}",
        body: "Unstable with ${env.BUILD_URL}"
    }
    failure {
      mail to: '2020mt93717@wilp.bits-pilani.ac.in',
        subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
        body: "Something is wrong with ${env.BUILD_URL}"
    }
  }
}