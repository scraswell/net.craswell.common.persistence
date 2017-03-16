package net.craswell.common.persistence;

import net.craswell.common.models.Model;

public interface PersistenceStore<T extends Model>
    extends AutoCloseable {

  /**
   * Persists a model in the store.
   * 
   * @param model
   * @return The persisted model.
   * @throws Exception 
   */
  T Create(T model) throws Exception;

  /**
   * Updates a model in the store.
   * 
   * @param model The model to be updated.
   */
  void Update(T model);

  /**
   * Deletes a model from the store.
   * 
   * @param model The model to be deleted.
   */
  void Delete(T model);
}
