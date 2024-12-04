package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingBean {

  private static final Logger LOG = LoggerFactory.getLogger(LoggingBean.class);
  public void logSomething() {
    LOG.info("log from bean EIP");
  }

}
