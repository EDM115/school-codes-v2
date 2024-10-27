FROM nginx:latest

# Create a non-root user
RUN useradd -m nginx_user

# Update the Nginx configuration to use the non-root user
COPY nginx.conf /etc/nginx/nginx.conf

# Change ownership of the Nginx directories to the non-root user
RUN chown -R nginx_user:nginx_user /var/cache/nginx /var/run /etc/nginx /usr/share/nginx/html

# Switch to the non-root user
USER nginx_user

# Expose the non-privileged port
EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]
