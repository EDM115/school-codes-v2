FROM python:3.6.0

RUN pip install \
    Flask==0.12.2 \  # This version has known vulnerabilities like CVE-2018-1000656
    Django==1.11.0 \ # This version has vulnerabilities like CVE-2019-12308
    requests==2.18.4

RUN apt-get update && \
    apt-get install -y \
    net-tools \
    iputils-ping \
    openssh-client \
    sudo

ENV USERNAME="admin" \
    PASSWORD="password123"

COPY app.py /app/app.py
WORKDIR /app

CMD ["flask", "run", "--host=0.0.0.0", "--port=5000", "--debug"]
