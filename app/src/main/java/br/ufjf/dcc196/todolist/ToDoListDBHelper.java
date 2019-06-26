package br.ufjf.dcc196.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class ToDoListDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=4;
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
        addBaseData(db);
    }

    private void addBaseData(SQLiteDatabase db){
        long idStatusAFazer = addStatusData(db);
        addTarefaData(db,idStatusAFazer);
        addTagsData(db);

    }

    private long addStatusData(SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put(ToDoListContract.Status.COLLUMN_NOME,"A fazer");
        long newid = db.insert(ToDoListContract.Status.TABLE_NAME,null,values);
        values.put(ToDoListContract.Status.COLLUMN_NOME,"Bloqueada");
        db.insert(ToDoListContract.Status.TABLE_NAME,null,values);
        values.put(ToDoListContract.Status.COLLUMN_NOME,"Concluída");
        db.insert(ToDoListContract.Status.TABLE_NAME,null,values);
        values.put(ToDoListContract.Status.COLLUMN_NOME,"Em execução");
        db.insert(ToDoListContract.Status.TABLE_NAME,null,values);

        return newid;
    }

    private void addTarefaData(SQLiteDatabase db, long idStatusAFazer){
        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();
        values.put(ToDoListContract.Tarefa.COLLUMN_TITULO,"Fazer compras");
        values.put(ToDoListContract.Tarefa.COLLUMN_DESCRICAO,"Ir ao mercado fazer compras");
        values.put(ToDoListContract.Tarefa.COLLUMN_DIFICULDADE,1);
        values.put(ToDoListContract.Tarefa.COLLUMN_DTHORALIMITE,"30/07/2019 12:00");
        String dtHrNow = new Date().toString();
        values.put(ToDoListContract.Tarefa.COLLUMN_DTHORAATUALIZACAO,dtHrNow);
        values.put(ToDoListContract.Tarefa.COLLUMN_STATUSID,idStatusAFazer);
        values2.put(ToDoListContract.Tarefa.COLLUMN_TITULO,"Estudar");
        values2.put(ToDoListContract.Tarefa.COLLUMN_DESCRICAO,"Estudar para as provas");
        values2.put(ToDoListContract.Tarefa.COLLUMN_DIFICULDADE,5);
        values2.put(ToDoListContract.Tarefa.COLLUMN_DTHORALIMITE,"30/06/2019 12:00");
        values2.put(ToDoListContract.Tarefa.COLLUMN_DTHORAATUALIZACAO,dtHrNow);
        values2.put(ToDoListContract.Tarefa.COLLUMN_STATUSID,idStatusAFazer);

        db.insert(ToDoListContract.Tarefa.TABLE_NAME,null,values);
        db.insert(ToDoListContract.Tarefa.TABLE_NAME,null,values2);
    }

    private void addTagsData(SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put(ToDoListContract.Tag.COLLUMN_NOME,"Estudo");
        db.insert(ToDoListContract.Tag.TABLE_NAME,null,values);
        values.put(ToDoListContract.Tag.COLLUMN_NOME,"Trabalho");
        db.insert(ToDoListContract.Tag.TABLE_NAME,null,values);
        values.put(ToDoListContract.Tag.COLLUMN_NOME,"Dia-a-dia");
        db.insert(ToDoListContract.Tag.TABLE_NAME,null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ToDoListContract.Tarefa.DROP_TABLE);
        db.execSQL(ToDoListContract.Status.DROP_TABLE);
        db.execSQL(ToDoListContract.Tag.DROP_TABLE);
        db.execSQL(ToDoListContract.TarefaTag.DROP_TABLE);
        onCreate(db);
    }

    private final String[] camposTarefa = {
            ToDoListContract.Tarefa._ID,
            ToDoListContract.Tarefa.COLLUMN_TITULO,
            ToDoListContract.Tarefa.COLLUMN_DESCRICAO,
            ToDoListContract.Tarefa.COLLUMN_DIFICULDADE,
            ToDoListContract.Tarefa.COLLUMN_DTHORALIMITE,
            ToDoListContract.Tarefa.COLLUMN_DTHORAATUALIZACAO,
            ToDoListContract.Tarefa.COLLUMN_STATUSID
    };


    public Cursor getCursorTarefaById(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = ToDoListContract.Tarefa._ID + " = ?";
        String[] args = {id};

        Cursor c = db.query(ToDoListContract.Tarefa.TABLE_NAME, camposTarefa, selecao, args, null, null, null);
        return c;
    }


    public Cursor getCursorTodasAsTarefas(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sort = ToDoListContract.Tarefa.COLLUMN_STATUSID + " ASC";
        Cursor c = db.query(ToDoListContract.Tarefa.TABLE_NAME, camposTarefa, null, null, null, null, sort);
        return c;
    }

    public void deleteTarefaById(String id, String titulo){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = ToDoListContract.Tarefa._ID+" = ?";

        String[] selectArgs = {id};
        db.delete(ToDoListContract.Tarefa.TABLE_NAME,select,selectArgs);
        Log.i("DBINFO", "DEL titulo: " + titulo);
    }

}
