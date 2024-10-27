def product_helper(product) -> dict:
  return {
    "id": str(product["_id"]),
    "name": product["name"],
    "description": product["description"],
    "quantity": product["quantity"],
    "price": product["price"]
  }
