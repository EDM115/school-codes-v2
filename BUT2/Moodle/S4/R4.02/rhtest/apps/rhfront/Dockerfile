# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
ARG GITPOD_WORKSPACE_URL
COPY package*.json ./
RUN npm install
COPY . .
RUN echo VITE_GITPOD_WORKSPACE_URL=$GITPOD_WORKSPACE_URL > .env
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
