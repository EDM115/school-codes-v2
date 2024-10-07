FROM nginx:1.14.0

COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

RUN apt-get update && \
    apt-get install -y php5.6 php5.6-fpm php5.6-mysql

ENV MYSQL_ROOT_PASSWORD="rootpassword"
ENV MYSQL_USER="admin"
ENV MYSQL_PASSWORD="adminpassword"

EXPOSE 80 3306

CMD ["nginx", "-g", "daemon off;"]
