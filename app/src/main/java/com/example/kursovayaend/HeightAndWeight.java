package com.example.kursovayaend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kursovayaend.Model.DataManager;

public class HeightAndWeight extends AppCompatActivity {

    private EditText editTextWeight;
    private EditText editTextHeight;
    private Spinner spinnerGender;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height_and_weight);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        spinnerGender = findViewById(R.id.spinnerGender);
        Button btnSaveData = findViewById(R.id.btnSaveData);

        // Определите массив значений для пола
        String[] genderOptions = {"Выберите ваш пол", "Мужской", "Женский"};

        // Создайте ArrayAdapter с использованием массива и стандартного макета выпадающего списка
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderOptions);

        // Устанавливаем адаптер в Spinner
        spinnerGender.setAdapter(genderAdapter);

        dataManager = new DataManager(this);

        // Проверяем, вносил ли пользователь данные ранее
        if (dataManager.areInitialUserDataInserted()) {
            // Если данные уже внесены, переходим на следующую активность
            Intent intent = new Intent(HeightAndWeight.this, ViborTrenirovki.class);
            startActivity(intent);
            finish(); // Закрываем текущую активность, чтобы пользователь не мог вернуться
        }

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    private void saveUserData() {
        String weightText = editTextWeight.getText().toString().trim();
        String heightText = editTextHeight.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();

        if (TextUtils.isEmpty(weightText) || TextUtils.isEmpty(heightText) || gender.equals("Выберите ваш пол")) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        Double weight = null;
        Double height = null;

        try {
            weight = Double.parseDouble(weightText);
            height = Double.parseDouble(heightText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        long result = dataManager.insertUserData(weight, height, gender);

        if (result != -1) {
            Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_SHORT).show();

            // Устанавливаем флаг о том, что начальные данные внесены
            dataManager.setInitialUserDataInserted();

            // После успешного сохранения данных, переход на активность ViborTrenirovki
            Intent intent = new Intent(HeightAndWeight.this, ViborTrenirovki.class);
            startActivity(intent);
            finish(); // Закрываем текущую активность, чтобы пользователь не мог вернуться
        } else {
            Toast.makeText(this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();
        }
    }
}
