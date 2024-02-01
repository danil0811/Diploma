package com.example.kursovayaend;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.example.kursovayaend.Model.DataManager;
import com.example.kursovayaend.Model.Exercise;
import com.example.kursovayaend.ui.theme.ExerciseAdapter;

import java.util.List;

public class Tren_all_body_7x4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tren_all_body7x4);

        // Создаем DataManager
        DataManager dataManager = new DataManager(this);

        // Вызываем метод вставки данных по умолчанию
        dataManager.insertDefaultDataIfNeeded();

        // Получаем ListView
        ListView exerciseListView = findViewById(R.id.exerciseListView);

        // Получаем ID выбранной тренировки (замените на реальный ID)
        long selectedWorkoutID = 1; // Замените на реальный ID

        // Получаем упражнения для выбранной тренировки
        List<Exercise> exercises = dataManager.getExercisesForWorkout(selectedWorkoutID);

        // Проверяем, есть ли данные
        if (exercises != null && !exercises.isEmpty()) {
            Log.d("Tren_all_body_7x4", "Number of exercises: " + exercises.size());

            // Создаем адаптер
            ExerciseAdapter adapter = new ExerciseAdapter(this, exercises);

            // Привязываем адаптер к ListView
            exerciseListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Нет данных об упражнениях для выбранной тренировки", Toast.LENGTH_SHORT).show();
        }
    }
}
