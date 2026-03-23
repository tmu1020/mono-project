package jp.co.monocrea.repository;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jp.co.monocrea.entity.UserEntity;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    public UserEntity findByUserId(String userId) {
        return find("userId", userId).firstResult();
    }
}