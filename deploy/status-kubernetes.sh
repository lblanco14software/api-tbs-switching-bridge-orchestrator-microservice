# !/bin/sh

POD_NAME_PATTERN=api-tbs-switching-bridge-orchestrator-microservice
NAMESPACE="${1:-dev}-api-backend"
POD_NAME="$(kubectl get pods -n "${NAMESPACE}" | grep $POD_NAME_PATTERN | awk -F'[ ]' '{print $1;}')"

printf "\n%s\n" "========================================================================================="
echo "Status of Pod $POD_NAME"
kubectl get pods -n "${NAMESPACE}" | grep "$POD_NAME_PATTERN"

printf "\n%s\n" "========================================================================================="
echo "Describe of Pod $POD_NAME"
kubectl -n "${NAMESPACE}" describe pods "$POD_NAME_PATTERN"

printf "\n%s\n" "========================================================================================="
echo "Logs of Pod $POD_NAME"
kubectl -n "${NAMESPACE}" logs "$POD_NAME" --follow