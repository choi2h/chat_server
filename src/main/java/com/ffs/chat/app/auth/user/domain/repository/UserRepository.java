package com.ffs.chat.app.auth.user.domain.repository;

import com.ffs.chat.app.auth.user.domain.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByLoginId(String loginId);
}
