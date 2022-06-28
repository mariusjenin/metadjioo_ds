package com.metadjioo_ds.utils;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.app.activity.MDSActivitySecondScreen;

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
     *
     * @param sizeList  size of the elements
     * @param maxByLine bound max
     * @return number of elements in one line
     */
    public static int organize(int sizeList, int maxByLine) {
        if (sizeList == 1) {
            return 1;
        } else {
            int nByLine = -1;
            float score = Float.MAX_VALUE;
            float currScore;
            for (int i = maxByLine; i >= 2; i--) {
                //Score with the amount of empty places and the number of line
                int nbEmptyPlace = ((i - (sizeList % i)) % i);
                int nbLine = (int) Math.ceil(sizeList / (float) i);
                currScore = nbEmptyPlace + nbLine;
                if (currScore < score) {
                    score = currScore;
                    nByLine = i;
                }
            }
            return nByLine;
        }

    }

    public static void launchActivityOnSecondScreen(Class<?> clss) {
        MDSActivitySecondScreen activitySecondScreen = MDSApp.getCurrentSecondScreenAct();
        if(activitySecondScreen == null ||activitySecondScreen.getClass() != clss){
            if(activitySecondScreen != null) activitySecondScreen.finish();
            Context context = MDSApp.getContext();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
                if (dm != null) {
                    Display[] displays = dm.getDisplays();
                    if (displays.length > 1) {
                        Intent intent = new Intent(context, clss);
                        ActivityOptions activityOptions = ActivityOptions.makeBasic();
                        activityOptions.setLaunchDisplayId(displays[1].getDisplayId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent, activityOptions.toBundle());
                    }
                }
            }
        }

    }
    public static int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, MDSApp.getContext().getResources().getDisplayMetrics());
    }

    public static int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, MDSApp.getContext().getResources().getDisplayMetrics());
    }
}
