#!/bin/bash

# 변수 설정
AKS_NAME="crew116-aks"
ACR_NAME="crew116cr"
NAMESPACE="plan-management"
APP_NAME="plan-service"
VERSION="1.0.0"
PORT="8080"

# ACR 로그인
echo "Logging into Azure Container Registry..."
az acr login --name $ACR_NAME

# Gradle 빌드
echo "Building with Gradle..."
./gradlew clean build -x test

# Docker 이미지 빌드 및 푸시
echo "Building and pushing Docker images..."
docker build -t ${ACR_NAME}.azurecr.io/${APP_NAME}:${VERSION} .
docker push ${ACR_NAME}.azurecr.io/${APP_NAME}:${VERSION}

# AKS 자격 증명 가져오기
echo "Getting AKS credentials..."
az aks get-credentials --resource-group $AKS_NAME --name $AKS_NAME

# Namespace 생성
echo "Creating namespace if not exists..."
kubectl create namespace $NAMESPACE --dry-run=client -o yaml | kubectl apply -f -

# Deployment 및 Service 배포
cat << EOF | kubectl apply -f -
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ${APP_NAME}
  namespace: ${NAMESPACE}
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ${APP_NAME}
  template:
    metadata:
      labels:
        app: ${APP_NAME}
    spec:
      containers:
      - name: ${APP_NAME}
        image: ${ACR_NAME}.azurecr.io/${APP_NAME}:${VERSION}
        imagePullPolicy: Always
        ports:
        - containerPort: ${PORT}
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
---
apiVersion: v1
kind: Service
metadata:
  name: ${APP_NAME}
  namespace: ${NAMESPACE}
spec:
  type: LoadBalancer
  ports:
  - port: ${PORT}
    targetPort: ${PORT}
  selector:
    app: ${APP_NAME}
EOF

echo "Deployment completed successfully!"

# 배포 상태 확인
echo "Checking deployment status..."
kubectl get pods -n $NAMESPACE
kubectl get services -n $NAMESPACE