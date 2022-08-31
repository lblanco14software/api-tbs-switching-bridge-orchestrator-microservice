# !/bin/sh

echo "Deploy Commands"
NAMESPACE="${1:-dev}-api-backend"
VALUE_YML="$(pwd)/microservices-deploy-template/values-api-tbs-switching-bridge-orchestrator-microservice.yaml"
RELEASE_NAME="api-tbs-switching-bridge-orchestrator-microservice"
REPOSITORY_HELM="helm-novo/novo-microservices-helm-template-${1:-dev}-api-tbs-switching-bridge-orchestrator-microservice"
VERSION_PACKAGE=3.0.0

helm repo update
helm upgrade --install -f "${VALUE_YML}" "${RELEASE_NAME}" "${REPOSITORY_HELM}" --version ${VERSION_PACKAGE} -n "${NAMESPACE}" --set novo.kubernetes.deploymentEnviroment="${1:-dev}" --set replicaCount=1