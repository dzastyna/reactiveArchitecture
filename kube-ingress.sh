#!/bin/sh

kubectl delete service gateway-ingress
kubectl delete deployment gateway-ingress

kubectl create -f gateway-ingress-deployment.yaml
kubectl create -f gateway-ingress-service.yaml

kubectl get services
cd ..

minikube service gateway-ingress