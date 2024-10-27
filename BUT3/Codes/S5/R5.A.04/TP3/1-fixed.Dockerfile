FROM debian:stable-slim

RUN apt-get update && \
    apt-get install -y openssl python3-pip

RUN pip3 install flask requests --break-system-packages

RUN useradd -m appuser

ENV SECRET_KEY=$SECRET_KEY

EXPOSE 8080

USER appuser

CMD ["bash"]
