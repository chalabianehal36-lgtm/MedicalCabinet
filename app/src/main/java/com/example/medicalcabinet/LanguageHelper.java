package com.example.medicalcabinet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

public class LanguageHelper {

    private static final String PREF_NAME = "MedicalCabinetPrefs";
    private static final String KEY_LANG  = "app_language";

    // تطبيق اللغة على الـ Context
    public static Context applyLanguage(Context context) {
        String lang = getSavedLanguage(context);
        return setLocale(context, lang);
    }

    // تغيير اللغة وحفظها
    public static void setLanguage(Context context, String langCode) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_LANG, langCode).apply();
        setLocale(context, langCode);
    }

    // جلب اللغة المحفوظة
    public static String getSavedLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LANG, "ar");
    }

    private static Context setLocale(Context context, String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }
}