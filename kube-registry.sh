#!/bin/sh

eval $(minikube docker-env)

cd arqlibrarian-registry
mvn clean install
docker build -t arq-registry:latest --rm=true .

kubectl delete service registry
kubectl delete deployment registry

kubectl create -f registry-deployment.yaml
kubectl create -f registry-service.yaml

kubectl get services
cd ..