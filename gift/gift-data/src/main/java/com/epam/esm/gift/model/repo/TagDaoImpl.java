package com.epam.esm.gift.model.repo;

import com.epam.esm.gift.model.Certificate;
import com.epam.esm.gift.model.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TagDaoImpl implements TagDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int save(Tag tag) {
        entityManager.persist(tag);
        entityManager.flush();
        return tag.getId();
    }
    @Override
    public List<Tag> findAll() {
        String jpql = "SELECT t FROM Tag t";
        TypedQuery<Tag> query = entityManager.createQuery(jpql, Tag.class);
        return query.getResultList();
    }

    @Override
    public List<Tag> findAll(Pageable pageable) {
        String hql = "SELECT t FROM Tag t";
        TypedQuery<Tag> query = entityManager.createQuery(hql, Tag.class);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    @Override
    public Optional<Tag> findById(int id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }
    @Override
    public void update(Tag tag) {
        entityManager.merge(tag);
    }
    @Override
    public void delete(int id) {
        Tag tag = entityManager.find(Tag.class, id);
        for (Certificate certificate : tag.getCertificates()) {
            certificate.getTags().remove(tag);
        }
        entityManager.remove(tag);
    }
    @Override
    public Tag findByName(String name) {
        String jpql = "SELECT t FROM Tag t WHERE t.name = :name";
        TypedQuery<Tag> query = entityManager.createQuery(jpql, Tag.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
