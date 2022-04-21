package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.window.SplashScreen;

import java.util.concurrent.Executor;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;

public class MainActivity extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Intent intent = new Intent(MainActivity.this, Splash.class);
                startActivity(intent);

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Успешно!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация")
                .setSubtitle("Прислоните палец")
                .setNegativeButtonText("Отмена")
                .build();

        biometricPrompt.authenticate(promptInfo);

        EditText editTextPassword2 = findViewById(R.id.editTextPassword2);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        CheckBox checkBox = findViewById(R.id.checkBox);
        EditText editTextLogin = findViewById(R.id.editTextLogin);
        EditText editTextPassword1 = findViewById(R.id.editTextPassword1);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        findViewById(R.id.btnLogin).setOnClickListener(view -> {
            if (btnLogin.getText().equals("Войти")) {
                editTextPassword2.setVisibility(View.GONE);
                btnLogin.setText("Зарегистрироваться");
                btnRegister.setText("Войти");
                checkBox.setVisibility(View.GONE);
            }
            else {
                editTextPassword2.setVisibility(View.VISIBLE);
                btnLogin.setText("Войти");
                btnRegister.setText("Зарегистрироваться");
                checkBox.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(view -> {
            String role = null;
            if (checkBox.getVisibility() == View.VISIBLE) {
                if (checkBox.isChecked()) role = "A";
                else role = "R";
            }

            if (btnRegister.getText().equals("Зарегистрироваться")) {
                if (editTextLogin.getText().length() >= 6 && editTextPassword1.getText().length() >= 6 && editTextPassword1.getText().toString().equals(editTextPassword2.getText().toString())) {
                    Cursor c = db.rawQuery("Select * from UserInfo where UserInfo.login='" + editTextLogin.getText().toString() + "'", null);
                    if (c.getCount() <= 0) {
                        Boolean checkInsertData = databaseHelper.insertUsers(editTextLogin.getText().toString(), editTextPassword1.getText().toString(), role);
                        if (role.equals("A")) {
                            Intent intent = new Intent(MainActivity.this, Administrator.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, User.class);
                            startActivity(intent);
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Данный логин уже занят!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
            }
            else {
                Cursor c = db.rawQuery("Select * from UserInfo where UserInfo.login='" + editTextLogin.getText().toString() + "' and UserInfo.password='" + editTextPassword1.getText().toString() +"' " +
                        "and UserInfo.role='A'", null);
                if (c.getCount() > 0)
                {
                    Intent intent = new Intent(MainActivity.this, Administrator.class);
                    startActivity(intent);
                    return;
                }
                Cursor c1 = db.rawQuery("Select * from UserInfo where UserInfo.login='" + editTextLogin.getText().toString() + "' and UserInfo.password='" + editTextPassword1.getText().toString() +"' " +
                        "and UserInfo.role='R'", null);
                if (c1.getCount() > 0)
                {
                    Intent intent = new Intent(MainActivity.this, User.class);
                    startActivity(intent);
                    return;
                }
                else Toast.makeText(getApplicationContext(), "Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}