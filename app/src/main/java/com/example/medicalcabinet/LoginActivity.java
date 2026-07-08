package com.example.medicalcabinet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

public class LoginActivity extends BaseActivity {

    private static final String PREF_NAME = "MedicalCabinetPrefs";
    SharedPreferences prefs;

    EditText etIdentifier, etPassword, etConfirmPassword, etFullName;
    Button btnAction;
    TextView tvSwitch, tvTitle, tvSubtitle, tvForgot;
    LinearLayout layoutConfirm, layoutName;

    boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        etIdentifier      = findViewById(R.id.etIdentifier);
        etPassword        = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFullName        = findViewById(R.id.etFullName);
        btnAction         = findViewById(R.id.btnAction);
        tvSwitch          = findViewById(R.id.tvSwitch);
        tvTitle           = findViewById(R.id.tvTitle);
        tvSubtitle        = findViewById(R.id.tvSubtitle);
        tvForgot          = findViewById(R.id.tvForgot);
        layoutConfirm     = findViewById(R.id.layoutConfirm);
        layoutName        = findViewById(R.id.layoutName);

        boolean hasAccount = prefs.getBoolean("has_account", false);
        if (hasAccount) {
            showLoginMode();
        } else {
            showRegisterMode();
        }

        btnAction.setOnClickListener(v -> {
            if (isLoginMode) handleLogin();
            else handleRegister();
        });

        tvSwitch.setOnClickListener(v -> {
            if (isLoginMode) showRegisterMode();
            else showLoginMode();
        });

        tvForgot.setOnClickListener(v -> showForgotDialog());
    }

    private void showLoginMode() {
        isLoginMode = true;
        tvTitle.setText(getString(R.string.login_title));
        tvSubtitle.setText(getString(R.string.welcome_back));
        btnAction.setText(getString(R.string.btn_login));
        tvSwitch.setText(getString(R.string.switch_to_register));
        tvForgot.setVisibility(View.VISIBLE);
        layoutConfirm.setVisibility(View.GONE);
        layoutName.setVisibility(View.GONE);
        etIdentifier.setHint(getString(R.string.identifier_hint));
        etPassword.setHint(getString(R.string.password_hint));
        clearErrors();
    }

    private void showRegisterMode() {
        isLoginMode = false;
        tvTitle.setText(getString(R.string.register_title));
        tvSubtitle.setText(getString(R.string.create_account_subtitle));
        btnAction.setText(getString(R.string.btn_register));
        tvSwitch.setText(getString(R.string.switch_to_login));
        tvForgot.setVisibility(View.GONE);
        layoutConfirm.setVisibility(View.VISIBLE);
        layoutName.setVisibility(View.VISIBLE);
        etIdentifier.setHint(getString(R.string.identifier_hint));
        etPassword.setHint(getString(R.string.password_hint));
        clearErrors();
    }

    private void handleLogin() {
        String identifier = etIdentifier.getText().toString().trim();
        String password   = etPassword.getText().toString().trim();

        if (identifier.isEmpty()) {
            etIdentifier.setError(getString(R.string.identifier_hint));
            etIdentifier.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.password_hint));
            etPassword.requestFocus();
            return;
        }

        String savedIdentifier = prefs.getString("identifier", "");
        String savedPassword   = prefs.getString("password", "");

        if (identifier.equals(savedIdentifier) && password.equals(savedPassword)) {
            prefs.edit().putBoolean("is_logged_in", true).apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            etPassword.setError(getString(R.string.error_wrong_credentials));
            etPassword.requestFocus();
        }
    }

    private void handleRegister() {
        String name       = etFullName.getText().toString().trim();
        String identifier = etIdentifier.getText().toString().trim();
        String password   = etPassword.getText().toString().trim();
        String confirm    = etConfirmPassword.getText().toString().trim();

        if (name.isEmpty()) {
            etFullName.setError(getString(R.string.error_enter_name));
            etFullName.requestFocus();
            return;
        }

        if (identifier.isEmpty()) {
            etIdentifier.setError(getString(R.string.error_enter_identifier));
            etIdentifier.requestFocus();
            return;
        }

        if (!isValidIdentifier(identifier)) {
            etIdentifier.setError(getString(R.string.error_invalid_identifier));
            etIdentifier.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.error_enter_password));
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError(getString(R.string.error_password_short));
            etPassword.requestFocus();
            return;
        }

        if (confirm.isEmpty()) {
            etConfirmPassword.setError(getString(R.string.error_confirm_password));
            etConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirm)) {
            etConfirmPassword.setError(getString(R.string.error_password_mismatch));
            etConfirmPassword.requestFocus();
            return;
        }

        prefs.edit()
                .putBoolean("has_account", true)
                .putString("full_name", name)
                .putString("identifier", identifier)
                .putString("password", password)
                .putBoolean("is_logged_in", true)
                .apply();

        Toast.makeText(this,
                getString(R.string.welcome_new_user) + " " + name,
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private boolean isValidIdentifier(String identifier) {
        if (Patterns.EMAIL_ADDRESS.matcher(identifier).matches()) return true;
        String phoneOnly = identifier.replaceAll("[+\\-\\s()]", "");
        return phoneOnly.matches("\\d{7,15}");
    }

    private void showForgotDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.forgot_password_title))
                .setMessage(getString(R.string.forgot_password_message))
                .setPositiveButton(getString(R.string.forgot_password_confirm), (d, w) -> {
                    prefs.edit()
                            .putBoolean("has_account", false)
                            .putBoolean("is_logged_in", false)
                            .remove("identifier")
                            .remove("password")
                            .remove("full_name")
                            .apply();
                    Toast.makeText(this,
                            getString(R.string.forgot_password_toast),
                            Toast.LENGTH_SHORT).show();
                    showRegisterMode();
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    private void clearErrors() {
        etIdentifier.setError(null);
        etPassword.setError(null);
        if (etConfirmPassword != null) etConfirmPassword.setError(null);
        if (etFullName != null) etFullName.setError(null);
    }
}