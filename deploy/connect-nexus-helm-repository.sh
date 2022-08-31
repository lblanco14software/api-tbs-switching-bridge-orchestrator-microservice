#!/bin/sh

echo "Connect Nexus Repository"
helm repo add helm-novo https://nexus.novopayment.net/repository/Helm/ --username devops-user-development --password Dev.Nov$Nex$u$@2020
helm repo update