from pydantic import BaseModel, EmailStr


class Client(BaseModel):
  first_name: str
  last_name: str
  email: EmailStr
  orders_count: int
