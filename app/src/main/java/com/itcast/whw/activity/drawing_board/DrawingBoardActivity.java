package com.itcast.whw.activity.drawing_board;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.itcast.whw.R;
import com.itcast.whw.tool.FunctionTool;

/**
 * 画图工具
 */
public class DrawingBoardActivity extends AppCompatActivity {

    private Toolbar drawing_toolbar;
    private ImageView iv_paint;
    private Paint paint;
    private Canvas canvas;
    /**
     * 初始画笔粗细
     */
    private int position = 4;
    private Bitmap copyBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_board);
        initView();

        setSupportActionBar(drawing_toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //获取屏幕分辨率
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //创建一张空白图片
        copyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        //创建画笔
        paint = new Paint();
        paint.setStrokeWidth(position);
        //创建画布
        canvas = new Canvas(copyBitmap);
        //开始作画
        canvas.drawBitmap(copyBitmap,new Matrix(), paint);
        iv_paint.setImageBitmap(copyBitmap);

        //给画板设置触摸事件
        iv_paint.setOnTouchListener(new View.OnTouchListener() {

            private int startY;
            private int startX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获取当前事件类型
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN://按下
                        //获取开始位置
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE://移动
                        //获取结束位置
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        //画线
                        canvas.drawLine(startX, startY,stopX,stopY, paint);
                        //显示到控件上
                        iv_paint.setImageBitmap(copyBitmap);

                        //更新坐标
                        startX = stopX;
                        startY = stopY;
                        break;
                    case MotionEvent.ACTION_UP://抬起
                        break;
                }

                //事件处理完毕
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawing_board_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.color://更改画笔颜色
                changePaintColor();
                break;
            case R.id.thick://更改画笔粗细
                changePaintThick();
                break;
            case R.id.clear://清空画板
                clearCanvas();
                break;
            case R.id.save://保存图片
                savePicture();
                break;
        }
        return true;
    }

    /**
     * 保存图片
     */
    private void savePicture() {
        FunctionTool.savePicture(copyBitmap,this,"保存成功");
    }

    /**
     * 清空画板
     */
    private void clearCanvas() {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    /**
     * 更改画笔粗细
     */
    private void changePaintThick() {
        View view = LayoutInflater.from(this).inflate(R.layout.drawing_paint_thick, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择画笔粗细")
                .setView(view)
                .setPositiveButton("确定",null)
                .show();
        SeekBar seekBar = view.findViewById(R.id.paint_seekBar);
        final TextView tv_thick = view.findViewById(R.id.paint_thick);
        //显示当前粗细
        tv_thick.setText(position + "");
        //设置最大进度
        seekBar.setMax(50);
        //显示当前进度
        seekBar.setProgress(position);

        //设置进度条的拖动事件
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //不停地改变当前显示的粗细值
                tv_thick.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //当拖动停止时，获得当前进度并设置给初始进度
                position = seekBar.getProgress();
                //更改画笔粗细
                paint.setStrokeWidth(position);
            }
        });
    }

    private void initView() {
        drawing_toolbar = (Toolbar) findViewById(R.id.drawing_toolbar);
        iv_paint = (ImageView) findViewById(R.id.iv_paint);
    }

    /**
     * 更改画笔颜色
     */
    private void changePaintColor(){
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("选择颜色")
                .initialColor(Color.parseColor("#ffffff"))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        //选中颜色，颜色为selectedColor
                        changeColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    /**
     * 将颜色设置到画笔上
     * @param selectedColor
     */
    private void changeColor(int selectedColor) {
        paint.setColor(selectedColor);
    }
}
