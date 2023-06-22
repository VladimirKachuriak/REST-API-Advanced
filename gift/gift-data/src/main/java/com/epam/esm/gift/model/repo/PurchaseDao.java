package com.epam.esm.gift.model.repo;

import com.epam.esm.gift.model.Purchase;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PurchaseDao extends EntityRepo<Purchase>{
    List<Purchase> findAll(Pageable pageable);
}
