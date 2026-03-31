package com.example.formapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    EditText etNama, etEmail, etPassword, etConfirm;
    RadioGroup rgGender;
    CheckBox cb1, cb2, cb3;
    Spinner spinner;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);

        rgGender = findViewById(R.id.rgGender);

        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);

        spinner = findViewById(R.id.spinner);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Spinner
        String[] jurusan = {"Informatika", "Sistem Informasi", "DKV"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, jurusan);
        spinner.setAdapter(adapter);

        // Real-time validation Email
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    etEmail.setError("Email tidak valid");
                } else {
                    etEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Real-time validation Password
        etConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etPassword.getText().toString().equals(s.toString())) {
                    etConfirm.setError("Password tidak sama");
                } else {
                    etConfirm.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Long Press
        btnSubmit.setOnLongClickListener(v -> {
            Toast.makeText(this, "Long Press Aktif", Toast.LENGTH_SHORT).show();
            return true;
        });

        // Klik Submit
        btnSubmit.setOnClickListener(v -> validate());
    }

    private void validate() {

        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        String confirm = etConfirm.getText().toString().trim();

        // Validasi kosong
        if (nama.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Semua data wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email tidak valid");
            return;
        }

        // Validasi password
        if (!pass.equals(confirm)) {
            etConfirm.setError("Password tidak sama");
            return;
        }

        // Validasi gender
        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi checkbox minimal 1
        int hobi = 0;
        if (cb1.isChecked()) hobi++;
        if (cb2.isChecked()) hobi++;
        if (cb3.isChecked()) hobi++;

        if (hobi < 1) {
            Toast.makeText(this, "Pilih minimal 1 hobi", Toast.LENGTH_SHORT).show();
            return;
        }


        // Dialog
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Data sudah benar?")
                .setPositiveButton("Ya", (dialog, which) ->
                        Toast.makeText(this, "Data berhasil disubmit", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Batal", null)
                .show();
    }
}