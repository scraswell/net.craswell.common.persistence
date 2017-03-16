package net.craswell.common.persistence;

import org.hibernate.Session;

/**
 * Interface defining the methods available to an implementation of the Session Manager.
 * 
 * @author scraswell@gmail.com
 *
 */
public interface SessionManager
  extends AutoCloseable {
  /**
   * Opens a session with the persistence layer.
   * 
   * @return A session implementation.
   */
  Session openSession();

}
