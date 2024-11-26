# Steps to reproduce problem with traceId
* Start containers with Jaeger, Opentelemetry collector and Prometheus: docker compose -f docker-compose-jaeger.yml up -d
* Watch traceIds from timer and netty route: `mvn spring-boot:run | grep -e "in timer"  -e "in netty"`
* Timer will fire two times and show the same traceId.
* Invoke netty route with `curl localhost:12345`; every call will generate a new traceId -- until you happen to hit
  the same thread again
* JVM has debugging port listening on 5005 for remote debugging

Test whether traceId is populated as it should be when the request comes from a different system that already
has assigned a traceId: `curl -H "traceparent: 00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01" localhost:12345"`


# Run this with Jaeger
* docker compose -f docker-compose-jaeger.yml up -d

# Run this with zipkin
* docker run -d --name=zipkin -p 9411:9411 openzipkin/zipkin
* docker run -d --name=grafana -p 3000:3000 grafana/grafana
* docker run --name prometheus -d -p 127.0.0.1:9090:9090 prom/prometheus
* mvn spring-boot:run
* Prometheus scrape endpoint: http://localhost:8080/actuator/prometheus

