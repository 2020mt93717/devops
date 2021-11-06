#!/bin/bash

echo running acceptance test
echo usage pass -a for application Context -h http base url

while getopts a:h: flag
do
   case "${flag}"
   in
     a) app_context=${OPTARG};;
     h) http_url=${OPTARG};;
   esac
done
curl_url="$http_url/$app_context"
echo formed url $curl_url
set -e
curl -sf -o /dev/null $curl_url
echo "$curl_url is working fine"
Runtime r = Runtime.getRuntime();
String[] cmd = {"java", "MyTest"};
String[] env = {"-classpath", testsDir + "/classes"};
Process p = r.exec(cmd, env);
// read output from test using
// p.getInputStream() and p.getErrorStream()
// (best done with a separate thread for each stream)
int rc = p.waitFor();
Status s = (rc == 0 ? Status.passed("OK") : 
Status.failed("process exited with return code " + rc);
// s contains result status from executing command
