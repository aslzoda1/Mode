package com.example.mode;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    Button btnSave, btnShow, btnDelete, btnExit;
    TextView tvGreeting;
    Switch themeSwitch;
    LinearLayout mainLayout;

    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_NAME = "username";
    private static final String KEY_THEME = "isDarkMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI elementlarini bog'lash
        etName = findViewById(R.id.etName);
        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);
        btnDelete = findViewById(R.id.btnDelete);
        btnExit = findViewById(R.id.btnExit);
        tvGreeting = findViewById(R.id.tvGreeting);
        themeSwitch = findViewById(R.id.themeSwitch);
        mainLayout = findViewById(R.id.mainLayout);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

         boolean isDarkMode = sharedPreferences.getBoolean(KEY_THEME, false);
        themeSwitch.setChecked(isDarkMode);
        applyTheme(isDarkMode);

         btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            if (!name.isEmpty()) {
                sharedPreferences.edit().putString(KEY_NAME, name).apply();
                Toast.makeText(this, "Saqlandi!", Toast.LENGTH_SHORT).show();
            }
        });

         btnShow.setOnClickListener(v -> {
            String savedName = sharedPreferences.getString(KEY_NAME, "User");
            tvGreeting.setText("Hello, " + savedName + " 👋");
        });

         btnDelete.setOnClickListener(v -> {
            sharedPreferences.edit().remove(KEY_NAME).apply();
            tvGreeting.setText("Hello, 👋");
            etName.setText("");
            Toast.makeText(this, "O'chirildi!", Toast.LENGTH_SHORT).show();
        });

         themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean(KEY_THEME, isChecked).apply();
            applyTheme(isChecked);
        });

         btnExit.setOnClickListener(v -> finish());
    }

    private void applyTheme(boolean isDark) {
        if (isDark) {
            mainLayout.setBackgroundColor(Color.parseColor("#121212"));
            tvGreeting.setTextColor(Color.WHITE);
         } else {
            mainLayout.setBackgroundColor(Color.WHITE);
            tvGreeting.setTextColor(Color.BLACK);
        }
    }
}