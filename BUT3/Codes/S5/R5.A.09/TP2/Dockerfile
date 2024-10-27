FROM python:3.12-slim

WORKDIR /app

RUN pip install --no-cache-dir "fastapi[standard]" mysql-connector-python

COPY ./app /app/app
COPY ./wait-for-it.sh /app/wait-for-it.sh

EXPOSE 8000

CMD ["/bin/bash", "/app/wait-for-it.sh", "db:3306", "--timeout=0", "--strict", "--", "uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
