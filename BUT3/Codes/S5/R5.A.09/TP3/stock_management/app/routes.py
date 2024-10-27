from fastapi import APIRouter, HTTPException
from app.database import product_collection
from app.helpers import product_helper
from app.models import Product
from bson import ObjectId


router = APIRouter()


@router.post("/products/")
def add_product(product: Product):
  product_dict = product.model_dump()
  product = product_collection.insert_one(product_dict)

  return {"message": "Produit ajouté avec succès !", "object": product_helper(product)}


@router.put("/products/{product_id}")
def update_product(product_id: str, product: Product):
  try:
    result = product_collection.update_one({"_id": ObjectId(product_id)}, {"$set": product.model_dump()})
  except Exception as e:
    raise HTTPException(status_code=500, detail=f"Produit non trouvé, {str(e)}")

  if result.matched_count == 0:
    raise HTTPException(status_code=404, detail="Produit non trouvé")

  return {"message": "Produit mis à jour avec succès !"}


@router.delete("/products/{product_id}")
def delete_product(product_id: str):
  try:
    result = product_collection.delete_one({"_id": ObjectId(product_id)})
  except Exception as e:
    raise HTTPException(status_code=500, detail=f"Produit non trouvé, {str(e)}")

  if result.deleted_count == 0:
    raise HTTPException(status_code=404, detail="Produit non trouvé")

  return {"message": "Produit supprimé avec succès !"}


@router.get("/products/")
def get_products():
  products = []

  for product in product_collection.find():
    products.append(product_helper(product))

  return products


@router.get("/products/{product_id}")
def get_product(product_id: str):
  try:
    product = product_collection.find_one({"_id": ObjectId(product_id)})
  except Exception as e:
    raise HTTPException(status_code=500, detail=f"Produit non trouvé, {str(e)}")

  if not product:
    raise HTTPException(status_code=404, detail="Produit non trouvé")

  return product_helper(product)
