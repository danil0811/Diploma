package com.example.kursovayaend.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FitnessApp.db";
    private static final int DATABASE_VERSION = 1;

    // Таблица User
    public static final String TABLE_USER = "user_data";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_GENDER = "gender";

    // Таблица Exercises
    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_EXERCISE_ID = "_id";
    public static final String COLUMN_EXERCISE_NAME = "exercise_name";
    public static final String COLUMN_EXERCISE_DESCRIPTION = "exercise_description";

    // Таблица Workout
    public static final String TABLE_WORKOUT = "workout";
    public static final String COLUMN_WORKOUT_ID = "_id";
    public static final String COLUMN_USER_ID_FK = "user_id";
    public static final String COLUMN_WORKOUT_NAME = "workout_name";
    public static final String COLUMN_WORKOUT_DATE = "workout_date";

    // Таблица Days
    public static final String TABLE_DAYS = "days";
    public static final String COLUMN_DAY_ID = "_id";
    public static final String COLUMN_DAY_NAME = "day_name";

    // Таблица WorkoutDays
    public static final String TABLE_WORKOUT_DAYS = "workout_days";
    public static final String COLUMN_WORKOUT_ID_FK_WD = "workout_id";
    public static final String COLUMN_DAY_ID_FK_WD = "day_id";

    // Таблица WorkoutExercises
    public static final String TABLE_WORKOUT_EXERCISES = "workout_exercises";
    public static final String COLUMN_WORKOUT_EXERCISE_ID = "_id";
    public static final String COLUMN_USER_ID_FK_WE = "user_id";
    public static final String COLUMN_EXERCISE_ID_FK_WE = "exercise_id";
    public static final String COLUMN_WORKOUT_ID_FK_WE = "workout_id";
    public static final String COLUMN_DAY_ID_FK_WE = "day_id";

    // SQL-запросы для создания таблиц
    private static final String TABLE_USER_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_WEIGHT + " REAL, " +
                    COLUMN_HEIGHT + " REAL, " +
                    COLUMN_GENDER + " TEXT);";

    private static final String TABLE_EXERCISES_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_EXERCISES + " (" +
                    COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EXERCISE_NAME + " TEXT, " +
                    COLUMN_EXERCISE_DESCRIPTION + " TEXT);";

    private static final String TABLE_WORKOUT_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_WORKOUT + " (" +
                    COLUMN_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID_FK + " INTEGER, " +
                    COLUMN_WORKOUT_NAME + " TEXT, " +
                    COLUMN_WORKOUT_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID_FK + ") REFERENCES " +
                    TABLE_USER + "(" + COLUMN_USER_ID + "));";

    private static final String TABLE_DAYS_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_DAYS + " (" +
                    COLUMN_DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DAY_NAME + " TEXT);";

    private static final String TABLE_WORKOUT_DAYS_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_WORKOUT_DAYS + " (" +
                    COLUMN_WORKOUT_ID_FK_WD + " INTEGER, " +
                    COLUMN_DAY_ID_FK_WD + " INTEGER, " +
                    "PRIMARY KEY (" + COLUMN_WORKOUT_ID_FK_WD + ", " + COLUMN_DAY_ID_FK_WD + "), " +
                    "FOREIGN KEY (" + COLUMN_WORKOUT_ID_FK_WD + ") REFERENCES " +
                    TABLE_WORKOUT + "(" + COLUMN_WORKOUT_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_DAY_ID_FK_WD + ") REFERENCES " +
                    TABLE_DAYS + "(" + COLUMN_DAY_ID + "));";

    private static final String TABLE_WORKOUT_EXERCISES_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_WORKOUT_EXERCISES + " (" +
                    COLUMN_WORKOUT_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID_FK_WE + " INTEGER, " +
                    COLUMN_EXERCISE_ID_FK_WE + " INTEGER, " +
                    COLUMN_WORKOUT_ID_FK_WE + " INTEGER, " +
                    COLUMN_DAY_ID_FK_WE + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID_FK_WE + ") REFERENCES " +
                    TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_EXERCISE_ID_FK_WE + ") REFERENCES " +
                    TABLE_EXERCISES + "(" + COLUMN_EXERCISE_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_WORKOUT_ID_FK_WE + ") REFERENCES " +
                    TABLE_WORKOUT + "(" + COLUMN_WORKOUT_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_DAY_ID_FK_WE + ") REFERENCES " +
                    TABLE_DAYS + "(" + COLUMN_DAY_ID + "));";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_EXERCISES_CREATE);
        db.execSQL(TABLE_WORKOUT_CREATE);
        db.execSQL(TABLE_DAYS_CREATE);
        db.execSQL(TABLE_WORKOUT_DAYS_CREATE);
        db.execSQL(TABLE_WORKOUT_EXERCISES_CREATE);
        Log.d("DBHelper", "Database created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Вы можете обновить существующую базу данных, если необходимо
        // Например: db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // onCreate(db);
    }
}

