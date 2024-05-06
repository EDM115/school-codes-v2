FROM node:18 as dependencies
WORKDIR /build
ADD package.json package-lock.json /build/
RUN npm ci

FROM node:18 as builder
WORKDIR /build
ADD . .
RUN npm ci
RUN npm run build

FROM node:18
COPY --from=dependencies /build /app/
COPY --from=builder /build/dist /app/
COPY ./src/public /app/public

USER node
EXPOSE 8080

ENV NODE_ENV=production

CMD [ "node", "/app/index.js" ]

