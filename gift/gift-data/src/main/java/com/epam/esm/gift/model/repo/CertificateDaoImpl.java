package com.epam.esm.gift.model.repo;


import com.epam.esm.gift.model.Certificate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CertificateDaoImpl implements CertificateDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int save(Certificate certificate){
        entityManager.persist(certificate);
        entityManager.flush();
        return certificate.getId();
    }
    @Override
    public Optional<Certificate> findById(int id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }
    @Override
    public List<Certificate> findAll() {
        String jpql = "SELECT c FROM Certificate c";
        return entityManager.createQuery(jpql, Certificate.class).getResultList();
    }
    @Override
    public void update(Certificate certificate) {
        entityManager.merge(certificate);
    }
    @Override
    public void delete(int id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        /*for (Tag tag : certificate.getTags()) {
            tag.getCertificates().remove(certificate);
        }*/
        /*for (Purchase purchase : certificate.getPurchases()) {
            purchase.setCertificate(null);
            certificate.getPurchases().remove(purchase);
        }
        certificate.getPurchases().clear();*/
        entityManager.remove(certificate);
    }
    @Override
    public List<Certificate> findByTags(List<String> tags, int count, PageRequest pageRequest) {
        String hql = "SELECT c FROM Certificate c JOIN c.tags t WHERE t.name IN (:tags) GROUP BY c HAVING COUNT(DISTINCT t) = :count";
        TypedQuery<Certificate> query = entityManager.createQuery(hql, Certificate.class);
        query.setParameter("tags", tags);
        query.setParameter("count", count);
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        return query.getResultList();
    }
}
