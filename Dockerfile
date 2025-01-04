# OpenJDK 11 Alpine-based image
FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

# Vaqt mintaqasini o'rnatish
ENV TZ=Asia/Tashkent


# Kerakli paketlarni o'rnatish va sertifikatlarni yangilash
RUN apk add --no-cache ca-certificates tzdata \
    && update-ca-certificates

# Tzdata bilan vaqt mintaqasini sozlash
RUN cp /usr/share/zoneinfo/${TZ} /etc/localtime \
    && echo "${TZ}" > /etc/timezone \


# Keraksiz kechalarni tozalash
RUN rm -rf /var/cache/apk/*

# JAR faylni konteynerga ko'chirish
COPY ruya-0.0.1.jar app.jar

# Spring Boot backend ishga tushirish
ENTRYPOINT ["java", "-jar", "app.jar"]

# Portni ochish (Spring Boot odatda 8080 portida ishlaydi)
EXPOSE 8080
