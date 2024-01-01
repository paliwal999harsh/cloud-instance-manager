# Cloud Instance Manager API - Usage Instructions

## Overview

Welcome to the Cloud Instance Manager API documentation. This API allows you to manage instances and leases for cloud services like AWS and Azure. Below are instructions on how to use the provided endpoints.

## Base URL

The base URL for the API is `http://localhost:{port}/cim/api/{basePath}`, where:
- `{port}` can be either '8080' or '8081'.
- `{basePath}` is 'v1' by default.

## Endpoints

### 1. Get Instance Details

**Endpoint:** `/instances/{instanceName}`

**Method:** `GET`

**Description:** Get details of a specific instance.

**Parameters:**
- `instanceName` (path): The unique name of the instance (max length: 15 characters).

**Response:**
- `200 OK`: Instance details retrieved successfully.
- `404 Not Found`: No instance found for the provided `instanceName`.
- `500 Internal Server Error`: Unexpected error.

### 2. Delete Instance

**Endpoint:** `/instances/{instanceName}`

**Method:** `DELETE`

**Description:** Delete a specific instance.

**Parameters:**
- `instanceName` (path): The unique name of the instance (max length: 15 characters).

**Response:**
- `405 Method Not Allowed`: Deleting instances is not allowed.

### 3. Modify State of Instance

**Endpoint:** `/instances/{instanceName}`

**Method:** `PUT`

**Description:** Set the state of a specific instance (start or stop).

**Parameters:**
- `instanceName` (path): The unique name of the instance (max length: 15 characters).
- `setState` (query): The state of the instance, either 'start' or 'stop'.

**Response:**
- `200 OK`: The instance state has been set to the given 'state'.
- `400 Bad Request`: The provided state is invalid.
- `404 Not Found`: No instance found for the provided `instanceName`.

### 4. Read All Instances

**Endpoint:** `/instances/listInstances`

**Method:** `GET`

**Description:** Read all instances available in the cloud.

**Parameters:**
- `cloud` (query): Specify the cloud type (AWS or Azure).

**Response:**
- `200 OK`: List of all instances available in the cloud.
- `204 No Content`: No instances are available in the cloud.
- `400 Bad Request`: Invalid cloud type provided.

### 5. Create Instance

**Endpoint:** `/instances`

**Method:** `POST`

**Description:** Create a new instance.

**Request Body:**
```json
{
  "instanceName": "example-instance",
  "cloud": "AWS"
}
