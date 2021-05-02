package com.saga.kursayin.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gurgen Poghosyan
 */
@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String jwtToken;
}
