# New User Stories Implementation

This document outlines all the new features added to the StoryBook Backend API based on user stories.

## Overview

### Admin User Stories
- ✅ Manage Categories (Create, Read, Update, Delete)
- ✅ Manage Authors (Create, Read, Update, Delete)
- ✅ Update Storybook Prices

### Regular User Stories
- ✅ Change Password (with old and new password validation)

---

## Admin Features

### 1. Category Management

#### 1.1 Add New Category (CREATE)
**Endpoint:** `POST /admin/categories`

**Request:**
```json
{
  "name": "Science Fiction",
  "description": "Futuristic and space-themed stories"
}
```

**Response:** 201 Created
```json
{
  "name": "Science Fiction",
  "description": "Futuristic and space-themed stories"
}
```

**Validation:**
- Category name is required
- Admin role required

---

#### 1.2 Update Category (UPDATE)
**Endpoint:** `PUT /admin/categories/{categoryId}`

**Request:**
```json
{
  "name": "Science Fiction & Fantasy",
  "description": "Updated description with both genres"
}
```

**Response:** 200 OK
```json
{
  "name": "Science Fiction & Fantasy",
  "description": "Updated description with both genres"
}
```

**Validation:**
- Category must exist
- Admin role required

---

#### 1.3 Delete Category (DELETE)
**Endpoint:** `DELETE /admin/categories/{categoryId}`

**Response:** 200 OK
```
"Category deleted successfully."
```

**Validation:**
- Category must exist
- Admin role required

---

### 2. Author Management

#### 2.1 Add New Author (CREATE)
**Endpoint:** `POST /admin/authors`

**Request:**
```json
{
  "name": "Isaac Asimov",
  "bio": "American writer and biochemist, known for science fiction"
}
```

**Response:** 201 Created
```json
{
  "name": "Isaac Asimov",
  "bio": "American writer and biochemist, known for science fiction"
}
```

**Validation:**
- Author name is required
- Admin role required

---

#### 2.2 Update Author (UPDATE)
**Endpoint:** `PUT /admin/authors/{authorId}`

**Request:**
```json
{
  "name": "Isaac Asimov",
  "bio": "Updated bio with more information"
}
```

**Response:** 200 OK
```json
{
  "name": "Isaac Asimov",
  "bio": "Updated bio with more information"
}
```

**Validation:**
- Author must exist
- Admin role required

---

#### 2.3 Delete Author (DELETE)
**Endpoint:** `DELETE /admin/authors/{authorId}`

**Response:** 200 OK
```
"Author deleted successfully."
```

**Validation:**
- Author must exist
- Admin role required

---

### 3. Storybook Price Management

#### 3.1 Update Storybook Price
**Endpoint:** `PUT /admin/storybooks/{storybookId}/price`

**Request:**
```json
{
  "price": 12.99
}
```

**Response:** 200 OK
```json
{
  "id": 1,
  "title": "The Sorcerer's Stone",
  "description": "A young boy discovers he is a wizard...",
  "authorName": "J.K. Rowling",
  "categoryName": "Fantasy",
  "price": 12.99,
  "audioUrl": "https://...",
  "coverImageUrl": "https://...",
  "createdAt": "2026-03-14T10:30:00"
}
```

**Validation:**
- Storybook must exist
- Price must be greater than 0
- Admin role required

---

## User Features

### 1. Change Password

#### 1.1 Change Password (UPDATE)
**Endpoint:** `POST /users/change-password`

**Headers:**
```
Authorization: Bearer <userToken>
Content-Type: application/json
```

**Request:**
```json
{
  "oldPassword": "CurrentPassword@123",
  "newPassword": "NewPassword@456"
}
```

**Response:** 200 OK
```
"Password changed successfully."
```

**Validation:**
- User must be authenticated
- Old password must match current password
- New password must be at least 6 characters
- Both passwords are required

**Error Cases:**
- `401 Unauthorized` - User not authenticated
- `400 Bad Request` - Invalid old password
- `400 Bad Request` - New password too short
- `404 Not Found` - User not found

---

## Business Logic

### Admin Category Management
1. Admin logs in and receives admin token
2. Admin can create new categories to organize storybooks
3. Admin can update category names and descriptions
4. Admin can delete categories (assuming no books are linked or cascading delete)

### Admin Author Management
1. Admin logs in and receives admin token
2. Admin can register new authors in the system
3. Admin can update author information (name, bio)
4. Admin can remove authors from the system

### Admin Price Updates
1. Admin logs in and receives admin token
2. Admin can adjust storybook prices for sales or corrections
3. Price change is reflected immediately in the system
4. Price history is not tracked (current implementation)

### User Password Change
1. User logs in and receives user token
2. User navigates to change password page/endpoint
3. User enters old password for verification
4. User enters new password (must be 6+ characters)
5. System validates old password matches current password
6. New password is hashed and saved
7. User receives confirmation message

---

## Security Considerations

### Admin Features
- All admin endpoints require JWT token with ADMIN role
- Request validation on all inputs
- Transactional operations for data consistency
- Error messages don't leak sensitive information

### User Password Change
- Old password must match exactly (BCrypt verification)
- New password must meet minimum length requirement
- Password is immediately hashed with BCrypt
- Current session token remains valid
- No logout required

---

## Messages (Internationalization)

All success and error messages are stored in `messages.properties`:

```properties
# Author messages
author.name.required=Author name is required.
author.created.success=Author created successfully.
author.updated.success=Author updated successfully.
author.deleted.success=Author deleted successfully.

# Category messages
category.name.required=Category name is required.
category.created.success=Category created successfully.
category.updated.success=Category updated successfully.
category.deleted.success=Category deleted successfully.

# User password change messages
user.password.change.success=Password changed successfully.
user.password.invalid=Invalid old password. Please try again.

# Storybook messages
storybook.price.updated=Storybook price updated successfully.
```

---

## API Request/Response Examples

### Complete Workflow: Admin Operations

#### 1. Admin Login
```bash
curl -X POST "http://localhost:1234/admin/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@storybook.com",
    "password": "Admin@123"
  }'
```

#### 2. Add Author
```bash
curl -X POST "http://localhost:1234/admin/authors" \
  -H "Authorization: Bearer <adminToken>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Stephen King",
    "bio": "American author of suspense and horror novels"
  }'
```

#### 3. Add Category
```bash
curl -X POST "http://localhost:1234/admin/categories" \
  -H "Authorization: Bearer <adminToken>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Horror",
    "description": "Scary and suspenseful stories"
  }'
```

#### 4. Add Storybook with New Author & Category
```bash
curl -X POST "http://localhost:1234/admin/storybooks" \
  -H "Authorization: Bearer <adminToken>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Shining",
    "description": "A psychological horror novel",
    "authorId": 5,
    "categoryId": 5,
    "price": 11.99,
    "audioUrl": "https://example.com/shining",
    "coverImageUrl": "https://example.com/shining-cover"
  }'
```

#### 5. Update Storybook Price
```bash
curl -X PUT "http://localhost:1234/admin/storybooks/6/price" \
  -H "Authorization: Bearer <adminToken>" \
  -H "Content-Type: application/json" \
  -d '{
    "price": 9.99
  }'
```

#### 6. Admin Logout
```bash
curl -X POST "http://localhost:1234/admin/logout" \
  -H "Authorization: Bearer <adminToken>"
```

---

### Complete Workflow: User Password Change

#### 1. User Login
```bash
curl -X POST "http://localhost:1234/users/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@storybook.com",
    "password": "User@123"
  }'
```

#### 2. Change Password
```bash
curl -X POST "http://localhost:1234/users/change-password" \
  -H "Authorization: Bearer <userToken>" \
  -H "Content-Type: application/json" \
  -d '{
    "oldPassword": "User@123",
    "newPassword": "NewSecurePassword@456"
  }'
```

#### 3. Login with New Password (Verify Change)
```bash
curl -X POST "http://localhost:1234/users/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@storybook.com",
    "password": "NewSecurePassword@456"
  }'
```

---

## Testing Checklist

### Admin Features Testing
- [ ] Add category successfully
- [ ] Update category successfully
- [ ] Delete category successfully
- [ ] Add author successfully
- [ ] Update author successfully
- [ ] Delete author successfully
- [ ] Update storybook price successfully
- [ ] Verify admin-only access (reject non-admin)
- [ ] Validate required fields
- [ ] Validate price > 0

### User Password Change Testing
- [ ] Change password successfully
- [ ] Reject invalid old password
- [ ] Reject short new password (< 6 chars)
- [ ] Verify new password works on login
- [ ] Reject unauthenticated requests
- [ ] Verify both password fields required

---

## Files Modified/Created

### New DTOs
- `CategoryRequest.java`
- `AuthorRequest.java`
- `ChangePasswordRequest.java`
- `UpdatePriceRequest.java`

### Updated Services
- `AdminService.java` (interface)
- `AdminServiceImpl.java` (implementation)
- `UserAuthService.java` (interface)
- `UserAuthServiceImpl.java` (implementation)

### Updated Controllers
- `AdminController.java` (7 new endpoints)
- `UserAuthController.java` (1 new endpoint)

### Updated Configuration
- `messages.properties` (new messages)

---

## Future Enhancements

- [ ] Password change history tracking
- [ ] Author deletion cascade handling
- [ ] Category deletion cascade handling
- [ ] Email notification on password change
- [ ] Password strength requirements enhancement
- [ ] Admin activity logging
- [ ] Bulk author/category import
- [ ] Price change audit trail
- [ ] Schedule-based price updates

