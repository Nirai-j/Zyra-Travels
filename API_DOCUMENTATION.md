# Zyra Travel Service API Documentation

This document provides a comprehensive list of all API endpoints available in the Zyra Travel Service, including sample request and response formats.

## Base URL

All endpoints are relative to the base URL: `/api`

## Authentication

Most endpoints require authentication. Authentication is handled via JWT tokens.

## Endpoints

### Authentication

#### Register a new user

**Endpoint:** `POST /auth/register`  
**Authentication Required:** No

**Sample Request:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "securepassword123",
  "phoneNumber": "+1234567890"
}
```

**Sample Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "userId": 1,
  "username": "johndoe"
}
```

#### Login

**Endpoint:** `POST /auth/login`  
**Authentication Required:** No

**Sample Request:**
```json
{
  "username": "johndoe",
  "password": "securepassword123"
}
```

**Sample Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "userId": 1,
  "username": "johndoe"
}
```

#### Logout

**Endpoint:** `POST /auth/logout`  
**Authentication Required:** Yes (Bearer Token)

**Sample Response:**
```json
{
  "message": "Logged out successfully"
}
```

### Users

#### Get current user profile

**Endpoint:** `GET /users/me`  
**Authentication Required:** Yes (Bearer Token)

**Sample Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "username": "johndoe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "roles": ["ROLE_USER"],
  "createdAt": "2025-05-30T10:15:30"
}
```

#### Update current user profile

**Endpoint:** `PUT /users/me`  
**Authentication Required:** Yes (Bearer Token)

**Sample Request:**
```json
{
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@example.com",
  "phoneNumber": "+1987654321"
}
```

**Sample Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Smith",
  "username": "johndoe",
  "email": "john.smith@example.com",
  "phoneNumber": "+1987654321",
  "roles": ["ROLE_USER"],
  "createdAt": "2025-05-30T10:15:30",
  "updatedAt": "2025-05-31T14:20:15"
}
```

### Destinations

#### Get all destinations (paginated)

**Endpoint:** `GET /destinations?page=0&size=10&sortBy=name&sortDir=asc`  
**Authentication Required:** No

**Sample Response:**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Bali",
      "country": "Indonesia",
      "description": "Beautiful island known for its forested volcanic mountains, iconic rice paddies, beaches and coral reefs",
      "imageUrl": "https://example.com/images/bali.jpg",
      "featured": true,
      "popularityScore": 95
    },
    {
      "id": 2,
      "name": "Paris",
      "country": "France",
      "description": "City of lights known for its cafe culture and iconic landmarks",
      "imageUrl": "https://example.com/images/paris.jpg",
      "featured": true,
      "popularityScore": 92
    }
  ],
  "totalPages": 5,
  "totalElements": 50,
  "currentPage": 0,
  "size": 10,
  "first": true,
  "last": false
}
```

#### Create a new destination

**Endpoint:** `POST /destinations`  
**Authentication Required:** Yes (Bearer Token, ADMIN role)

**Sample Request:**
```json
{
  "name": "Santorini",
  "country": "Greece",
  "description": "Stunning island known for its whitewashed, cubiform houses in the town of Oia",
  "imageUrl": "https://example.com/images/santorini.jpg",
  "featured": true
}
```

**Sample Response:**
```json
{
  "id": 3,
  "name": "Santorini",
  "country": "Greece",
  "description": "Stunning island known for its whitewashed, cubiform houses in the town of Oia",
  "imageUrl": "https://example.com/images/santorini.jpg",
  "featured": true,
  "popularityScore": 0,
  "createdAt": "2025-05-31T15:30:45"
}
```

### Travel Packages

#### Get all travel packages (paginated)

**Endpoint:** `GET /packages?page=0&size=10&sortBy=price&sortDir=asc`  
**Authentication Required:** No

**Sample Response:**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Bali Adventure",
      "description": "Experience the beauty of Bali with this 7-day adventure package",
      "price": 1299.99,
      "duration": 7,
      "thumbnailUrl": "https://example.com/images/bali-thumb.jpg",
      "bannerImageUrl": "https://example.com/images/bali-banner.jpg",
      "groupSizeMin": 4,
      "groupSizeMax": 12,
      "featured": true,
      "active": true,
      "averageRating": 4.8,
      "reviewCount": 24
    },
    {
      "id": 2,
      "name": "Paris Getaway",
      "description": "Romantic 5-day trip to the city of lights",
      "price": 1499.99,
      "duration": 5,
      "thumbnailUrl": "https://example.com/images/paris-thumb.jpg",
      "bannerImageUrl": "https://example.com/images/paris-banner.jpg",
      "groupSizeMin": 2,
      "groupSizeMax": 8,
      "featured": true,
      "active": true,
      "averageRating": 4.6,
      "reviewCount": 18
    }
  ],
  "totalPages": 3,
  "totalElements": 25,
  "currentPage": 0,
  "size": 10,
  "first": true,
  "last": false
}
```

#### Create a new package

**Endpoint:** `POST /packages`  
**Authentication Required:** Yes (Bearer Token, ADMIN role)

**Sample Request:**
```json
{
  "name": "Greek Islands Explorer",
  "description": "Explore the beautiful Greek islands including Santorini and Mykonos",
  "price": 2499.99,
  "duration": 10,
  "thumbnailUrl": "https://example.com/images/greek-thumb.jpg",
  "bannerImageUrl": "https://example.com/images/greek-banner.jpg",
  "groupSizeMin": 6,
  "groupSizeMax": 15,
  "featured": true,
  "active": true
}
```

**Sample Response:**
```json
{
  "id": 3,
  "name": "Greek Islands Explorer",
  "description": "Explore the beautiful Greek islands including Santorini and Mykonos",
  "price": 2499.99,
  "duration": 10,
  "thumbnailUrl": "https://example.com/images/greek-thumb.jpg",
  "bannerImageUrl": "https://example.com/images/greek-banner.jpg",
  "groupSizeMin": 6,
  "groupSizeMax": 15,
  "featured": true,
  "active": true,
  "averageRating": null,
  "reviewCount": 0,
  "createdAt": "2025-05-31T16:20:10"
}
```

### Package Availabilities

#### Add availability to a package

**Endpoint:** `POST /packages/{packageId}/availabilities`  
**Authentication Required:** Yes (Bearer Token, ADMIN role)

**Sample Request:**
```json
{
  "startDate": "2025-07-15",
  "endDate": "2025-07-25",
  "availableSpots": 15,
  "price": 2499.99
}
```

**Sample Response:**
```json
{
  "id": 1,
  "travelPackageId": 3,
  "startDate": "2025-07-15",
  "endDate": "2025-07-25",
  "availableSpots": 15,
  "price": 2499.99,
  "createdAt": "2025-05-31T16:25:30"
}
```

### Bookings

#### Create a new booking

**Endpoint:** `POST /bookings`  
**Authentication Required:** Yes (Bearer Token)

**Sample Request:**
```json
{
  "travelPackageId": 3,
  "availabilityId": 1,
  "startDate": "2025-07-15",
  "endDate": "2025-07-25",
  "numberOfTravelers": 2,
  "contactEmail": "john.doe@example.com",
  "contactPhone": "+1234567890",
  "specialRequests": "Vegetarian meals preferred"
}
```

**Sample Response:**
```json
{
  "id": 1,
  "bookingNumber": "ZYRA-25070001",
  "userId": 1,
  "userName": "John Doe",
  "userEmail": "john.doe@example.com",
  "travelPackageId": 3,
  "travelPackageName": "Greek Islands Explorer",
  "availabilityId": 1,
  "startDate": "2025-07-15",
  "endDate": "2025-07-25",
  "numberOfTravelers": 2,
  "totalAmount": 4999.98,
  "contactEmail": "john.doe@example.com",
  "contactPhone": "+1234567890",
  "specialRequests": "Vegetarian meals preferred",
  "status": "PENDING",
  "createdAt": "2025-05-31T16:30:45"
}
```

### Payments

#### Process a payment

**Endpoint:** `POST /payments`  
**Authentication Required:** Yes (Bearer Token)

**Sample Request:**
```json
{
  "bookingId": 1,
  "amount": 4999.98,
  "paymentMethod": "CREDIT_CARD",
  "cardNumber": "4111111111111111",
  "cardHolderName": "John Doe",
  "expiryMonth": 12,
  "expiryYear": 2026,
  "cvv": "123"
}
```

**Sample Response:**
```json
{
  "id": 1,
  "bookingId": 1,
  "transactionId": "txn_1234567890",
  "amount": 4999.98,
  "paymentMethod": "CREDIT_CARD",
  "status": "COMPLETED",
  "createdAt": "2025-05-31T16:35:20"
}
```

### Reviews

#### Create a review for a package

**Endpoint:** `POST /reviews/package/{packageId}`  
**Authentication Required:** Yes (Bearer Token)

**Sample Request:**
```json
{
  "rating": 5,
  "title": "Amazing Experience!",
  "content": "The Greek Islands Explorer package exceeded all my expectations. The accommodations were excellent, the guides were knowledgeable, and the itinerary was perfect. Highly recommend!",
  "travelDate": "2025-07-15"
}
```

**Sample Response:**
```json
{
  "id": 1,
  "packageId": 3,
  "packageName": "Greek Islands Explorer",
  "userId": 1,
  "userName": "John Doe",
  "rating": 5,
  "title": "Amazing Experience!",
  "content": "The Greek Islands Explorer package exceeded all my expectations. The accommodations were excellent, the guides were knowledgeable, and the itinerary was perfect. Highly recommend!",
  "travelDate": "2025-07-15",
  "approved": false,
  "featured": false,
  "helpfulCount": 0,
  "createdAt": "2025-08-05T10:15:30"
}
```

## Query Parameters

Many endpoints support the following query parameters for pagination:

- `page`: Page number (zero-based, default: 0)
- `size`: Page size (default: 10)
- `sortBy`: Field to sort by
- `sortDir`: Sort direction (asc or desc)

## Response Formats

All responses are in JSON format.

## Error Handling

The API uses standard HTTP status codes:

- 200: Success
- 201: Created
- 204: No Content
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

**Sample Error Response:**
```json
{
  "timestamp": "2025-05-31T16:40:25",
  "message": "Resource not found",
  "details": "Travel package with id 999 does not exist",
  "errorCode": "RESOURCE_NOT_FOUND"
}
```

## Authentication Roles
- `ROLE_USER`: Basic authenticated user
- `ROLE_ADMIN`: Administrative user with full access

## Notes
1. All dates should be in ISO 8601 format
2. All monetary values are in the default currency (USD)
3. File uploads have a maximum size of 5MB
4. JWT tokens expire after 24 hours
5. Rate limiting may be applied to certain endpoints 