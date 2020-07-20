job(“Job1”){
description(“Pull the data from github.”)
scm{
github(“viveks2612/DevOps-task6”,”master”)
}
triggers {
scm(“* * * * *”)
upstream(‘seedjiob’, ‘SUCCESS’)
}
steps{
shell(readFileFromWorkspace(‘index.html’))
}
}
job("launch kubernetes") {
description ("Start website")
  triggers {
        upstream('website_code_pull', 'SUCCESS')
    }
   steps {
        shell('''#!/bin/bash -xe
cp -rvf /root/.jenkins/workspace/website_code_pull/* .
if ls | grep .html
then
if kubectl get deployment | awk '{print $1}' | grep htmlwebsite
then

kubectl delete all --all
kubectl delete pvc --all
sleep 10 

fi 
kubectl delete pvc --all 
kubectl create -f httpdeply.yml
sleep 5
export html=$(ls | grep .html)
export htmlpod=$(kubectl get pods | grep htmlwebsite | awk '{print$1}')
sleep 4
kubectl cp $(echo $html) $(echo $htmlpod):/usr/local/apache2/htdocs/
 
elif ls | grep .php
then
if kubectl get deployment | awk '{print $1}' | grep phpwebsite 
then
kubectl delete all --all
kubectl delete pvc --all
sleep 10
fi
kubectl delete pvc --all 
kubectl create -f phpdeploy.yml
sleep 5
export php=$(ls | grep .html)
export phpod=$(kubectl get pods | grep phpwebsite | awk '{print$1}') 
sleep 4
kubectl cp $(echo $php) $(echo $phpod):/var/www/html/
else 
echo "Nothing found" 
fi''')
    }
}
