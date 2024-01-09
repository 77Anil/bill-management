From openjdk:17
EXPOSE 8083
ADD target/bill-management.jar bill-management.jar
ENTRYPOINT ["java", "-Djasypt.encryptor.password=${JASYPT_SECERT_KEY}", "-jar", "bill-management.jar"]