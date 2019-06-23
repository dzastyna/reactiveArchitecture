#!/bin/sh

cd arqlibrarian-users.rest
mvn clean install
docker build -t msieraczkiewicz/arq-users:latest --rm=true .

docker push msieraczkiewicz/arq-users:latest

kubectl delete service users
kubectl delete deployment users

kubectl create -f users-deployment-aws.yaml
kubectl create -f users-service-aws.yaml

kubectl get services
cd ..