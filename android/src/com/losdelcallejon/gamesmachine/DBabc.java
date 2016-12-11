package com.losdelcallejon.gamesmachine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ricardo Justiniano on 10-Dec-16.
 */

public class DBabc extends SQLiteOpenHelper {

    private SQLiteDatabase DB;
    private boolean esNuevaBD=false;

    public DBabc(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Sql = "CREATE TABLE usuarios(" +
                "id INTEGER," +
                "sexo VARCHAR(100))";
        DB.execSQL(Sql);
        Sql = "CREATE TABLE unidades(" +
                "id INTEGER," +
                "nombre VARCHAR(100),"+
                "descripcion VARCHAR(100))";
        DB.execSQL(Sql);
        Sql = "CREATE TABLE cursado(" +
                "id INTEGER," +
                "fecha VARCHAR(100),"+
                "unidad_id INTEGER)";
        DB.execSQL(Sql);
        Sql = "CREATE TABLE palabras(" +
                "id INTEGER," +
                "letra VARCHAR(100),"+
                "unidad_id INTEGER)";
        DB.execSQL(Sql);
        this.esNuevaBD=true;
    }

    public boolean esNuevaBD()
    {
        return esNuevaBD;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS usuarios");

        onCreate(DB);
    }

    public void OpenDatabase() {

        DB = this.getWritableDatabase();
    }

    public void CloseDatabase() {
        DB.close();
    }


}
