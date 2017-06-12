package com.sarah.sarah;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

class SQLiteHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ACTION = "action";
    public static final String COLUMN_STATE = "state";
    public static final String TABLE_NAME = "STATES";

    static final String DATABASE_NAME = "StatesDB";
    static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_CIUDADES =
                        "CREATE TABLE "+ TABLE_NAME+ "( "+
                                COLUMN_ACTION + " TEXT NOT NULL," +
                                COLUMN_STATE + " INTEGER NOT NULL);";

    private static final String INSERT_LUZ = "INSERT INTO " + TABLE_NAME + " VALUES ('luz', 0);";
    private static final String INSERT_VENTILADOR = "INSERT INTO " + TABLE_NAME + " VALUES ('ventilador', 0);";
    private static final String INSERT_TV = "INSERT INTO " + TABLE_NAME + " VALUES ('tv', 0);";
    private static final String INSERT_CERROJO = "INSERT INTO " + TABLE_NAME + " VALUES ('cerrojo', 0);";

    public SQLiteHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CIUDADES);
        db.execSQL(INSERT_LUZ);
        db.execSQL(INSERT_VENTILADOR);
        db.execSQL(INSERT_TV);
        db.execSQL(INSERT_CERROJO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
