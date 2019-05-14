package com.itcast.whw.tool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.widget.Toast;

import com.itcast.whw.activity.currency_conversion.CurrencyConversionActivity;
import com.itcast.whw.activity.drawing_board.DrawingBoardActivity;
import com.itcast.whw.activity.search_near.NearMapActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 收藏工具
 */
public class FunctionTool {

    /**
     * 根据工具名称，跳转界面
     *
     * @param context
     * @param toolName
     */
    public static void startActivity(Context context, String toolName) {
        if (toolName.equals("查看附近")) {
            context.startActivity(new Intent(context, NearMapActivity.class));
        } else if (toolName.equals("汇率转换")) {
            context.startActivity(new Intent(context, CurrencyConversionActivity.class));
        } else if (toolName.equals("画图")) {
            context.startActivity(new Intent(context, DrawingBoardActivity.class));
        }
    }

    /**
     * 保存图片
     *
     * @param bitmap
     * @param context
     */
    public static void savePicture(Bitmap bitmap, Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), SystemClock.uptimeMillis() + ".png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fos != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//如果是4.4及以上版本
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file); //out is your output file
                mediaScanIntent.setData(contentUri);
                context.sendBroadcast(mediaScanIntent);
            } else {
                context.sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://"
                                + Environment.getExternalStorageDirectory())));
            }

        }
    }

    /**
     * 保存图片，并显示提示信息
     *
     * @param bitmap
     * @param context
     * @param str
     */
    public static void savePicture(Bitmap bitmap, Context context, String str) {
        savePicture(bitmap, context);
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
    }
}
