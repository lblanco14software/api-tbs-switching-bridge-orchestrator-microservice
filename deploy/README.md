# Novopayment Guide for microservices deployment 

Connect to Novopayment Nexus Helm Repository

    helm repo add helm-novo https://nexus.novopayment.net/repository/Helm/ --username devops-user-development --password Dev.Nov$Nex$u$@2020

Update repositories in helm

    helm repo update

Deploy to kubernetes:

    helm upgrade --install api-tbs-switching-bridge-orchestrator-microservice -f .\microservices-deploy-template\values-api-tbs-switching-bridge-orchestrator-microservice.yaml helm-novo/novo-microservices-datasource-helm-template

Show status:

    kubectl get pods -n dev-backend | grep api-tbs-switching-bridge-orchestrator-microservice
