package com.example.cholademo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.opensooq.supernova.gligar.GligarPicker;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;


public class MultiSelect_Lib extends AppCompatActivity {

    int PICKER_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select_lib);




    }

    private void open_dialog() {

        final Dialog dialog_data = new Dialog(this);

        dialog_data.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog_data.getWindow().setGravity(Gravity.CENTER);

        dialog_data.setContentView(R.layout.custom_alertdialog);

        WindowManager.LayoutParams lp_number_picker = new WindowManager.LayoutParams();
        Window window = dialog_data.getWindow();
        lp_number_picker.copyFrom(window.getAttributes());

        lp_number_picker.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp_number_picker.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp_number_picker);


        dialog_data.show();
    }


    public void pick(View view) {

        open_dialog();

//        new GligarPicker().requestCode(PICKER_REQUEST_CODE).limit(6).withActivity(this).show();

//        FishBun.with(this)
//                .setImageAdapter(new GlideAdapter())
//                .setIsUseDetailView(false)
//                .setMinCount(1)
//                .setMaxCount(7)
//              .startAlbum();


//        FilePickerBuilder.getInstance()
//                .setMaxCount(5)
//                .pickFile(this);



//        FishBun.with(this)
//                .setImageAdapter(new GlideAdapter())
//                .setIsUseDetailView(false)
//                .setMaxCount(7)
//                .setMinCount(1)
//                .setActionBarColor(Color.parseColor("#795548"), Color.parseColor("#5D4037"), false)
//                .setActionBarTitleColor(Color.parseColor("#ffffff"))
//                .setAlbumSpanCount(2, 4)
//                .setButtonInAlbumActivity(false)
//                .setCamera(true)
//                .setReachLimitAutomaticClose(true)
//                .setAllViewTitle("All")
//                .setActionBarTitle(\"Image Library")
//                .textOnImagesSelectionLimitReached("Limit Reached!")
//                .textOnNothingSelected("Nothing Selected")
//                .setSelectCircleStrokeColor(Color.BLACK)
//                .isStartInAllView(false)
//                .startAlbum();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && resultCode == RESULT_OK && data != null)
        {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Log.e("pick" , "ok") ;
            }
        }


            if (requestCode == FishBun.FISHBUN_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Toast.makeText(MultiSelect_Lib.this, "img picked", Toast.LENGTH_SHORT).show();

            ArrayList<Uri>array_uri = new ArrayList<>();

            array_uri = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);

            for (int i = 0; i < array_uri.size(); i++)
            {
                Uri uri= Uri.parse("content://media"+ array_uri.get(i).getPath());
                Log.e("getPath"+i , array_uri.get(i).getPath().toString() );
//                Log.e("URi"+i , uri.() );



                String path = getPathFromUri(MultiSelect_Lib.this, uri );
                File ff = new File(path);

                Log.e("URi"+i , array_uri.get(i).getPath().toString() );

               String encodeFileToBase64Binary = encodeFileToBase64Binary(ff);

                String extension = ff.getAbsolutePath().substring(ff.getAbsolutePath().lastIndexOf("."));

                Log.e("extensido2", extension);

                Log.e("encodeFile", encodeFileToBase64Binary);

            }

        }


            if (resultCode != Activity.RESULT_OK)
            {


        }

//        switch (requestCode){
//            case PICKER_REQUEST_CODE : {
//                String pathsList[]= data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); // return list of selected images paths.
////                imagesCount.text = "Number of selected Images: " + pathsList.length;
//                break;
//            }
//        }
    }


    private String encodeFileToBase64Binary(File yourFile) {
        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e("anipamp", e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
        return encoded;
    }




    public String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        try {

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }

                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    } else if ("document".equals(type)) {
                        contentUri = MediaStore.Files.getContentUri("external");
                    }
//                else if ("pdf".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }

            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }

            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else if ("document".equals(type)) {
                    contentUri = MediaStore.Files.getContentUri("external");
                }
//                else if ("pdf".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }

            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        catch (Exception ee){
            Log.e("getPathFromUri_" , uri.toString()) ;
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,null);

            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }
        catch (NullPointerException ee)
        {
            Log.e("Error_select_file" , "unsupported file");
        }
        finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }



}