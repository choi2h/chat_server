package com.ffs.chat.app.auth;

import com.ffs.chat.app.auth.user.domain.User;
import com.ffs.chat.app.auth.user.domain.repository.UserRepository;
import com.ffs.chat.exception.DefaultResultCode;
import com.ffs.chat.exception.ServiceResultCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserProvider {

    private final UserRepository userRepository;

    public AuthUser getAuthUser(String loginId) {
        Optional<User> userOptional = userRepository.findByLoginId(loginId);
        if(userOptional.isEmpty()) {
            throw new ServiceResultCodeException(DefaultResultCode.FAILED);
        }

        User user = userOptional.get();
        return getAuthUser(user.getId(), user);
    }

    private AuthUser getAuthUser(Long id, User user) {
        return AuthUser.builder()
                .id(id)
                .name(user.getName())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .role(user.getRole())
                .branchId(user.getBranchId())
                .build();

    }
}
