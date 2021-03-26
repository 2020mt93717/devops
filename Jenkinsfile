pipeline {

  /* Configure this pipeline to run in slave node*/
  agent {
    node {
       label 'slave'
       customWorkspace "${env.JOB_WORKSPACE}"
    }
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
      
        sh "cp target/DevOpsCICD.war ${env.CATALINA_HOME}/webapps/"
        
      }
    }
    
    /* 4. Wait for predefined time. This is needed so that tomcat can deploy the war */
    stage ("Wait Prior to Running Automated Test") {
    
      steps {
        
        echo "Waiting ${env.CATALINA_DEPLOY_WAIT_TIME} seconds for deployment to complete prior to starting automated testing"
        
        sleep "${env.CATALINA_DEPLOY_WAIT_TIME}" // seconds
        
       }
    }

    /* 5. Run automated acceptance testing on tomcat staging server */
    stage('Automated Acceptance Test - Staging') {

      steps {
      
        sh 'chmod +x target/test-classes/scripts/run_acceptance_test.sh'
        
        sh "./target/test-classes/scripts/run_acceptance_test.sh -h ${env.CATALINA_URL} -a DevOpsCICD"
        
      }
    }
    
    /* 6. Wait for user to finish sanity test on Stage */
    stage('Sanity check') {

      steps {

        input "Test ${env.CATALINA_URL}/DevOpsCICD URL. Does the staging environment look ok?"

      }
    }

    /* 7. Promote the war to production runnin on amazon ec2 tomcat */
    stage('Deploy - Production') {

      steps {

        sh '''echo deploy to production EC2 Tomcat'''
        
        sshagent(['AWS_EC2_Prod_Key']) {
        
          sh "scp -o StrictHostKeyChecking=no target/DevOpsCICD.war  ec2-user@35.154.130.100:/opt/apache-tomcat-8.5.64/webapps/"            
        }

        /*sh './deploy production'*/
      }
    }
    
    /* 8. Wait for predefined time. This is needed so that tomcat can deploy the war */
    stage ("Wait Prior to Running Automated Test") {
    
      steps {
        
        echo "Waiting ${env.CATALINA_DEPLOY_WAIT_TIME} seconds for deployment to complete prior to starting automated testing"
        
        sleep "${env.CATALINA_DEPLOY_WAIT_TIME}" // seconds
        
       }
    }
    
  }

  post {
    success {
      sh "echo Sending Mail Sent Build is Successful"
      mail to: '2020mt93717@wilp.bits-pilani.ac.in',
        subject: "Success Pipeline: ${currentBuild.fullDisplayName}",
        body: "Good with ${env.BUILD_URL}"
      sh "echo Mail Sent To 2020mt93717@wilp.bits-pilani.ac.in"
    }
    unstable {
        sh "echo Sending Mail Sent Build is Unstable"
      mail to: '2020mt93717@wilp.bits-pilani.ac.in',
        subject: "Unstable Pipeline: ${currentBuild.fullDisplayName}",
        body: "Unstable with ${env.BUILD_URL}"
      sh "echo Mail Sent To 2020mt93717@wilp.bits-pilani.ac.in"
    }
    failure {
       sh "echo Sending Mail Sent Build Failed"
      mail to: '2020mt93717@wilp.bits-pilani.ac.in',
        subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
        body: "Something is wrong with ${env.BUILD_URL}"
      sh "echo Mail Sent To 2020mt93717@wilp.bits-pilani.ac.in"
    }
  }
}