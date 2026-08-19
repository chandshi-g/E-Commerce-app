# StackCart 🛒

A full-stack e-commerce web application built for practice, covering product catalog, cart, wishlist, orders, and Stripe-based payments.

## Tech Stack

- **Frontend:** Vue.js
- **Backend:** Spring Boot (REST API)
- **Database:** MySQL (Dockerized container)
- **Payments:** Stripe Integration
- **Containerization:** Docker

## Features

- Category & product management
- User authentication (token-based)
- Cart & wishlist functionality
- Order & order item management
- Stripe payment integration
- RESTful API architecture

## Architecture

- Frontend (Vue.js) communicates with the backend via REST APIs
- Backend (Spring Boot) handles business logic, authentication, and order processing
- MySQL runs in a Docker container — no local installation required
- Stripe handles payment processing securely

## Entities

`Category` · `Product` · `User` · `Cart` · `Wishlist` · `Order` · `OrderItem` · `AuthenticationToken`

## Setup & Installation

### Prerequisites

- Java 17+
- Node.js & npm
- Docker