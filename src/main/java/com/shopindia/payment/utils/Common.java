package com.shopindia.payment.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Common {
    public static String getShortUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX).toUpperCase();
    }
}
