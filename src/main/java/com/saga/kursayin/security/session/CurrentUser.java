package com.saga.kursayin.security.session;


import com.saga.kursayin.persistence.entity.UserEntity;
import lombok.Data;

/**
 * @author Gurgen Poghosyan
 */
@Data
public class CurrentUser {

    private SessionUser sessionUser;

    public CurrentUser(UserEntity userEntity) {
        this.sessionUser = SessionUser.mapUserToSessionUser(userEntity);
    }
}
