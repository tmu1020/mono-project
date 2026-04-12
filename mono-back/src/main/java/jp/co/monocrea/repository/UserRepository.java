package jp.co.monocrea.repository;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import java.util.Map;
import jp.co.monocrea.entity.UserEntity;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    public UserEntity findByUserId(String userId) {
        return find("userId", userId).firstResult();
    }

    public PanacheQuery<UserEntity> search(String query, Sort sort, Map<String, Object> params) {
        return find(query, sort, params);
    }
}