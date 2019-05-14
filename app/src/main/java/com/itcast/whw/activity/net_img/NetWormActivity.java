package com.itcast.whw.activity.net_img;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itcast.whw.R;
import com.itcast.whw.tool.NetUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

public class NetWormActivity extends AppCompatActivity implements View.OnClickListener {
    private final int SUCCESS = 1;

    private ArrayList<String> imgUrlList = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    String resource = msg.obj.toString();
                    String reg = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?";
                    Pattern pattern = Pattern.compile(reg);
                    Matcher matcher = pattern.matcher(resource);
                    while (matcher.find()) {
                        String group = matcher.group();
                        Log.i("TEST", group);
                        if (group.endsWith("jpeg") || group.endsWith("jpg") || group.endsWith("gif") || group.endsWith("png") || group.endsWith("ico")) {
                            if (!imgUrlList.contains(group)) {
                                imgUrlList.add(group);
                            }
                        }
                    }
                    if (imgUrlList.size() == 0) {
                        Toast.makeText(getApplicationContext(), "你输入的网址中没有找到图片", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(NetWormActivity.this, NetImgsActivity.class);
                        intent.putStringArrayListExtra("imgs", imgUrlList);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    private EditText zack_et_url;
    private Button zack_btn_getImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_worm);
        initView();
    }

    private void initView() {
        zack_et_url = (EditText) findViewById(R.id.zack_et_url);
        zack_btn_getImg = (Button) findViewById(R.id.zack_btn_getImg);

        zack_btn_getImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zack_btn_getImg:
                NetUtil netUtil = NetUtil.getInstance();
                String url = zack_et_url.getText().toString().trim();
                if(url.endsWith("") || url == null){
                    url = "http://www.sohu.com/a/229186816_502280";
                }
                netUtil.getDataAsynFromNet(url, new NetUtil.MyNetCall() {
                    @Override
                    public void success(okhttp3.Call call, Response response) throws IOException {
                        String source = response.body().string();
                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = source;
                        handler.sendMessage(msg);
                    }
                    @Override
                    public void failed(okhttp3.Call call, IOException e) {
                        Toast.makeText(getApplicationContext(), "网络错误或者网址错误", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

}
