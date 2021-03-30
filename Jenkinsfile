pipeline {

  /* Configure this pipeline to run in slave node*/
  agent {
    node {
       label 'slave'
    }
  }

  /* 
   * ------------------------
   * Pipeline stages
   * ------------------------
   * 1. Build - build using Maven war package
   * 2. Test  - run maven surefire junit unit tests
   * 3. Deploy to Local Tomcat Staging server 
   * 4. Wait for predefined time. This is needed so that tomcat can deploy the war
   * 5. Run automated acceptance testing on tomcat staging server
   * 6. Wait for user to finish sanity test on Stage
   * 7. Promote the war to production runnin on amazon ec2 tomcat
   * 8. Wait for predefined time. This is needed so that tomcat can deploy the war
   * 9. Run automated acceptance testing on tomcat staging server
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
        
          /* Collect Unit test results. It doesnt matter if its sucessful or failed */
          junit 'target/surefire-reports/*.xml'

        }
      }
    }

    /* 3. Deploy to Local Tomcat Staging server and perform smoke test */
    stage('Deploy - Staging') {

      steps {
      
        /* Move DevOps War to tomcat webapps folder */
        sh "cp target/DevOpsCICD.war ${env.STAGE_CATALINA_HOME}/webapps/"
        
      }
    }
    
    /* 4. Wait for predefined time. This is needed so that tomcat can deploy the war */
    stage ("Wait Prior to Running Automated Test - Stage") {
    
      steps {
        
        echo "Waiting ${env.DEPLOY_WAIT_TIME} seconds for deployment to complete prior to starting automated testing"
        
        sleep "${env.DEPLOY_WAIT_TIME}" // seconds
        
       }
    }

    /* 5. Run automated acceptance testing on tomcat staging server */
    stage('Automated Acceptance Test - Staging') {

      steps {
      
        /* update permission so that script can be run from commandline */
        sh 'chmod +x target/test-classes/scripts/run_acceptance_test.sh'
        
        /* run the script. Stage fails if Script exits with non 0 */
        sh "./target/test-classes/scripts/run_acceptance_test.sh -h ${env.STAGE_CATALINA_URL} -a DevOpsCICD"
        
      }
    }
    
    /* 6. Wait for user to finish sanity test on Stage */
    stage('Sanity check') {

      steps {
        
        /* Wait for user to finish testing and provide input to the job */
        input "Test ${env.STAGE_CATALINA_URL}/DevOpsCICD URL. Does the staging environment look ok?"

      }
    }

    /* 7. Promote the war to production runnin on amazon ec2 tomcat */
    stage('Deploy - Production') {

      steps {

        sh '''echo deploy to production EC2 Tomcat'''
        
        /* Use ssh agent with creds key pair for the ec2 instance */
        /* All code which runs insiden sshagent block has this authorization */
        sshagent(['AWS_EC2_Prod_Key']) {
        
           /* Copy the war to EC2 instance */
           sh "scp -o StrictHostKeyChecking=no target/DevOpsCICD.war ${PROD_CATALINA_HOME}/webapps/"            
        }

        /*sh './deploy production'*/
      }
    }
    
    /* 8. Wait for predefined time. This is needed so that tomcat can deploy the war */
    stage ("Wait Prior to Running Automated Test - Production") {
    
      steps {
        
        echo "Waiting ${env.DEPLOY_WAIT_TIME} seconds for deployment to complete prior to starting automated testing"
        
        sleep "${env.DEPLOY_WAIT_TIME}" // seconds
        
       }
    }
    

    /* 9. Run automated acceptance testing on tomcat production server */
    stage('Automated Acceptance Test - Production') {

      steps {
      
        /* update permission so that script can be run from commandline */
        sh 'chmod +x target/test-classes/scripts/run_acceptance_test.sh'
        
        /* run the script. Stage fails if Script exits with non 0 */
        sh "./target/test-classes/scripts/run_acceptance_test.sh -h ${env.PROD_CATALINA_URL} -a DevOpsCICD"
        
      }
    }
    
  }

  /* This blocks always runs after all stages */ 
  post {
   
    /* All stages were successful */
    success {
    
      sh "echo Sending Mail For Successful Build"
      
      /* Send Mail to User notifying that build is good */ 
      mail to: '2020mt93717@wilp.bits-pilani.ac.in',
        subject: "Success Pipeline: ${currentBuild.fullDisplayName}",
        body: "Good with ${env.BUILD_URL}"
        
      sh "echo Mail Sent To 2020mt93717@wilp.bits-pilani.ac.in"
    }
    unstable {
      
      sh "echo Sending Mail For Unstable Build"
      
      /* Send Mail to User notifying that build is unstable */ 
      mail to: '2020mt93717@wilp.bits-pilani.ac.in',
        subject: "Unstable Pipeline: ${currentBuild.fullDisplayName}",
        body: "Unstable with ${env.BUILD_URL}"
        
      sh "echo Mail Sent To 2020mt93717@wilp.bits-pilani.ac.in"
    }
    failure {
      sh "echo Sending Mail For Failed Build"
      
      /* Send Mail to User notifying that build has Failed */ 
      mail to: '2020mt93717@wilp.bits-pilani.ac.in',
        subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
        body: "Something is wrong with ${env.BUILD_URL}"
        
      sh "echo Mail Sent To 2020mt93717@wilp.bits-pilani.ac.in"
    }
  }
}