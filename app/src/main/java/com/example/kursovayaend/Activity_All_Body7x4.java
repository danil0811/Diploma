package com.example.kursovayaend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_All_Body7x4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_body7x4);

        // Находим ImageView и устанавливаем для него обработчик нажатия
        ImageView firstDayFirstWeekImageView = findViewById(R.id.FirstDayFirstWeek);
        firstDayFirstWeekImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода на активность Tren_all_body_7x4
                Intent intent = new Intent(Activity_All_Body7x4.this, Tren_all_body_7x4.class);
                // Запускаем активность
                startActivity(intent);
            }
        });

        // Находим кнопку и устанавливаем для нее обработчик нажатия
        Button startProgramButton = findViewById(R.id.StartProgrammAllBody7x4);
        startProgramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода на активность Tren_all_body_7x4
                Intent intent = new Intent(Activity_All_Body7x4.this, Tren_all_body_7x4.class);
                // Запускаем активность
                startActivity(intent);
            }
        });
    }
}
