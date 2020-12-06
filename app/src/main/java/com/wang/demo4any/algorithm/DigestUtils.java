package com.wang.demo4any.algorithm;

import androidx.annotation.NonNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

public final class DigestUtils {

    private DigestUtils() {
    }

    public static byte[] md5(@NonNull byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        return md.digest();
    }

    public static String md5Hex(@NonNull byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        byte[] digest = md.digest(); // 16 bytes
        // String.format("%032x", new BigInteger(1, digest));
        return String.format("%0" + digest.length + "x", new BigInteger(1, digest));
    }

    public static String md5Hex(@NonNull String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        // String.format("%032x", new BigInteger(1, digest));
        return String.format("%0" + digest.length + "x", new BigInteger(1, digest));
    }

    public static byte[] sha1(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        return md.digest();
    }

    public static String sha1Hex(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        byte[] digest = md.digest(); // 20 bytes
        // String.format("%040x", new BigInteger(1, digest));
        return String.format("%0" + digest.length + "x", new BigInteger(1, digest));
    }

    public static String sha1Hex(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        // String.format("%040x", new BigInteger(1, digest));
        return String.format("%0" + digest.length + "x", new BigInteger(1, digest));
    }

    public static byte[] sha256(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        return md.digest();
    }

    public static String sha256Hex(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        byte[] digest = md.digest(); // 32 bytes
        // String.format("%064x", new BigInteger(1, digest));
        return String.format("%0" + digest.length + "x", new BigInteger(1, digest));
    }

    public static byte[] hmac(byte[] input) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
            SecretKey secretKey = keyGen.generateKey();

            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(secretKey);
            mac.update(input);
            byte[] bytes = mac.doFinal();
            return bytes;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hmacHex(byte[] input) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5"); // "HmacSHA1", "HmacSHA256"
            SecretKey secretKey = keyGen.generateKey();
            byte[] encoded = secretKey.getEncoded();

            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(secretKey);
            mac.update(input);
            byte[] digest = mac.doFinal();
            return String.format("%0" + digest.length + "x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sha256Hex(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        // String.format("%064x", new BigInteger(1, digest));
        return String.format("%0" + digest.length + "x", new BigInteger(1, digest));
    }


    enum DigestAlgorithm {
        MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256");
        String str;

        DigestAlgorithm(String str) {
            this.str = str;
        }

        public String get() {
            return str;
        }
    }

    public static byte[] digest(DigestAlgorithm algorithm, byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm.get());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        return md.digest();
    }

    public static String digestHex(DigestAlgorithm algorithm, byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm.get());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        byte[] digest = md.digest(); // 32 bytes
        return String.format("%0" + digest.length + "x", new BigInteger(1, digest));
    }

    public static String digestHex(DigestAlgorithm algorithm, String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm.get());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return String.format("%0" + digest.length + "x", new BigInteger(1, digest));
    }
}
