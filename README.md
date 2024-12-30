**API Endpoints**
Base URL: http://localhost:8080

**1. Authentication**
   
POST /auth/register

Request Body (JSON):

json

Copy code

{
  "username": "john",
  "password": "pass123",
  "email": "john@example.com"
}

Expected Output (JSON):

json
Copy code

{
  "id": 1,
  "username": "john",
  "email": "john@example.com",
  "roles": ["CUSTOMER"]
}

Registers a new user. Depending on your logic, you can assign roles (e.g., ["ADMIN"], ["CUSTOMER"]) here.

POST /auth/login

Request Body (JSON):

json

Copy code

{
  "username": "john",
  "password": "pass123"
}

Expected Output (JSON):

json
Copy code

{
  "accessToken": "<JWT_TOKEN>",
  "expiresIn": 3600
}

Logs in with valid credentials and returns a JWT token used for subsequent requests.


2. Books

GET /books

Request:

Typically no body.

If secured, provide Authorization: Bearer <token> header.

Expected Output (JSON):

json
Copy code

[
  {
    "id": 1,
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "price": 39.99,
    "stock": 10
  },
  ...
]

Fetches a list of all books. May or may not require authentication, depending on your security config.

GET /books/{id}

Request:

No body.

If secured, provide Authorization: Bearer <token> header.

Expected Output (JSON):

json

Copy code
{
  "id": 1,
  "title": "Effective Java",
  "author": "Joshua Bloch",
  "price": 39.99,
  "stock": 10
}

Retrieves a single book by its ID.

POST /books

Request Body (JSON) (Requires admin or staff privileges):

json
Copy code

{
  "title": "Domain-Driven Design",
  "author": "Eric Evans",
  "price": 49.99,
  "stock": 5
}

Expected Output (JSON):

json
Copy code
{
  "id": 2,
  "title": "Domain-Driven Design",
  "author": "Eric Evans",
  "price": 49.99,
  "stock": 5
}
Creates a new book entry in the system.

PUT /books/{id}
Request Body (JSON) (Requires admin or staff privileges):

json
Copy code
{
  "title": "Domain-Driven Design (2nd Ed.)",
  "price": 59.99,
  "stock": 10
}
Expected Output (JSON):

json
Copy code
{
  "id": 2,
  "title": "Domain-Driven Design (2nd Ed.)",
  "author": "Eric Evans",
  "price": 59.99,
  "stock": 10
}
Updates book details such as title, price, or stock.

DELETE /books/{id}
Request:

No body (Requires admin privileges).
Provide Authorization: Bearer <token> header.
Expected Output (JSON):

json
Copy code
{
  "message": "Book with id=2 deleted successfully."
}
Removes a book from the system.

3. Orders
GET /orders
Request:

No body (Requires admin or staff privileges).
Provide Authorization: Bearer <token> header.
Expected Output (JSON):

json
Copy code
[
  {
    "id": 101,
    "userId": 1,
    "items": [
      { "bookId": 2, "quantity": 1 }
    ],
    "totalPrice": 49.99
  },
  ...
]
Lists all orders in the system.

GET /orders/{id}
Request:

No body.
Provide Authorization: Bearer <token> header (ADMIN, STAFF, or owner).
Expected Output (JSON):

json
Copy code
{
  "id": 101,
  "userId": 1,
  "items": [
    { "bookId": 2, "quantity": 1 }
  ],
  "totalPrice": 49.99
}
Retrieves an order by ID. Typically accessible by the order’s owner or staff/admin.

POST /orders
Request Body (JSON) (Requires authenticated user):

json
Copy code
{
  "items": [
    { "bookId": 2, "quantity": 2 },
    { "bookId": 3, "quantity": 1 }
  ]
}
Expected Output (JSON):

json
Copy code
{
  "id": 102,
  "userId": 1,
  "items": [
    { "bookId": 2, "quantity": 2 },
    { "bookId": 3, "quantity": 1 }
  ],
  "totalPrice": 139.97
}
Creates a new order for the authenticated user.

PUT /orders/{id}
Request Body (JSON) (Requires the same user or admin/staff role):

json
Copy code
{
  "items": [
    { "bookId": 2, "quantity": 1 }
  ]
}
Expected Output (JSON):

json
Copy code
{
  "id": 102,
  "userId": 1,
  "items": [
    { "bookId": 2, "quantity": 1 }
  ],
  "totalPrice": 49.99
}
Updates an existing order’s items (if your business rules allow it).

DELETE /orders/{id}
Request:

No body.
Provide Authorization: Bearer <token> header (ADMIN, STAFF, or owner).
Expected Output (JSON):

json
Copy code
{
  "message": "Order with id=102 cancelled successfully."
}
Cancels or deletes the specified order.

4. Users

GET /users

Request:

No body (Requires admin privileges).

Provide Authorization: Bearer <token> header.

Expected Output (JSON):

json

Copy code
[
  {
    "id": 1,
    "username": "admin",
    "roles": ["ADMIN"]
  },
  {
    "id": 2,
    "username": "john",
    "roles": ["CUSTOMER"]
  }
]

Retrieves a list of all users. Only admins can see every user.

GET /users/{id}

Request:

No body.

Provide Authorization: Bearer <token> header (ADMIN or same user).

Expected Output (JSON):

json

Copy code
{
  "id": 2,
  "username": "john",
  "roles": ["CUSTOMER"],
  "email": "john@example.com"
}

Fetches user details. Regular users can only view their own info; admins can view any user.


PUT /users/{id}

Request Body (JSON) (Requires admin or same user):

json

Copy code

{
  "username": "john_updated",
  "email": "john.updated@example.com"

}

Expected Output (JSON):

json
Copy code

{
  "id": 2,
  "username": "john_updated",
  "email": "john.updated@example.com"
}

Updates user details. Often used by the user to update their own info or by an admin to manage users.

DELETE /users/{id}

Request:

No body (Requires admin privileges).

Provide Authorization: Bearer <token> header.

Expected Output (JSON):

json

Copy code

{
  "message": "User with id=2 deleted successfully."
}

Removes a user account from the system. Typically restricted to admins.
