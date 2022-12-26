package com.example.mpproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Size;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class HUtils {

    static public void showMessage(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    static public void closeKeyboard(MainActivity activity) {
        View view = activity.getCurrentFocus();
        closeKeyboard(activity, view);
    }

    static public void closeKeyboard(MainActivity activity, View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    static public String getDateFormat(String pattern, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String format = formatter.format(date);

        return format;
    }

    static public void showDeleteDialog(Context context, DialogInterface.OnClickListener positiveListener,
                                        DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("삭제");
        dialog.setMessage("정말 삭제하시겠습니까?");
        dialog.setPositiveButton("확인", positiveListener);
        dialog.setNegativeButton("취소", negativeListener);
        dialog.show();
    }

    static public void showLockDialog(Context context, LockLevel initLevel, Consumer<LockLevel> onSubmit, Runnable onCancel) {
        int initValue = lockLevelToDialogIndex(initLevel);
        AtomicInteger select = new AtomicInteger(initValue);
        String[] options = new String[]{"잠금 안함", "마스터키 잠금", "개별키 잠금"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("잠금");

        dialog.setSingleChoiceItems(options, initValue, (d, i) -> {
            select.set(i);
        });
        dialog.setPositiveButton("확인", (d, i) -> {
            if (onSubmit != null) onSubmit.accept(DialogIndexToLockLevel(select.get()));
        });
        dialog.setNegativeButton("취소", (d, i) -> {
            if (onCancel != null) onCancel.run();

        });
        dialog.show();
    }

    static private int lockLevelToDialogIndex(LockLevel level) {
        switch (level) {
            default:
            case NOTHING:
                return 0;
            case MASTER_KEY:
                return 1;
            case PRIVATE_KEY:
                return 2;
        }
    }

    static private LockLevel DialogIndexToLockLevel(int index) {
        switch (index) {
            default:
            case 0:
                return LockLevel.NOTHING;
            case 1:
                return LockLevel.MASTER_KEY;
            case 2:
                return LockLevel.PRIVATE_KEY;
        }
    }


    static public void showInputPasswdDialog(Context context, Consumer<String> onSubmit) {
        showInputPasswdDialog(context, "비밀번호 입력", onSubmit);
    }

    static public void showInputPasswdDialog(Context context, String title, Consumer<String> onSubmit) {
        View dialogView = (View) View.inflate(context, R.layout.dialog_input, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setView(dialogView);
        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText eText = dialogView.findViewById(R.id.edtInput);
                onSubmit.accept(eText.getText().toString());
            }
        });
        dialog.setNegativeButton("취소", null);
        dialog.show();
    }


    public static int getThemeColor(Context context, int res) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(res, typedValue, true);
        return ContextCompat.getColor(context, typedValue.resourceId);
    }

    public static void saveBitmap(Context context, Bitmap bitmap, String filename) {
        File file = new File(context.getFilesDir(), filename);
        OutputStream os = null;

        try {
            file.createNewFile();
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadBitmap(Context context, String filename) {
        File file = context.getFilesDir();
        try {
            FileInputStream fs = context.openFileInput(filename);

            Bitmap bitmap = BitmapFactory.decodeStream(fs);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        return null;
    }

    public static void deleteBitmap(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    public void openCamera(Context context) throws CameraAccessException {
        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        String cameraId = manager.getCameraIdList()[0];
        CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
        int level = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
    }
}













