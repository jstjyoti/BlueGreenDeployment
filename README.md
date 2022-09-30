# BlueGreenDeployment
This Repository would contain all the code related to Blud Green Deployment strategy on GKE

A detailed overview of the deployment strategies is presented by google
[here](https://cloud.google.com/architecture/application-deployment-and-testing-strategies).

## Getting started
Export environment variables:
```
gcloud config set project PROJECT_ID
```
Replace PROJECT_ID with the ID of the project where you want to create the cluster.

```
export PROJECT=$(gcloud config get-value project)
```
Create a GKE cluster with Anthos enabled:
```
gcloud container clusters create "test-cluster" \
   --zone "us-central1-a" \
   --scopes "https://www.googleapis.com/auth/cloud-platform" \
   --addons HorizontalPodAutoscaling,HttpLoadBalancing \
```
Get running cluster credentials:
```
gcloud container clusters get-credentials "test-cluster"  \
   --zone "us-central1-a" \
   --project $PROJECT
```
Clone repository:
```
git clone https://github.com/jstjyoti/BlueGreenDeployment
cd BlueGreenDeployment
```

## Blue/Green
Create deployment with the current version of the application:
```
kubectl apply -f bluegreen/deployment-old.yaml
```
Expose the deployment using Kubernetes service:
```
kubectl apply -f bluegreen/service-old.yaml
```
Check if deployment and service created successfully:
```
kubectl rollout status deploy app-01 -w
kubectl get svc/app -w
```
Wait for external IP to be allocated before proceeding. Press CTRL-C to end the watch loop.

On a new terminal, get the service IP and send requests to the current deployment:
```
while(true); \
do \
curl "http://$(kubectl get svc app -o jsonpath="{.status.loadBalancer.ingress[0].ip}"):8080/version"; echo; \
done
```
Create deployment with the new version of the application:
```
kubectl apply -f bluegreen/deployment-old.yaml
```
Check if the new deployment created successfully:
```
kubectl rollout status deploy app-02 -w
```
Update the service to point to the new version:
```
kubectl apply -f bluegreen/service-new.yaml
```
Monitor the response changing on the terminal where curl command was executed.

Cleanup:
```
kubectl delete -f bluegreen/ --ignore-not-found
```
