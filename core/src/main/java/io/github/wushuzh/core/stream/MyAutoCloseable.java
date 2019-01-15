package io.github.wushuzh.core.stream;

import java.io.IOException;

/**
 * MyAutoCloseable
 */
public class MyAutoCloseable implements AutoCloseable {

  public void saySomething() throws IOException {
    throw new IOException("Exception from method saySomething");
  }

  @Override
  public void close() throws IOException {
    throw new IOException("Exception from method close");
  }

}
