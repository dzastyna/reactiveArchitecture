#!/bin/sh

eval $(minikube docker-env)

cd arqlibrarian-issues.rest
mvn clean install
docker build -t arq-issues:latest --rm=true .

kubectl delete service issues
kubectl delete deployment issues

kubectl create -f issues-deployment.yaml
kubectl create -f issues-service.yaml

kubectl get services
cd ..