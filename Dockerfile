From openjdk:17
EXPOSE 8083
ADD target/bill-management.jar bill-management.jar
CMD ["java", "-Djasypt.encryptor.password=billmng", "-jar", "bill-management.jar"]