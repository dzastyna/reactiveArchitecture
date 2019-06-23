#!/bin/sh

cd spring-gateway
mvn clean install
docker build -t msieraczkiewicz/spring-gateway:latest --rm=true .
docker push msieraczkiewicz/spring-gateway:latest

kubectl delete service spring-gateway
kubectl delete deployment spring-gateway

kubectl create -f spring-gateway-deployment-aws.yaml
kubectl create -f spring-gateway-service-aws.yaml

kubectl get services
