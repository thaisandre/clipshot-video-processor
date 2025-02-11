#!/bin/bash

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