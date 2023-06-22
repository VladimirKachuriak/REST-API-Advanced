package com.epam.esm.gift.model.repo;

import com.epam.esm.gift.model.Purchase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PurchaseDaoImpl implements PurchaseDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int save(Purchase purchase) {
        entityManager.persist(purchase);
        entityManager.flush();
        return purchase.getId();
    }

    @Override
    public Optional<Purchase> findById(int id) {
        return Optional.ofNullable(entityManager.find(Purchase.class, id));
    }

    @Override
    public List<Purchase> findAll() {
        String jpql = "SELECT p FROM Purchase p";
        return entityManager.createQuery(jpql, Purchase.class).getResultList();
    }

    @Override
    public List<Purchase> findAll(Pageable pageable) {
        String jpql = "SELECT p FROM Purchase p";
        TypedQuery<Purchase> query = entityManager.createQuery(jpql, Purchase.class);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    @Override
    public void update(Purchase purchase) {
        entityManager.merge(purchase);
    }

    @Override
    public void delete(int id) {
        Purchase purchase = entityManager.find(Purchase.class, id);
        if (purchase != null) {
            entityManager.remove(purchase);
        }
    }
}
