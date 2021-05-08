package com.saga.kursayin.security;

import java.util.Random;

public class IdCrypto {
    private static final int len = 12;

    public static String encryptid(Long id) {
        String temp = new String();
        String char_id = id.toString();
        int id_len = char_id.length();
        Random rand = new Random();
        for (int i = 0; i < id_len; i++) {
            temp += rand.nextInt(10);
            temp += char_id.charAt(i);
        }
        for (int i = id_len * 2; i < len - 1; i++) {
            temp += rand.nextInt(10);
        }
        temp += id_len;
        return temp;
    }

    public static Long decryptid(String idcrypt) {
        int id_len = Character.getNumericValue(idcrypt.charAt(len - 1));
        String retid = "";
        for (int i = 1; i <= id_len * 2; i += 2) {
            retid += idcrypt.charAt(i);
        }
        return Long.parseLong(retid);
    }
}
