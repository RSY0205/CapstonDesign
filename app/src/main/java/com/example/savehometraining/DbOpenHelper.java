package com.example.savehometraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.savehometraining.ui.Routine.Routine_ConnectCustomRoutine;
import com.example.savehometraining.ui.Routine.MyadapterRoutine;

import java.util.ArrayList;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context) {
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

    public long insertColumn(String excercise, String count_min, String set_sec, String routine_name) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.EXERCISE, excercise);
        values.put(DataBases.CreateDB.COUNT_MIN, count_min);
        values.put(DataBases.CreateDB.SET_SEC, set_sec);
        values.put(DataBases.CreateDB.ROUTINE_NAME, routine_name);
        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }

    public Cursor selectColumns() {
        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    public boolean updateColumn(long id, String excercise, String count_min, String set_sec, String routine_name) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.EXERCISE, excercise);
        values.put(DataBases.CreateDB.COUNT_MIN, count_min);
        values.put(DataBases.CreateDB.SET_SEC, set_sec);
        values.put(DataBases.CreateDB.ROUTINE_NAME, routine_name);
        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;
    }

    public Cursor sortColumn(){
        Cursor c = mDB.rawQuery( "SELECT DISTINCT routine_name FROM usertable;", null);
        return c;
    }

    public String[] loadRoutineTitle(){
        Cursor cursor = mDB.rawQuery( "SELECT DISTINCT routine_name FROM usertable;", null);

        ArrayList<String> ArrayListRoutine=new ArrayList<>();

        if(cursor!=null && cursor.moveToFirst()){
            do{
                ArrayListRoutine.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        int RoutinelistSize=ArrayListRoutine.size();

        String[] RoutineNameList=new String[RoutinelistSize];
        for(int i=0;i<RoutinelistSize;i++){
            RoutineNameList[i]=ArrayListRoutine.get(i);
        }
        return RoutineNameList;
    }

    public static void loadRoutine(String RoutineName){
        Cursor cursor = mDB.rawQuery( "select * from usertable where routine_name=\""+RoutineName+"\"", null);
        MyadapterRoutine.mItems.clear();
        if(cursor!=null && cursor.moveToFirst()){
            do{
                Routine_ConnectCustomRoutine.mMyAdapter_CustomRoutine.addItem(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            }while(cursor.moveToNext());
        }
        Routine_ConnectCustomRoutine.mMyAdapter_CustomRoutine.notifyDataSetChanged();
    }
    public boolean deleteColumn(String RoutineName){
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "routine_name=\""+RoutineName+"\"", null) > 0;
    }
    public boolean checkRoutine(String RoutineName){
        String Query="select * from usertable where routine_name=\""+RoutineName+"\"";
        Cursor cursor = mDB.rawQuery( Query, null);
        if(cursor.getCount()==0){
            Log.e("중복검사", "checkRoutine:true");
            return true;//중복 없음
        }
        else{
            Log.e("중복검사", "checkRoutine:false");
            return false;//중복 존재
        }
    }
}
