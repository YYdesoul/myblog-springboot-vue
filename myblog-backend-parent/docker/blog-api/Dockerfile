FROM openjdk:11
ADD blog-api.jar blog-api.jar
ENTRYPOINT ["java","-jar","/blog-api.jar", "--spring.profiles.active=prod"]