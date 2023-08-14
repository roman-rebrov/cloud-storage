package com.cloudstorage.storage.repository;

import com.cloudstorage.storage.entity.persistence.UserPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserPersistence, Long> {
    public UserPersistence findByLoginAndPassword(String login, String password);
}
