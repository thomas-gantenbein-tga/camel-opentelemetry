# Steps to reproduce problem with traceId
* Start containers with Jaeger, Opentelemetry collector and Prometheus: docker compose -f docker-compose-jaeger.yml up -d
* Run Camel: mvn spring-boot:run
* Watch traceIds from timer route: they will always be the same
* Invoke netty route: `curl localhost:12345`
  * Watch traceIds from netty route: it changes at each request 


# Run this with Jaeger
* docker compose -f docker-compose-jaeger.yml up -d

# Run this with zipkin
* docker run -d --name=zipkin -p 9411:9411 openzipkin/zipkin
* docker run -d --name=grafana -p 3000:3000 grafana/grafana
* docker run --name prometheus -d -p 127.0.0.1:9090:9090 prom/prometheus
* mvn spring-boot:run
* Prometheus scrape endpoint: http://localhost:8080/actuator/prometheus

