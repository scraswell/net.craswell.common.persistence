package net.craswell.common.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * The persistence session manager.
 * 
 * @author scraswell@gmail.com
 *
 */
public abstract class SessionManagerAbstract
	implements AutoCloseable, SessionManager {
	/**
	 * The Hibernate session factory.
	 */
	private static SessionFactory sessionFactory;
	
	/**
	 * The Hibernate configuration.
	 */
	private static Configuration configuration;
	
	/* (non-Javadoc)
   * @see net.craswell.common.persistence.SessionManager#openSession()
   */
	@Override
  public Session openSession() {
		if (sessionFactory == null
				&& configuration == null) {
			
			configuration = this.buildHibernateConfiguration();
			
			sessionFactory = configuration
					.buildSessionFactory();
		}
		
		return sessionFactory.openSession();
	}
	
	/**
	 * Builds the Hibernate configuration.
	 * 
	 * @return The Hibernate configuration.
	 */
	protected abstract Configuration buildHibernateConfiguration();
	
	/* (non-Javadoc)
   * @see net.craswell.common.persistence.SessionManager#close()
   */
	@Override
  public void close() {
		if (sessionFactory != null
				&& sessionFactory.isOpen()) {
			sessionFactory.close();
			sessionFactory = null;
		}
		
		if (configuration != null) {
			configuration = null;
		}
	}
}
