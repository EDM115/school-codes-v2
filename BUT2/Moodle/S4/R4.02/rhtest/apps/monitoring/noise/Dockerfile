FROM alpine:3.19.1
RUN apk update && apk add --no-cache bash curl
ADD ./noise.sh .

CMD [ "bash", "./noise.sh" ]