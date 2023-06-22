package com.epam.esm.gift.model.repo;

import com.epam.esm.gift.model.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagDao extends EntityRepo<Tag> {
    Tag findByName(String name);
    List<Tag> findAll(Pageable pageable);
}
