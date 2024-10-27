FROM ubuntu:jammy

RUN apt-get update && apt-get install -y \
    nodejs \
    npm \
    netcat \
    openssh-server

WORKDIR /app

RUN npm install express loadash

RUN useradd -m -d /home/superuser -s /bin/bash superuser

EXPOSE 22 8080

CMD ["/usr/sbin/sshd", "-D"]
