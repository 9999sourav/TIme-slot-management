FROM openjdk:11
WORKDIR usr/lib
EXPOSE 4400
ADD ./target/slot-management-service-0.0.1-SNAPSHOT.jar /usr/lib/slot-management-service-0.0.1-SNAPSHOT.jar
ADD ./target/classes/template1.html /usr/lib/slot-management-service/src/main/resources/template1.html
ADD ./target/classes/template2.html /usr/lib/slot-management-service/src/main/resources/template2.html
ADD ./target/classes/template3.html /usr/lib/slot-management-service/src/main/resources/template3.html
ADD ./target/classes/template4.html /usr/lib/slot-management-service/src/main/resources/template4.html
ENTRYPOINT ["java","-jar", "slot-management-service-0.0.1-SNAPSHOT.jar"]
