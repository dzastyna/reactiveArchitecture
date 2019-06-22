#!/bin/sh

eval $(minikube docker-env)

cd arqlibrarian-catalogue.rest
mvn clean install
docker build -t arq-catalogue:latest --rm=true .

kubectl delete service catalogue
kubectl delete deployment catalogue

kubectl create -f catalogue-deployment.yaml
kubectl create -f catalogue-service.yaml

kubectl get services
cd ..