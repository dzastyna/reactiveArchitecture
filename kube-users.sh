#!/bin/sh

eval $(minikube docker-env)

cd arqlibrarian-users.rest
mvn clean install
docker build -t arq-users:latest --rm=true .

kubectl delete service users
kubectl delete deployment users

kubectl create -f users-deployment.yaml
kubectl create -f users-service.yaml

kubectl get services
cd ..
