package com.arek00.xmlReader.helpers;

import ch.qos.logback.core.encoder.ByteArrayUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {
    private static MessageDigest digest;

    private MD5Generator() {
    }

    public static String generateMD5Hash(String text) {
        byte[] textBytes = null;

        try {
            digest = MessageDigest.getInstance("MD5");
            textBytes = text.getBytes("UTF-8");
        } catch (NoSuchAlgorithmException e) {
            try {
                digest = MessageDigest.getInstance("md5");
            } catch (NoSuchAlgorithmException e1) {
                MyLogger.logError("MD5 Generator ERROR:", e1.getMessage());
                e1.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            MyLogger.logError("MD5 GENERATOR ERROR:", e.getMessage());
        }

        digest.update(textBytes);
        byte[] hash = digest.digest();

        return new String(ByteArrayUtil.toHexString(hash));
    }

}
