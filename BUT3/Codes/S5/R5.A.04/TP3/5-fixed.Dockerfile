# Stage 1: Build stage with Node.js
FROM node:latest AS builder

WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# Stage 2: Production stage with Debian as the base
FROM debian:latest

RUN apt-get update && \
    apt-get install -y wget

# Copy files from build stage (including sensitive files)
COPY --from=builder /app /app

RUN useradd -m -d /home/appuser -s /bin/bash appuser

USER appuser

CMD ["node", "/app/dist/index.js"]
