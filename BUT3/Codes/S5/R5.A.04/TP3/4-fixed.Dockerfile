FROM php:8.2-fpm-bullseye

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    nginx \
    default-mysql-client \
    wget \
    curl

COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

ENV MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-"rootpassword"}
ENV MYSQL_USER=${MYSQL_USER:-"admin"}
ENV MYSQL_PASSWORD=${MYSQL_PASSWORD:-"adminpassword"}

RUN useradd -m -d /home/appuser -s /bin/bash appuser

USER appuser

EXPOSE 80 3306

CMD ["nginx", "-g", "daemon off;"]
