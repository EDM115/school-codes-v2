FROM debian:11-slim

RUN apt-get update && \
    apt-get install -y \
    curl \
    wget \
    vim \
    openssl \
    python3-pip

RUN pip3 install flask requests

CMD ["bash"]

ENV SECRET_KEY="hardcoded_secret"

EXPOSE 8080
