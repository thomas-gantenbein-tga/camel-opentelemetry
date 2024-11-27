package org.example;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.netty.NettyConstants;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    @Override
    public void configure() {
        from("netty-http:http://0.0.0.0:12345")
            .routeId("netty")
            .log("in netty route")
            .to("direct:anotherRoute")
            .to("direct:aFasterRoute");

        from("timer:mytimer?period=5000&repeatCount=2&synchronous=true")
            .routeId("timer")
            .log("in timer route")
            .to("direct:anotherRoute");

        from("direct:anotherRoute")
            .log("in direct route")
            .delay(simple("${random(50, 350)}"))
            .log("another log in direct");
        
	from("direct:aFasterRoute")
            .log("in faster route")
            .delay(simple("${random(10, 70)}"))
            .log("another log in faster route");
    }

}
