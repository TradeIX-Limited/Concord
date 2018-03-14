kubectl create configmap corda --from-file=config
kubectl apply -f services/
kubectl apply -f deployments/
