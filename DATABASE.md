# Database Documentation

## Overview
This document describes the database structure for the Zyra Travel Service application. The application uses PostgreSQL as its primary database.

## Database Setup
1. Create a PostgreSQL database named `zyra_travel`
2. The application will automatically create the tables using `schema.sql`
3. Initial data will be populated using `data.sql`

## Database Schema

### Users and Authentication
- `users`: Stores user information and profile details
- `roles`: Defines user roles (ADMIN, USER)
- `user_roles`: Maps users to their roles

### Travel Packages
- `travel_packages`: Main table for travel package information
- `package_availabilities`: Stores availability dates and capacity for packages

### Bookings and Payments
- `bookings`: Records travel package bookings
- `payments`: Tracks payment transactions
- `payment_methods`: Available payment methods

### Reviews
- `reviews`: User reviews for travel packages

## Table Relationships

### Users & Roles
- One user can have multiple roles (Many-to-Many through user_roles)
- Each role can be assigned to multiple users

### Travel Packages & Bookings
- One travel package can have multiple availabilities (One-to-Many)
- One availability can have multiple bookings (One-to-Many)
- One user can have multiple bookings (One-to-Many)

### Bookings & Payments
- One booking can have multiple payments (One-to-Many)
- Each payment is associated with one payment method (Many-to-One)

### Reviews
- One booking can have one review (One-to-One)
- One user can have multiple reviews (One-to-Many)
- One travel package can have multiple reviews (One-to-Many)

## Important Notes
1. All tables include audit columns (created_at, updated_at)
2. Soft delete is not implemented - records are permanently deleted
3. Passwords in the users table are stored using BCrypt encryption
4. Money values are stored using DECIMAL(10,2) for precision 