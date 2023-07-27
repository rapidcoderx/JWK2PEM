#!/bin/bash

# prompt for the environment variables
echo -n "Enter the KID and press [ENTER]: "
read KID
echo -n "Enter the OKTA_DOMAIN and press [ENTER]: "
read OKTA_DOMAIN
echo -n "Enter the ENDPOINT and press [ENTER]: "
read ENDPOINT

# base64 encode the variables
KID_BASE64=$(echo -n "$KID" | base64)
OKTA_DOMAIN_BASE64=$(echo -n "$OKTA_DOMAIN" | base64)
ENDPOINT_BASE64=$(echo -n "$ENDPOINT" | base64)

# create the secret
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Secret
metadata:
  name: my-secret
type: Opaque
data:
  KID: $KID_BASE64
  OKTA_DOMAIN: $OKTA_DOMAIN_BASE64
  ENDPOINT: $ENDPOINT_BASE64
EOF
