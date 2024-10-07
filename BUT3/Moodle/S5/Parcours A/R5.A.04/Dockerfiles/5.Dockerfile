# Stage 1: Build stage with Node.js
FROM node:12.0.0 as builder

WORKDIR /app
COPY package*.json ./
RUN npm install --unsafe-perm

COPY . .
RUN npm run build

# Stage 2: Production stage with Debian as the base
FROM debian:10

RUN apt-get update && \
    apt-get install -y wget

# Copy files from build stage (including sensitive files)
COPY --from=builder /app /app

COPY config/.env /root/.env
COPY secrets/api_key /root/.ssh/api_key

RUN chmod 777 /root/.env /root/.ssh/api_key

USER root
CMD ["node", "/app/dist/index.js"]