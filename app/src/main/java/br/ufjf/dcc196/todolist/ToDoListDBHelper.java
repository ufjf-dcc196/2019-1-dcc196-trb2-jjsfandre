package br.ufjf.dcc196.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoListDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="ToDoList";

    public ToDoListDBHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ToDoListContract.Tarefa.CREATE_TABLE);
        db.execSQL(ToDoListContract.Status.CREATE_TABLE);
        db.execSQL(ToDoListContract.Tag.CREATE_TABLE);
        db.execSQL(ToDoListContract.TarefaTag.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ToDoListContract.Tarefa.DROP_TABLE);
        db.execSQL(ToDoListContract.Status.DROP_TABLE);
        db.execSQL(ToDoListContract.Tag.DROP_TABLE);
        db.execSQL(ToDoListContract.TarefaTag.DROP_TABLE);
        onCreate(db);
    }
}
