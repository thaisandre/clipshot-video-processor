#!/bin/bash

# iniciando a imagem
#docker build -t clipshot-video-processor:latest .
#docker tag clipshot-video-processor:latest thaisandre/clipshot-video-processor:latest
#docker push thaisandre/clipshot-video-processor:latest
#docker buildx build -t clipshot-video-processor:latest:clipshot-video-processor:latest .
#docker push clipshot-video-processor:latest
#docker buildx build -t thaisandre/clipshot-video-processor:latest --push .

#docker buildx build -t clipshot-video-processor:latest .
#docker buildx build -t clipshot-video-processor:latest --push .

#docker build -t clipshot-video-processor:latest .

#
#echo "Aguardando o cluster EKS estar ativo..."
#aws eks wait cluster-active --name tastytap-cluster
#
#echo "Atualizando o kubeconfig para o cluster EKS..."S
#aws eks update-kubeconfig --name "tastytap-cluster" --region "us-east-1"

cd kubernetes
echo "Iniciando o deploy..."

FILES=(
  "mysql-secrets.yaml"
  "mysql-deployment.yaml"
  "app-secrets.yaml"
  "app-deployment.yaml"
  "ingress.yaml"
)

for FILE in "${FILES[@]}"; do
  if [ -f "$FILE" ]; then
    echo "Aplicando $FILE..."
    kubectl apply -f "$FILE"
  else
    echo "Arquivo $FILE não encontrado!"
  fi
done

EXTERNAL_IP=$(kubectl get nodes -o wide | grep -v NAME | awk '{print $6}')
echo "EXTERNAL-IP: $EXTERNAL_IP"

echo "Todas as configurações foram aplicadas."