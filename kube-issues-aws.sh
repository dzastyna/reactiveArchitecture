#!/bin/sh

cd arqlibrarian-issues.rest
mvn clean install
docker build -t msieraczkiewicz/arq-issues:latest --rm=true .
docker push msieraczkiewicz/arq-issues:latest

kubectl delete service issues
kubectl delete deployment issues

kubectl create -f issues-deployment-aws.yaml
kubectl create -f issues-service-aws.yaml

kubectl get services
cd ..