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