FROM php:5.6-fpm

ENV DEBIAN_FRONTEND=noninteractive

RUN sed -i 's/deb.debian.org/archive.debian.org/g' /etc/apt/sources.list && \
    sed -i '/stretch-updates/d' /etc/apt/sources.list && \
    sed -i '/security.debian.org/s/^/#/' /etc/apt/sources.list && \
    echo 'Acquire::Check-Valid-Until "false";' > /etc/apt/apt.conf.d/99ignore-valid-until && \
    apt-get update && apt-get install -y \
    nginx \
    mysql-client \
    wget \
    curl

COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

ENV MYSQL_ROOT_PASSWORD="rootpassword"
ENV MYSQL_USER="admin"
ENV MYSQL_PASSWORD="adminpassword"

EXPOSE 80 3306

CMD ["nginx", "-g", "daemon off;"]
