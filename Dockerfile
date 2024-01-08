From openjdk:17
EXPOSE 8083
ADD target/bill-management.jar bill-management.jar
CMD ["java", "-Dserver.port=8083", "-Djasypt.encryptor.password=billmng", "-jar", "bill-management.jar"]