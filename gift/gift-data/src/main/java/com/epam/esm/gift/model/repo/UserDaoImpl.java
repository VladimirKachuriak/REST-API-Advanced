package com.epam.esm.gift.model.repo;


import com.epam.esm.gift.model.Tag;
import com.epam.esm.gift.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int save(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user.getId();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        String jpql = "SELECT u FROM User u";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    @Override
    public Tag findMostUsedTagByUser(int userId) {
        String hql = "SELECT t FROM User u JOIN u.purchases p JOIN p.certificate c JOIN c.tags t " +
                "WHERE u.id = :userId GROUP BY t ORDER BY COUNT(t) DESC, SUM(p.price) DESC";
        TypedQuery<Tag> query = entityManager.createQuery(hql, Tag.class);
        query.setParameter("userId", userId);
        query.setMaxResults(1);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
