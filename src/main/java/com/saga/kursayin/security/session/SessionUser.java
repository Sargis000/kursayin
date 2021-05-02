package com.saga.kursayin.security.session;

import com.saga.kursayin.persistence.entity.UserEntity;
import lombok.Data;

/**
 * @author Gurgen Poghosyan
 */
@Data
public class SessionUser {

    public static final String SESSION_USER_KEY = "SESSION_USER";

    private Long id;
    private String username;

    public static SessionUser mapUserToSessionUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(userEntity.getId());
        sessionUser.setUsername(userEntity.getUsername());
        return sessionUser;
    }
}
