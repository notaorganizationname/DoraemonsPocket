package com.itcast.whw.activity.currency_conversion;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.itcast.whw.R;

/**
 * 汇率转换工具
 */
public class CurrencyConversionActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar currency_toolbar;
    private Spinner spinner_left;
    private Spinner spinner_right;
    private EditText et_money_j;
    private Button btn_conversion_j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion);
        initView();

        setSupportActionBar(currency_toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        currency_toolbar = (Toolbar) findViewById(R.id.currency_toolbar);
        spinner_left = (Spinner) findViewById(R.id.spinner_left);
        spinner_right = (Spinner) findViewById(R.id.spinner_right);
        et_money_j = (EditText) findViewById(R.id.et_money_j);
        btn_conversion_j = (Button) findViewById(R.id.btn_conversion_j);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.money_spinner,android.R.layout.simple_list_item_1);
        spinner_left.setAdapter(adapter);
        spinner_right.setAdapter(adapter);

        //注册监听
        //TODO 未完工，待续。。。。
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_conversion_j:

                break;
        }
    }
}
