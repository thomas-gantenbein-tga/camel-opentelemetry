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
        from("netty:tcp://0.0.0.0:12345")
        //from("timer:mytimer?period=10000")
            .routeId("netty")
            .log("in netty route")
            .to("direct:anotherRoute");

        from("timer:mytimer?period=5000&repeatCount=2")
            .routeId("timer")
            .log("in timer route")
            .to("direct:anotherRoute");

        from("direct:anotherRoute")
            .log("in direct route")
            .delay(simple("${random(50, 5000)}"))
            .log("another log in direct")
            .process(e -> {
                e.getIn().setHeader(NettyConstants.NETTY_CLOSE_CHANNEL_WHEN_COMPLETE, true);
            });
    }

}
