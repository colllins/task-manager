package com.collins.taskmanager.repository;

import java.util.List;

/**
 * Generic contract for basic create, read and delete operations
 * on entities identified by some ID type.
 *
 * @param <ID> type used as the unique identifier (e.g. Integer, Long)
 * @param <T>  entity type being stored (e.g. Task)
 */
public interface Repository<ID, T> {

    /**
     * Saves the given entity. If it is new, an ID should be assigned.
     *
     * @param entity entity to save
     * @return the saved entity (potentially with an updated ID)
     */
    T save(T entity);

    /**
     * Returns the entity with the given id, or null / throws later
     * depending on how callers choose to handle missing values.
     *
     * @param id identifier of the entity
     * @return the found entity, or null if not found
     */
    T findById(ID id);

    /**
     * Returns all stored entities.
     *
     * @return list containing all entities
     */
    List<T> findAll();

    /**
     * Deletes the entity with the given id if it exists.
     *
     * @param id identifier of the entity to remove
     */
    void deleteById(ID id);

    /**
     * Removes all entities from the repository.
     */
    void deleteAll();
}
