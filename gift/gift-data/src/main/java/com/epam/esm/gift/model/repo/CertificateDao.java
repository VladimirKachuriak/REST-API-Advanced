package com.epam.esm.gift.model.repo;

import com.epam.esm.gift.model.Certificate;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CertificateDao extends EntityRepo<Certificate>{
    List<Certificate> findByTags(List<String> tag, int count, PageRequest pageRequest);
}
