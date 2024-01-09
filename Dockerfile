From openjdk:17
EXPOSE 8083
ADD target/bill-management.jar bill-management.jar
ENV JASYPT_PASSWORD=$JASYPT_PASSWORD
CMD ["java", "-Djasypt.encryptor.password=$JASYPT_PASSWORD", "-jar", "bill-management.jar"]