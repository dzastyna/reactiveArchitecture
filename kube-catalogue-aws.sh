#!/bin/sh

cd arqlibrarian-catalogue.rest
mvn clean install
docker build -t msieraczkiewicz/arq-catalogue:latest --rm=true .

docker push msieraczkiewicz/arq-catalogue:latest

kubectl delete service catalogue
kubectl delete deployment catalogue

kubectl create -f catalogue-deployment-aws.yaml
kubectl create -f catalogue-service-aws.yaml

kubectl get services
cd ..