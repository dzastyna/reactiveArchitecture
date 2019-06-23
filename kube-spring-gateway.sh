#!/bin/sh

eval $(minikube docker-env)

cd spring-gateway
mvn clean install
docker build -t spring-gateway:latest --rm=true .

kubectl delete service spring-gateway
kubectl delete deployment spring-gateway

kubectl create -f spring-gateway-deployment.yaml
kubectl create -f spring-gateway-service.yaml

kubectl get services

cd ..

sleep 5s
kubectl port-forward svc/spring-gateway 8998:8080