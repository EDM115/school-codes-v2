FROM sonarsource/sonar-scanner-cli
USER root
RUN chown -R 1001:1001 /opt/sonar-scanner
USER 1001
