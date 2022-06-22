package com.metadjioo_ds.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static String getSHA256SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String randomString(int length) {
        // create a string of all characters
        String alphabet = "0123456789abcdefghijklmnopqrstuvABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }
        return sb.toString();
    }

    /**
     * Compute the optimal line size given the size of the elements and the max of the size
     * @param sizeList size of the elements
     * @param maxByLine bound max
     * @return number of elements in one line
     */
    public static int organize(int sizeList, int maxByLine) {
        if(sizeList==1){
            return 1;
        } else {
            int nByLine = -1;
            float score = Float.MAX_VALUE;
            float currScore;
            for(int i = maxByLine; i >= 2;i--){
                //Score with the amount of empty places and the number of line
                int nbEmptyPlace = ((i - (sizeList % i))% i);
                int nbLine = (int)Math.ceil(sizeList/(float)i);
                currScore = nbEmptyPlace + nbLine;
                if(currScore < score){
                    score = currScore;
                    nByLine = i;
                }
            }
            return nByLine;
        }

    }

    /**
     * Generate a value suitable for use in setId.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * by omegasoft7 -> https://gist.github.com/omegasoft7/fdf7225a5b2955a1aba8
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}
