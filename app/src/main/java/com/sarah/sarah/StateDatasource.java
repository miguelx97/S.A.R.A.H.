package com.sarah.sarah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Andrea on 23/04/2017.
 */

public class StateDatasource {
    private Context mContext;
    private SQLiteHelper mSQLiteHelper;

    public StateDatasource (Context context){
        mContext = context;
        mSQLiteHelper = new SQLiteHelper(mContext);
    }

    public SQLiteDatabase openReadable() {
        return mSQLiteHelper.getReadableDatabase();
    }

    public SQLiteDatabase openWriteable() {
        return mSQLiteHelper.getWritableDatabase();
    }

    public void close(SQLiteDatabase database) {
        database.close();
    }

    public int readState(String action) {
        SQLiteDatabase database = openReadable();
        State state = new State();
        String s= "SELECT " + SQLiteHelper.COLUMN_STATE +
                " FROM "+ SQLiteHelper.TABLE_NAME +
                " WHERE " + SQLiteHelper.COLUMN_ACTION + " = '" + action + "';";

        Cursor cursor = database.rawQuery(s,  null);
        int stateNow = -1;
        if (cursor.moveToFirst()) {
            stateNow =  getIntFromColumnName(cursor,SQLiteHelper.COLUMN_STATE);

        }
        cursor.close();
        database.close();
        return stateNow;
    }

    public void actualizarSQL(State state) {
        SQLiteDatabase database = openWriteable();
        database.beginTransaction();
        database.execSQL("UPDATE " + SQLiteHelper.TABLE_NAME +
                " SET " + SQLiteHelper.COLUMN_ACTION + " = " + state.getAction()+
                "', " + SQLiteHelper.COLUMN_STATE + "='"+ state.getState() + "' ");
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

    }

    private int getIntFromColumnName(Cursor cursor, String
            columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }
    private String getStringFromColumnName(Cursor cursor, String
            columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }
}
