cd kubernetes/queues

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

#aws --endpoint-url=http://192.168.49.2:30000 s3 mb s3://clipshot-videos
#aws --endpoint-url=http://192.168.49.2:30000 s3 mb s3://clipshot-frames