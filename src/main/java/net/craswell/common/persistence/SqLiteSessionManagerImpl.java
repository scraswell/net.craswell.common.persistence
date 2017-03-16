package net.craswell.common.persistence;

import org.hibernate.cfg.Configuration;

/**
 * SQLite session manager implementation.
 * 
 * @author scraswell@gmail.com
 *
 */
public class SqLiteSessionManagerImpl
  extends SessionManagerAbstract {

  @Override
  /**
   * Builds the Hibernate configuration.
   * 
   * @return The Hibernate configuration.
   */
  protected Configuration buildHibernateConfiguration() {
    Configuration configuration = null;

    configuration = new Configuration();

    configuration.configure();

    return configuration;
  }
}
