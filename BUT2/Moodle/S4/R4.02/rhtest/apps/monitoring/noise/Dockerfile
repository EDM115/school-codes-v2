FROM alpine:3.17.1
RUN apk update && apk add --no-cache bash curl
ADD ./noise.sh .

CMD [ "bash", "./noise.sh" ]