# !/bin/sh

echo "Undeploy Commands"

NAMESPACE="${1:-dev}-api-backend"
RELEASE_NAME="api-tbs-switching-bridge-orchestrator-microservice"
helm uninstall "${RELEASE_NAME}" -n "${NAMESPACE}"