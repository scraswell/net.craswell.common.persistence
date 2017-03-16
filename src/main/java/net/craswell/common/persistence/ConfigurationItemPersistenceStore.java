package net.craswell.common.persistence;

import java.util.UUID;
import java.util.List;

import org.hibernate.Session;

import net.craswell.common.models.ConfigurationItem;

/**
 * Models the persistence store for configuration items.
 * 
 * @author scraswell@gmail.com
 *
 */
public class ConfigurationItemPersistenceStore
    extends PersistenceStoreAbstract<ConfigurationItem>
    implements PersistenceStore<ConfigurationItem> {

  /**
   * Initializes a new instance of the ConfigurationItemPersistenceStore class.
   * 
   * @param sessionManager A persistence layer session manager implementation.
   */
  public ConfigurationItemPersistenceStore(
      SessionManager sessionManager) {
    super(ConfigurationItem.class, sessionManager);
  }

  /**
   * Initializes a new instance of the ConfigurationItemPersistenceStore class using the SQLite
   * session manager implementation.
   */
  public ConfigurationItemPersistenceStore() {
    this(new SqLiteSessionManagerImpl());
  }

  /**
   * Reads a configuration item from the persistence store.
   * 
   * @param uuid The configuration item UUID.
   * @return The configuration item.
   * 
   * @throws Exception
   */
  public ConfigurationItem Read(UUID uuid)
      throws Exception {
    try (Session session = this.getSessionManager().openSession()) {
      return this.Read(session, uuid);
    }
  }

  /**
   * Lists the configuration items in the persistence store.
   * 
   * @return A list of configuration items.
   * 
   * @throws Exception
   */
  public List<ConfigurationItem> List()
      throws Exception {
    try (Session session = this.getSessionManager().openSession()) {
      return this.List(session);
    }
  }
}
