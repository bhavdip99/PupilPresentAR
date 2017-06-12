package com.bhavdip.pupilpresentar.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by bhavdip on 12/6/17.
 */

public class Utility {
    // convert from bitmap to byte array
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getByteArrayAsBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {

            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

}
