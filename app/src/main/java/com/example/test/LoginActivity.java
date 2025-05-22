package com.example.test;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private CheckBox checkboxRemember;
    private SharedPreferences sharedPreferences;

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;

    private final String VALID_EMAIL = "admindetection@gmail.com";
    private final String VALID_PASSWORD = "123456abcd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Đảm bảo layout là activity_main.xml

        //Remember Password
        checkboxRemember = findViewById(R.id.checkboxRemember);

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        //Check emial password
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (validateInput(email, password)) {
                if (email.equals(VALID_EMAIL) && password.equals(VALID_PASSWORD)) {
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    if (checkboxRemember.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();
                    }

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email không được để trống");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email không hợp lệ");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Mật khẩu không được để trống");
            return false;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Mật khẩu phải ít nhất 6 ký tự");
            return false;
        }
        return true;
    }
}

