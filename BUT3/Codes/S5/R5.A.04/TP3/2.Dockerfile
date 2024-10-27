FROM python:3.6.0

RUN curl https://bootstrap.pypa.io/pip/3.6/get-pip.py | python && \
    pip install Flask==0.12.2 Django==1.11.0 requests==2.18.4

RUN sed -i 's/deb.debian.org/archive.debian.org/g' /etc/apt/sources.list && \
    sed -i '/jessie-updates/d' /etc/apt/sources.list && \
    sed -i '/security.debian.org/s/^/#/' /etc/apt/sources.list && \
    echo 'Acquire::Check-Valid-Until "false";' > /etc/apt/apt.conf.d/99ignore-valid-until && \
    apt-get update && \
    apt-get install -y --allow-unauthenticated --force-yes \
    net-tools \
    iputils-ping \
    openssh-client \
    sudo

ENV USERNAME="admin" \
    PASSWORD="password123"

COPY app.py /app/app.py
WORKDIR /app

CMD ["flask", "run", "--host=0.0.0.0", "--port=5000", "--debug"]
