From openjdk:17
EXPOSE 8083
ADD target/bill-management.jar bill-management.jar
CMD ["java", "-Djasypt.encryptor.password=$JASYPT_PASSWORD", "-jar", "bill-management.jar"]