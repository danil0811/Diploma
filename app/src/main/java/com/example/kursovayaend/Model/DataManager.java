package com.example.kursovayaend.Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;


import static com.example.kursovayaend.Model.DBHelper.*;


public class DataManager {

    private DBHelper dbHelper;
    private SharedPreferences sharedPreferences;

    public DataManager(Context context) {
        dbHelper = new DBHelper(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public long insertUserData(Double weight, Double height, String gender) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_GENDER, gender);

        long newRowId = db.insert(TABLE_USER, null, values);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(COLUMN_WEIGHT, weight != null ? weight.floatValue() : 0);
        editor.putFloat(COLUMN_HEIGHT, height != null ? height.floatValue() : 0);
        editor.putString(COLUMN_GENDER, gender);
        editor.apply();

        db.close();

        return newRowId;
    }

    public UserData getUserData() {
        float weight = sharedPreferences.getFloat(COLUMN_WEIGHT, 0);
        float height = sharedPreferences.getFloat(COLUMN_HEIGHT, 0);
        String gender = sharedPreferences.getString(COLUMN_GENDER, "");

        return new UserData(weight, height, gender);
    }

    public long insertWorkout(long userID, String workoutName, String workoutDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID_FK, userID);
        values.put(COLUMN_WORKOUT_NAME, workoutName);
        values.put(COLUMN_WORKOUT_DATE, workoutDate);

        long newRowId = db.insert(TABLE_WORKOUT, null, values);
        db.close();

        return newRowId;
    }

    public long insertDay(String dayName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_NAME, dayName);

        long newRowId = db.insert(TABLE_DAYS, null, values);
        db.close();

        return newRowId;
    }

    public long linkWorkoutAndDay(long workoutID, long dayID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_ID_FK_WD, workoutID);
        values.put(COLUMN_DAY_ID_FK_WD, dayID);

        long newRowId = db.insert(TABLE_WORKOUT_DAYS, null, values);
        db.close();

        return newRowId;
    }

    public long insertExercise(String exerciseName, String exerciseDescription) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAME, exerciseName);
        values.put(COLUMN_EXERCISE_DESCRIPTION, exerciseDescription);

        long newRowId = db.insert(TABLE_EXERCISES, null, values);
        db.close();

        return newRowId;
    }


    public long linkWorkoutDayAndExercise(long workoutID, long dayID, long exerciseID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_ID_FK_WE, workoutID);
        values.put(COLUMN_DAY_ID_FK_WE, dayID);
        values.put(COLUMN_EXERCISE_ID_FK_WE, exerciseID);

        long newRowId = db.insert(TABLE_WORKOUT_EXERCISES, null, values);
        db.close();

        return newRowId;
    }

    // Метод вставки упражнений по умолчанию, если их нет в базе данных
    private void insertDefaultDaysIfNeeded() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Проверяем, есть ли дни в базе данных
        Cursor cursor = db.query(
                DBHelper.TABLE_DAYS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() == 0) {
            // Если дней нет, добавляем их
            String[] dayNames = {"День 1", "День 2", "День 3", "День 4", "День 5"};

            for (int i = 0; i < dayNames.length; i++) {
                ContentValues values = new ContentValues();
                values.put(DBHelper.COLUMN_DAY_NAME, dayNames[i]);

                db.insert(DBHelper.TABLE_DAYS, null, values);
            }
        }

        cursor.close();
    }

    // Метод для получения дней в виде массива строк
    public String[] getDaysAsArray() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Запрос для получения всех дней
        Cursor cursor = db.query(
                DBHelper.TABLE_DAYS,
                new String[]{DBHelper.COLUMN_DAY_NAME},
                null,
                null,
                null,
                null,
                null
        );

        // Создаем массив строк для хранения дней
        String[] daysArray = new String[cursor.getCount()];

        // Перемещаемся в начало курсора
        if (cursor.moveToFirst()) {
            int columnIndexDayName = cursor.getColumnIndex(DBHelper.COLUMN_DAY_NAME);

            // Проверяем, существует ли столбец
            if (columnIndexDayName != -1) {
                // Заполняем массив значениями из курсора
                for (int i = 0; i < cursor.getCount(); i++) {
                    daysArray[i] = cursor.getString(columnIndexDayName);
                    cursor.moveToNext();
                }
            }
        }

        // Закрываем курсор и базу данных
        cursor.close();
        db.close();

        return daysArray;
    }

    // Метод вставки данных по умолчанию
    public void insertDefaultDataIfNeeded() {

        Log.d("DataManager", "Inserting default data");

        // Вызываем метод вставки дней
        insertDefaultDaysIfNeeded();

        // Вызываем метод вставки упражнений
        insertDefaultExercisesIfNeeded();

        // Вызываем метод вставки тренировок
        insertDefaultWorkoutsIfNeeded();
    }

    // Метод вставки упражнений по умолчанию, если их нет в базе данных
    private void insertDefaultExercisesIfNeeded() {
        Log.d("DataManager", "Inserting default exercises");

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Проверяем, есть ли упражнения в базе данных
        Cursor cursor = db.query(
                DBHelper.TABLE_EXERCISES,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() == 0) {
            // Если упражнений нет, добавляем их
            String[] exerciseNames = {"Альпинист", "Боковые наклоны к стопам", "Планка", "Поднятие ног лежа на спине",
                    "Пресс", "Повороты туловища сидя", "Скручивание в пояснице лежа на полу (влево)",
                    "Скручивание в пояснице лежа на полу (вправо)", "Отжимание с опорой впереди",
                    "Отжимания с упором на колени", "Отжимания от пола", "Отжимания с широким упором"};

            String[] exerciseDescriptions = {
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ...",
                    "Описание упражнения: ..."
            };

            for (int i = 0; i < exerciseNames.length; i++) {
                ContentValues values = new ContentValues();
                values.put(DBHelper.COLUMN_EXERCISE_NAME, exerciseNames[i]);
                values.put(DBHelper.COLUMN_EXERCISE_DESCRIPTION, exerciseDescriptions[i]);

                long exerciseId = db.insert(DBHelper.TABLE_EXERCISES, null, values);

                // Вставляем связи между упражнениями и тренировками
                for (int j = 1; j <= 5; j++) { // Предположим, что у вас есть 5 тренировок
                    long workoutId = j;
                    long dayId = j;

                    // Вызываем метод вставки данных в таблицу workout_exercises
                    long linkRowId = linkWorkoutDayAndExercise(workoutId, dayId, exerciseId);

                    // Проверяем результат вставки
                    if (linkRowId != -1) {
                        // Успешно вставлено
                        Log.d("DataManager", "Link inserted successfully for exerciseId " + exerciseId);
                    } else {
                        // Произошла ошибка вставки
                        Log.e("DataManager", "Error inserting link for exerciseId " + exerciseId);
                    }
                }
            }
        }

        cursor.close();
    }

    // Метод вставки тренировок по умолчанию, если их нет в базе данных
    private void insertDefaultWorkoutsIfNeeded() {
        Log.d("DataManager", "Inserting default workouts");

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Проверяем, есть ли тренировки в базе данных
        Cursor cursor = db.query(
                DBHelper.TABLE_WORKOUT,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() == 0) {
            // Если тренировок нет, добавляем их
            String[] workoutNames = {"7x4 всего тела", "7x4 нижней части тела", "Тренировка пресса (новичок)",
                    "Тренировка груди (новичок)", "Тренировка рук (новичок)"};

            String[] workoutDates = {"Дата1", "Дата2", "Дата3", "Дата4", "Дата5"};

            for (int i = 0; i < workoutNames.length; i++) {
                ContentValues values = new ContentValues();
                values.put(DBHelper.COLUMN_WORKOUT_NAME, workoutNames[i]);
                values.put(DBHelper.COLUMN_WORKOUT_DATE, workoutDates[i]);

                long workoutId = db.insert(DBHelper.TABLE_WORKOUT, null, values);

                // Вставляем связи между тренировками и упражнениями
                for (int j = 1; j <= 12; j++) { // Предположим, что у вас есть 12 упражнений
                    long exerciseId = j;
                    long dayId = j;

                    // Вызываем метод вставки данных в таблицу workout_exercises
                    long linkRowId = linkWorkoutDayAndExercise(workoutId, dayId, exerciseId);

                    // Проверяем результат вставки
                    if (linkRowId != -1) {
                        // Успешно вставлено
                        Log.d("DataManager", "Link inserted successfully for workoutId " + workoutId);
                    } else {
                        // Произошла ошибка вставки
                        Log.e("DataManager", "Error inserting link for workoutId " + workoutId);
                    }
                }
            }
        }

        cursor.close();
        db.close();
    }



    public void insertInitialUserData(double weight, double height, String gender) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_GENDER, gender);

        db.insert(TABLE_USER, null, values);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(COLUMN_WEIGHT, (float) weight);
        editor.putFloat(COLUMN_HEIGHT, (float) height);
        editor.putString(COLUMN_GENDER, gender);
        editor.apply();

        db.close();
    }

    public boolean areInitialUserDataInserted() {
        return sharedPreferences.getBoolean("initialUserDataInserted", false);
    }

    public void setInitialUserDataInserted() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("initialUserDataInserted", true);
        editor.apply();
    }

    public Cursor getExercisesForWorkout(long workoutID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                COLUMN_EXERCISE_NAME,
                COLUMN_EXERCISE_DESCRIPTION
        };

        String tables = TABLE_WORKOUT_EXERCISES + " JOIN " + TABLE_EXERCISES +
                " ON " + TABLE_WORKOUT_EXERCISES + "." + COLUMN_EXERCISE_ID_FK_WE +
                " = " + TABLE_EXERCISES + "." + COLUMN_EXERCISE_ID;

        String selection = COLUMN_WORKOUT_ID_FK_WE + " = ?";
        String[] selectionArgs = {String.valueOf(workoutID)};

        return db.query(
                tables,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }
}
