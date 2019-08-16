package com.demo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.one.R;

/**
 * Description Espresso测试框架示例Activity
 * Author wanghengwei
 * Date   2019/8/16 11:12
 */
public class EspressoActivity extends BaseActivity {

    private TextView tvResult;
    private Button btnTest;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_espresso);

        tvResult = findViewById(R.id.tv_result);
        btnTest = findViewById(R.id.btn_test);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("测试点击事件成功");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().equals("test111")
                        && etPassword.getText().toString().equals("123456")) {
                    tvResult.setText("登录成功");
                }
            }
        });
    }
}
