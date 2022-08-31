# Novopayment Help for microservices deployment

# Deploy Commands

```bash
$ NAMESPACE="test-backend"
$ VALUE_YML="values-api-tbs-switching-bridge-orchestrator-microservice.yaml"
$ RELEASE_NAME="api-tbs-switching-bridge-orchestrator-microservice"
$ REPOSITORY_HELM="helm-novo/novo-microservices-helm-template-test-novo-b2b-connector"
$ VERSION_PACKAGE=1.0.0
$ helm install -f "${VALUE_YML}" "${RELEASE_NAME}" "${REPOSITORY_HELM}" --version "${VERSION_PACKAGE}" -n "${NAMESPACE}" --set novo.kubernetes.deploymentEnviroment="test" --set replicaCount=3
```

# Upgrade Commands

```bash
$ NAMESPACE="test-backend"
$ VALUE_YML="values-api-tbs-switching-bridge-orchestrator-microservice.yaml"
$ RELEASE_NAME="api-tbs-switching-bridge-orchestrator-microservice"
$ VERSION_PACKAGE=1.0.0
$ REPOSITORY_HELM="helm-novo/novo-microservices-helm-template-test-novo-b2b-connector"$ helm upgrade -f "${VALUE_YML}" "${RELEASE_NAME}" "${REPOSITORY_HELM}" --version "${VERSION_PACKAGE}" -n "${NAMESPACE}" --set novo.kubernetes.deploymentEnviroment="test" --description "Upgrade business rules"
```

# Undeploy Commands

```bash
$ NAMESPACE="test-backend"
$ VALUE_YML="values-api-tbs-switching-bridge-orchestrator-microservice.yaml"
$ RELEASE_NAME="api-tbs-switching-bridge-orchestrator-microservice"
$ REPOSITORY_HELM="helm-novo/novo-microservices-helm-template-test-novo-b2b-connector"$ helm uninstall "${RELEASE_NAME}" -n "${NAMESPACE}"
```