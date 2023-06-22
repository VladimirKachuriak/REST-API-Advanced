package com.epam.esm.gift.model.repo;

import com.epam.esm.gift.model.Tag;
import com.epam.esm.gift.model.User;

public interface UserDao extends EntityRepo<User> {
    /*@Query("SELECT t FROM User u JOIN u.purchases p JOIN p.certificate c JOIN c.tags t " +
            "WHERE u.id = :userId GROUP BY t ORDER BY COUNT(t) DESC, SUM(p.price) DESC LIMIT 1")*/
    Tag findMostUsedTagByUser(int userId);
}
