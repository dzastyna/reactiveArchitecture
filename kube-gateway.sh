#!/bin/sh

eval $(minikube docker-env)

cd arqlibrarian-gateway
mvn clean install
docker build -t arq-gateway:latest --rm=true .

kubectl delete service gateway
kubectl delete deployment gateway

kubectl create -f gateway-deployment.yaml
kubectl create -f gateway-service.yaml

kubectl get services
cd ..

minikube service gateway
kubectl port-forward svc/gateway 8999:8083