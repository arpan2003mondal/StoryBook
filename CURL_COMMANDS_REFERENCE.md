# StoryBook API - Quick Reference (cURL Commands)

## Base Configuration
```bash
BASE_URL="http://localhost:1234"
```

## Authentication

### Register New User
```bash
curl -X POST "$BASE_URL/users/register" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Cooper",
    "email": "alice@example.com",
    "password": "Password@123"
  }'
```

### User Login
```bash
USER_TOKEN=$(curl -s -X POST "$BASE_URL/users/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@storybook.com",
    "password": "User@123"
  }')

echo "User Token: $USER_TOKEN"
```

### Admin Login
```bash
ADMIN_TOKEN=$(curl -s -X POST "$BASE_URL/admin/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@storybook.com",
    "password": "Admin@123"
  }')

echo "Admin Token: $ADMIN_TOKEN"
```

### User Logout
```bash
curl -X POST "$BASE_URL/users/logout" \
  -H "Authorization: Bearer $USER_TOKEN"
```

---

## Storybooks

### Get All Storybooks
```bash
curl -X GET "$BASE_URL/api/storybooks" \
  -H "Authorization: Bearer $USER_TOKEN"
```

### Search Storybooks
```bash
# Search by keyword
curl -X GET "$BASE_URL/api/storybooks/search?keyword=Harry" \
  -H "Authorization: Bearer $USER_TOKEN"

# Other search examples
curl -X GET "$BASE_URL/api/storybooks/search?keyword=Game" \
  -H "Authorization: Bearer $USER_TOKEN"

curl -X GET "$BASE_URL/api/storybooks/search?keyword=Fantasy" \
  -H "Authorization: Bearer $USER_TOKEN"
```

### Get Storybook by ID
```bash
curl -X GET "$BASE_URL/api/storybooks/1" \
  -H "Authorization: Bearer $USER_TOKEN"
```

### Add Storybook (Admin Only)
```bash
curl -X POST "$BASE_URL/admin/storybooks" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Hobbit",
    "description": "A fantasy adventure about a small hobbit'\''s unexpected journey",
    "authorId": 4,
    "categoryId": 1,
    "price": 8.99,
    "audioUrl": "https://archive.org/download/hobbit_librivox/hobbit_64kb.m4b",
    "coverImageUrl": "https://images.unsplash.com/photo-1507842217343-583f20270319?w=400&h=600&fit=crop"
  }'
```

---

## Shopping Cart

### Add Item to Cart
```bash
curl -X POST "$BASE_URL/api/storybooks/cart/add" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "storybookId": 1
  }'

# Add multiple items
curl -X POST "$BASE_URL/api/storybooks/cart/add" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"storybookId": 2}'

curl -X POST "$BASE_URL/api/storybooks/cart/add" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"storybookId": 3}'
```

### View Cart
```bash
curl -X GET "$BASE_URL/api/storybooks/cart" \
  -H "Authorization: Bearer $USER_TOKEN"
```

### Remove Item from Cart
```bash
curl -X DELETE "$BASE_URL/api/storybooks/cart/items/1" \
  -H "Authorization: Bearer $USER_TOKEN"
```

---

## Wallet & Orders

### Get Wallet Balance
```bash
curl -X GET "$BASE_URL/api/wallet/balance" \
  -H "Authorization: Bearer $USER_TOKEN"
```

### Checkout
```bash
curl -X POST "$BASE_URL/api/wallet/checkout" \
  -H "Authorization: Bearer $USER_TOKEN"
```

### Get User Library
```bash
curl -X GET "$BASE_URL/api/wallet/library" \
  -H "Authorization: Bearer $USER_TOKEN"
```

---

## Complete Test Scenario (Bash Script)

Save this as `test_api.sh` and run with `bash test_api.sh`:

```bash
#!/bin/bash

BASE_URL="http://localhost:1234"

echo "=== Starting StoryBook API Test ==="

# 1. Admin Login
echo -e "\n[1] Admin Login..."
ADMIN_RESPONSE=$(curl -s -X POST "$BASE_URL/admin/login" \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@storybook.com", "password": "Admin@123"}')
ADMIN_TOKEN=$(echo $ADMIN_RESPONSE)
echo "Admin Token: $ADMIN_TOKEN"

# 2. User Login
echo -e "\n[2] User Login..."
USER_RESPONSE=$(curl -s -X POST "$BASE_URL/users/login" \
  -H "Content-Type: application/json" \
  -d '{"email": "john@storybook.com", "password": "User@123"}')
USER_TOKEN=$(echo $USER_RESPONSE)
echo "User Token: $USER_TOKEN"

# 3. Get All Storybooks
echo -e "\n[3] Get All Storybooks..."
curl -s -X GET "$BASE_URL/api/storybooks" \
  -H "Authorization: Bearer $USER_TOKEN" | python -m json.tool

# 4. Search Storybooks
echo -e "\n[4] Search for 'Harry'..."
curl -s -X GET "$BASE_URL/api/storybooks/search?keyword=Harry" \
  -H "Authorization: Bearer $USER_TOKEN" | python -m json.tool

# 5. Get Specific Storybook
echo -e "\n[5] Get Storybook ID 1..."
curl -s -X GET "$BASE_URL/api/storybooks/1" \
  -H "Authorization: Bearer $USER_TOKEN" | python -m json.tool

# 6. Add to Cart
echo -e "\n[6] Adding Storybook 1 to Cart..."
curl -s -X POST "$BASE_URL/api/storybooks/cart/add" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"storybookId": 1}' | python -m json.tool

# 7. Add another item
echo -e "\n[7] Adding Storybook 2 to Cart..."
curl -s -X POST "$BASE_URL/api/storybooks/cart/add" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"storybookId": 2}' | python -m json.tool

# 8. View Cart
echo -e "\n[8] View Cart..."
curl -s -X GET "$BASE_URL/api/storybooks/cart" \
  -H "Authorization: Bearer $USER_TOKEN" | python -m json.tool

# 9. Check Wallet Balance
echo -e "\n[9] Check Wallet Balance..."
curl -s -X GET "$BASE_URL/api/wallet/balance" \
  -H "Authorization: Bearer $USER_TOKEN" | python -m json.tool

# 10. Checkout
echo -e "\n[10] Checkout..."
curl -s -X POST "$BASE_URL/api/wallet/checkout" \
  -H "Authorization: Bearer $USER_TOKEN" | python -m json.tool

# 11. View Library
echo -e "\n[11] View User Library..."
curl -s -X GET "$BASE_URL/api/wallet/library" \
  -H "Authorization: Bearer $USER_TOKEN" | python -m json.tool

echo -e "\n=== Test Complete ==="
```

---

## Test Data Summary

### Existing Users
| Email | Password | Role |
|-------|----------|------|
| admin@storybook.com | Admin@123 | ADMIN |
| john@storybook.com | User@123 | USER |
| jane@storybook.com | User@123 | USER |

### Authors (IDs)
| ID | Name |
|----|------|
| 1 | J.K. Rowling |
| 2 | George R.R. Martin |
| 3 | Agatha Christie |
| 4 | J.R.R. Tolkien |

### Categories (IDs)
| ID | Name |
|----|------|
| 1 | Fantasy |
| 2 | Mystery |
| 3 | Adventure |
| 4 | Thriller |

### Storybooks (IDs)
| ID | Title | Author | Price |
|----|-------|--------|-------|
| 1 | The Sorcerer's Stone | J.K. Rowling | $9.99 |
| 2 | The Chamber of Secrets | J.K. Rowling | $9.99 |
| 3 | The Prisoner of Azkaban | J.K. Rowling | $9.99 |
| 4 | A Game of Thrones | George R.R. Martin | $12.99 |
| 5 | A Clash of Kings | George R.R. Martin | $12.99 |

---

## Tips

- Use `python -m json.tool` to format JSON responses prettily
- Save tokens in variables to reuse them
- Test with different user accounts to verify permissions
- Check error responses to understand validation requirements
- Use real storybook IDs (1-5) for testing

