pipeline {

  /* Configure this pipeline to run in slave node*/
  agent {
    label 'slave'
  }

  /* Pipeline stages
   * 1. Build - build using Maven war package
   * 2. Test  - run maven surefire junit unit tests
   * 3. Deploy to Local Tomcat Staging server 
   * 4. Wait for predefined time. This is needed so that tomcat can deploy the war
   * 5. Run automated acceptance testing on tomcat staging server
   * 6. Wait for user to finish sanity test on Stage
   * 7. Promote the war to production runnin on amazon ec2 tomcat
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
        
      }
    }
    
    /* 4. Wait for predefined time. This is needed so that tomcat can deploy the war */
    stage ("Wait Prior to Running Automated Test") {
       def time = ${env.CATALINA_DEPLOY_WAIT_TIME}
       echo "Waiting ${env.CATALINA_DEPLOY_WAIT_TIME} seconds for deployment to complete prior to starting automated testing"
       sleep time.toInteger() // seconds
    }

    /* 5. Run automated acceptance testing on tomcat staging server */
    stage('Automated Acceptance Test - Staging') {

      steps {
        sh 'chmod +x target/test-classes/scripts/run_acceptance_test.sh'
        sh "./target/test-classes/scripts/run_acceptance_test.sh -h ${env.CATALINA_URL} -a jenkins-0.0.1-SNAPSHOT"
      }
    }
    
    /* 6. Wait for user to finish sanity test on Stage */
    stage('Sanity check') {

      steps {

        input "Test ${env.CATALINA_URL}/jenkins-0.0.1-SNAPSHOT URL. Does the staging environment look ok?"

      }
    }

    /* 7. Promote the war to production runnin on amazon ec2 tomcat */
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