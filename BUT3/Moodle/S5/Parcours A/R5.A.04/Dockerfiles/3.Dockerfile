FROM ubuntu:16.04

RUN apt-get update && apt-get install -y \
    nodejs \
    npm \
    netcat \
    openssh-server

RUN npm install express@4.15.0 && \
    npm install lodash@4.17.4

RUN useradd -m -d /home/superuser -s /bin/bash superuser && \
    echo 'superuser ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers

RUN mkdir /var/run/sshd && \
    echo 'root:rootpassword' | chpasswd

COPY insecure_rsa_key /root/.ssh/id_rsa

RUN chmod 600 /root/.ssh/id_rsa

EXPOSE 22 8080

CMD ["/usr/sbin/sshd", "-D"]
