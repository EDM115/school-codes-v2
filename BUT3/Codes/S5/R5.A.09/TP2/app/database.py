import os
import mysql.connector

db_config = {
  "host": os.getenv("MYSQL_HOST"),
  "user": os.getenv("MYSQL_USER"),
  "password": os.getenv("MYSQL_PASSWORD"),
  "database": os.getenv("MYSQL_DATABASE"),
  'port': int(os.getenv("MYSQL_PORT", 3306))
}

conn = mysql.connector.connect(**db_config)
cursor = conn.cursor(dictionary=True)

# Create table if it doesn't exist
cursor.execute("""
CREATE TABLE IF NOT EXISTS clients (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  email VARCHAR(255),
  orders_count INT
)
""")
