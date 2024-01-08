From openjdk:8
EXPOSE 8083
ADD target/bill-management.jar bill-management.jar
CMD ["java", "-Djasypt.encryptor.password=${JASYPT_SECERT_KEY}", "-jar", "bill-management.jar"]