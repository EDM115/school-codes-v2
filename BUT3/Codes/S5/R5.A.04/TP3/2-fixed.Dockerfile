FROM python:latest

RUN curl https://bootstrap.pypa.io/pip/get-pip.py | python && \
    pip3 install Flask Django requests

RUN apt-get update && \
    apt-get install -y \
    net-tools \
    iputils-ping \
    openssh-client \
    sudo

ENV USERNAME=$USERNAME \
    PASSWORD=$PASSWORD

COPY app.py /app/app.py
WORKDIR /app

CMD ["flask", "run", "--host=0.0.0.0", "--port=5000", "--debug"]
