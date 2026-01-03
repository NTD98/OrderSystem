#!/bin/bash

# Build OrderApi
echo "Building OrderApi..."
cd OrderApi/OrderApi
./mvnw clean package -DskipTests
docker build -t order-api:latest .
cd ../..

# Build OrderStatusApi
echo "Building OrderStatusApi..."
cd OrderStatusApi
./mvnw clean package -DskipTests
docker build -t order-status-api:latest .
cd ..

# Build OrderProcessingConsumer
echo "Building OrderProcessingConsumer..."
cd OrderProcessingConsumer/OrderProcessingConsumer
./mvnw clean package -DskipTests
docker build -t order-processing-consumer:latest .
cd ../..

# Redeploy with Helm
echo "Redeploying with Helm..."
helm upgrade --install order-system ./helm/order-system
