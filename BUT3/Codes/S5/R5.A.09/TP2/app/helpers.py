def client_helper(client) -> dict:
  return {
    "id": client["id"],
    "first_name": client["first_name"],
    "last_name": client["last_name"],
    "email": client["email"],
    "orders_count": client["orders_count"]
  }
