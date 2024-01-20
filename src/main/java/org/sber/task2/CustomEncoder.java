package org.sber.task2;

import java.nio.charset.StandardCharsets;

public class CustomEncoder {
    public static byte[] encode(byte[] bytes, String key) {
        byte[] keyCode = key.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ keyCode[i % (keyCode.length - 1)]);
        }
        return bytes;
    }

    public static byte[] decode(byte[] bytes, String key){
        byte[] keyCode = key.getBytes(StandardCharsets.UTF_8);

        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte) (bytes[i] ^ keyCode[i & (keyCode.length - 1)]);
        }
        return bytes;
    }
}
