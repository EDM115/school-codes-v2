from pymongo import MongoClient


client = MongoClient("mongodb://mongo:27017/")
db = client["stock_management"]
product_collection = db["products"]
