package net.craswell.common.persistence;

import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import net.craswell.common.models.Model;

/**
 * The base abstract persistence store.
 * @author 00005309
 *
 * @param <T>
 */
public abstract class PersistenceStoreAbstract<T extends Model>
  implements PersistenceStore<T> {
  /**
   * The generic class.
   */
  private final Class<T> clazz;
  
  /**
   * The session manager is used to manage persistence layer sessions.
   */
  private SessionManager sessionManager;

  /**
   * Initializes a new instance of the PersistenceStoreAbstract class.
   * 
   * @param clazz
   * @param sessionManager
   */
  protected PersistenceStoreAbstract(
      Class<T> clazz,
      SessionManager sessionManager) {
    if (clazz == null) {
      throw new IllegalArgumentException("clazz");
    }

    if (sessionManager == null) {
      throw new IllegalArgumentException("sessionManager");
    }

    this.clazz = clazz;
    this.sessionManager = sessionManager;
  }

  /* (non-Javadoc)
   * @see net.craswell.common.persistence.PersistenceStore#Create(T)
   */
  @Override
  public T Create(T model) throws Exception {
    try (Session session = this.sessionManager.openSession()) {
      Transaction tx = session.beginTransaction();

      session.save(model);

      tx.commit();
    }

    return model;
  }

  /**
   * Reads a model from the store.
   * 
   * @param session The session.
   * @param uuid The model identifier.
   * @return The model as read from the store.
   * @throws Exception 
   */
  protected T Read(
      Session session,
      UUID uuid) throws Exception {

    CriteriaBuilder builder = session.getCriteriaBuilder();

    CriteriaQuery<T> criteria = builder
        .createQuery(this.clazz);

    Root<T> root = criteria
        .from(this.clazz);

    Path<Object> objectPath = root.get("id");

    Predicate isEntity = builder.equal(
        objectPath,
        uuid);

    criteria
      .where(isEntity);

    return session
        .createQuery(criteria)
        .uniqueResult();
  }

  /**
   * Lists models from the store.
   * 
   * @param session The session.
   * @return A list of persisted models.
   * @throws Exception 
   */
  protected List<T> List(Session session) throws Exception {

    CriteriaBuilder builder = session.getCriteriaBuilder();

    CriteriaQuery<T> criteria = builder
        .createQuery(this.clazz);

    Root<T> variableRoot = criteria
        .from(this.clazz);

    criteria.select(variableRoot);

    return (List<T>) session
        .createQuery(criteria)
        .list();
  }

  /* (non-Javadoc)
   * @see net.craswell.common.persistence.PersistenceStore#Update(T)
   */
  @Override
  public void Update(T model) {
    try (Session session = this.sessionManager.openSession()) {
      Transaction tx = session.beginTransaction();

      session.update(model);

      tx.commit();
    }
  }

  /* (non-Javadoc)
   * @see net.craswell.common.persistence.PersistenceStore#Delete(T)
   */
  @Override
  public void Delete(T model) {
    try (Session session = this.sessionManager.openSession()) {
      Transaction tx = session.beginTransaction();

      session.delete(model);

      tx.commit();
    }
  }

  /**
   * Gets the persistence session manager.
   * 
   * @return
   */
  protected SessionManager getSessionManager() {
    return this.sessionManager;
  }

  /* (non-Javadoc)
   * @see java.lang.AutoCloseable#close()
   */

  @Override
  public void close() throws Exception {
    // TODO Auto-generated method stub
    if (this.sessionManager != null) {
      try {
        this.sessionManager.close();
      } finally {
        this.sessionManager = null;
      }
    }
  }
}
