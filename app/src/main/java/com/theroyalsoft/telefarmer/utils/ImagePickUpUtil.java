package com.theroyalsoft.telefarmer.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 21/10/23.
 */
public class ImagePickUpUtil {
    public static final int REQUEST_CODE_GALLERY = 103;
    public static final int REQUEST_CODE_CAMERA = 102;
    public static final int REQUEST_CODE_VOICE = 104;


    ///========================================================================

    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();

    }

    ///========================================================================

    private static void showPictureDialog(Activity activity) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(activity);
        pictureDialog.setTitle("Pick Up Image");
        String[] pictureDialogItems = {"Gallery", "Camera"};

        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery(activity);
                                break;
                            case 1:
                                takePhotoFromCamera(activity);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    ///========================================================================
    private static void choosePhotoFromGallery(Activity activity) {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        activity.startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);

/*
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_GALLERY);*/
    }

    private static void takePhotoFromCamera(Activity activity) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public String saveImage(Activity activity, Bitmap myBitmap, String imageDirectory) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + imageDirectory);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            MediaScannerConnection.scanFile(activity,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"},
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Timber.d("Scan Completed path:" + path);
                            Timber.d("Scan Completed uri:" + uri);
                        }
                    });

            fo.close();

            Timber.d("File Saved::--->" + f.getAbsolutePath());


            return f.getAbsolutePath();

        } catch (IOException e1) {
            Timber.d("Error:" + e1.toString());

        }
        return "";
    }


    //==========================================================================


    public static File getFile(Context context, Bitmap bm) {

        File imgFile = new File(context.getCacheDir(), "image-"+System.currentTimeMillis()+".jpg");
        try {
            imgFile.createNewFile();
        } catch (IOException e) {

            Timber.d("Error:" + e.toString());
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapData = bos.toByteArray();

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(imgFile);
            fos.write(bitmapData);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            Timber.d("Error:" + e.toString());
        } catch (IOException e) {
            Timber.d("Error:" + e.toString());
        }
        return imgFile;
    }


    //==========================================================================


    public static Uri getImageUri(Bitmap inImage, Context context) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //==========================================================================
}
