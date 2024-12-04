package org.example;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.netty.NettyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            .to("direct:aSlowerRoute")
            .to("direct:aFasterRoute");

        from("timer:mytimer?period=5000&repeatCount=2&synchronous=true")
            .routeId("timer")
            .log("in timer route")
            .to("direct:aSlowerRoute")
            .to("direct:aFasterRoute");

        from("direct:aSlowerRoute")
            .log("in aSlowerRoute")
            .delay(simple("${random(50, 350)}"))
            .process(e -> {
                Logger log = LoggerFactory.getLogger("logInProcessor");
                log.info("log from process EIP");
            })
            .delay(1)
            .bean(LoggingBean.class, "logSomething")
            .log("still in aSlowerRoute");
        
	from("direct:aFasterRoute")
            .log("in aFasterRoute")
            .delay(simple("${random(10, 70)}"))
            .log("still in aFasterRoute");
    }

}
