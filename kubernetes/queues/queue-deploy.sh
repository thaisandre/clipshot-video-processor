echo "Iniciando o deploy..."

FILES=(
  "rabbitmq-definitions-configmap.yaml"
  "rabbitmq-deploy.yaml"
  "localstack-deployment.yaml"
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