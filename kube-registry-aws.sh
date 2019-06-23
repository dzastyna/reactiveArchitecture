#!/bin/sh

cd arqlibrarian-registry
mvn clean install
docker build -t msieraczkiewicz/arq-registry:latest --rm=true .
docker push msieraczkiewicz/arq-registry:latest

kubectl delete service registry
kubectl delete deployment registry

kubectl create -f registry-deployment-aws.yaml
kubectl create -f registry-service-aws.yaml

kubectl get services
cd ..