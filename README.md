# API Aggregator - Jobsity
## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Problem Description](#problem-description)
5. [Running the Application](#running-the-application)
   - [Prerequisites](#prerequisites)
   - [Docker Setup](#docker-setup)
   - [Docker File](#docker-file)
   - [Docker Compose File](#docker-compose-file)
6. [Authentication](#authentication)
   - [Using the JWT Token](#using-the-jwt-token)
7. [Endpoints](#endpoints)
   - [Get all contacts](#get-all-contacts)
   - [Get a specific contacts](#get-a-specific-contacts)

## Overview

This project is developed for Jobsity and implements an API Aggregator service. The purpose of this API is to connect to external APIs, retrieve contact information, and make it available to consumers in a unified format. The system supports fetching all contacts or a specific contact by ID, requiring a bearer token for requesting the client resource.


## Features

- Retrieves contact data from external API (Kenect Labs)
- Two main endpoints: list all contacts and fetch a contact by ID
- The client uses a token for making a security request

## Technologies Used

- **Java 21** - Backend logic
- **Spring Boot 3.3.4** - Framework for building the application
- **Spring Cloud OpenFeign** - API client for communicating with external services
- **Lombok** - Reduces boilerplate code
- **Docker & Docker Compose** - Containerization
- **Maven** - Dependency management and build automation

## Problem Description

The API Aggregator connects to external service (Kenect Labs) to retrieve contact information, transforms the data into a common model, and provides it to the consumers through a unified API. 
It is required to use JWT authentication to access the client, ensuring only authorized users can retrieve data. The Bearer token is predefined by the JobSity

## Running the Application

### Prerequisites

Ensure you have the following installed:

- Docker
- Docker Compose
- Java 17 or higher
- Maven

### Docker Setup


This project is set up to run easily using Docker and Docker Compose. To run the application locally:

1. Clone the repository:

         git clone https://github.com/erickvls/api-aggregator.git
         cd api-aggregator


2. Start the application using Docker Compose:
   
         docker-compose up --build

This will build the application and start the API service.

### Docker File
The Dockerfile builds a Maven project and creates a lightweight image to run the Spring Boot application.

### Docker Compose File
The docker-compose.yml file includes services for the application and related configurations.

## Authentication
The API itself does not handle authentication, but it requires a JWT token to be included in the header of all requests to access its endpoints. This token is generated and provided by an external service, and you must obtain it before making any requests to the API.


### Using the JWT Token
1. To perform any operation on the API, the token must be included in the Authorization header in the following format:

       Authorization: Bearer J7ybt6jv6pdJ4gyQP9gNonsY

## Endpoints
The application provides two main endpoints, which are not directly protected but require the client to provide a JWT token in the request header to interact with a third-party API.

### Get all contacts
1. Retrieves a list of contacts. To perform this operation, the client must include the JWT token in the request header, which is used for authentication with the third-party API.

         GET /contacts
         Authorization: Bearer J7ybt6jv6pdJ4gyQP9gNonsY



### Get a specific contact
1. Retrieves a specific contact by its id. Similar to the previous endpoint, the client must include the JWT token in the request header to access the third-party API.

       GET /contacts/{id}
       Authorization: Bearer J7ybt6jv6pdJ4gyQP9gNonsY
