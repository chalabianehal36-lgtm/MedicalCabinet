package com.example.medicalcabinet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RadioGroup rgLanguage = findViewById(R.id.rgLanguage);
        Button btnSave        = findViewById(R.id.btnSaveSettings);
        RadioButton rbArabic  = findViewById(R.id.rbArabic);
        RadioButton rbFrench  = findViewById(R.id.rbFrench);
        RadioButton rbEnglish = findViewById(R.id.rbEnglish);

        // تحديد اللغة الحالية
        String currentLang = LanguageHelper.getSavedLanguage(this);
        switch (currentLang) {
            case "fr": rbFrench.setChecked(true); break;
            case "en": rbEnglish.setChecked(true); break;
            default:   rbArabic.setChecked(true); break;
        }

        btnSave.setOnClickListener(v -> {
            int selectedId = rgLanguage.getCheckedRadioButtonId();
            String langCode = "ar";

            if (selectedId == R.id.rbFrench) {
                langCode = "fr";
            } else if (selectedId == R.id.rbEnglish) {
                langCode = "en";
            }

            // حفظ اللغة وإعادة تشغيل التطبيق
            LanguageHelper.setLanguage(this, langCode);

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}