from fastapi import APIRouter, HTTPException
from app.database import cursor, conn
from app.helpers import client_helper
from app.models import Client


router = APIRouter()


@router.post("/clients/")
def add_client(client: Client):
  query = "INSERT INTO clients (first_name, last_name, email, orders_count) VALUES (%s, %s, %s, %s)"
  values = (client.first_name, client.last_name, client.email, client.orders_count)
  cursor.execute(query, values)
  conn.commit()
  return {"message": "Client added successfully"}


@router.put("/clients/{client_id}")
def update_client(client_id: int, client: Client):
  query = "UPDATE clients SET first_name = %s, last_name = %s, email = %s, orders_count = %s WHERE id = %s"
  values = (client.first_name, client.last_name, client.email, client.orders_count, client_id)
  cursor.execute(query, values)
  if cursor.rowcount == 0:
    raise HTTPException(status_code=404, detail="Client not found")
  conn.commit()
  return {"message": "Client updated successfully"}


@router.delete("/clients/{client_id}")
def delete_client(client_id: int):
  query = "DELETE FROM clients WHERE id = %s"
  cursor.execute(query, (client_id,))
  if cursor.rowcount == 0:
    raise HTTPException(status_code=404, detail="Client not found")
  conn.commit()
  return {"message": "Client deleted successfully"}


@router.get("/clients/")
def get_clients():
  cursor.execute("SELECT * FROM clients")
  clients = cursor.fetchall()
  return [client_helper(client) for client in clients]


@router.get("/clients/{client_id}")
def get_client(client_id: int):
  cursor.execute("SELECT * FROM clients WHERE id = %s", (client_id,))
  client = cursor.fetchone()
  if not client:
    raise HTTPException(status_code=404, detail="Client not found")
  return client_helper(client)
