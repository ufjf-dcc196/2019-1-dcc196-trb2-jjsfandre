package br.ufjf.dcc196.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufjf.dcc196.todolist.Model.Status;
import br.ufjf.dcc196.todolist.Model.Tag;
import br.ufjf.dcc196.todolist.Model.Tarefa;

public class ToDoListDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=8;
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
        String dtHrNow = Helper.getDataHoraAtualFormatada();
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


    public void deleteTagById(String id, String titulo){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = ToDoListContract.Tag._ID+" = ?";

        String[] selectArgs = {id};
        db.delete(ToDoListContract.Tag.TABLE_NAME,select,selectArgs);
        Log.i("DBINFO", "DEL titulo: " + titulo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Long> getCursorTagsByTarefa(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selecaoTarefaTag = ToDoListContract.TarefaTag.COLLUMN_TAREFA + "= ?";
        String[] argsTarefaTag = {id};

        Cursor c = db.query(ToDoListContract.TarefaTag.TABLE_NAME,camposTarefaTag,selecaoTarefaTag,argsTarefaTag,null,null,null);
        int idxTag = c.getColumnIndex(ToDoListContract.TarefaTag.COLLUMN_TAG);
        c.move(-1);

        List<Long> tagsIds = new ArrayList<>();

        while(c.moveToNext()){
            Long idTag = c.getLong(idxTag);
            tagsIds.add(idTag);
        }
        return tagsIds;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Tag> getListTagsByTarefa(String id){
        List<Long> listTagsIds = getCursorTagsByTarefa(id);

        List<Tag> tags = new ArrayList<>();
        for (Long idTag :
                listTagsIds) {
            Tag tag = getTagById(idTag);
            if (tag != null)
                tags.add(tag);
        }

        return tags;
    }

    public List<Long> getCursorTarefasByTag(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selecaoTarefaTag = ToDoListContract.TarefaTag.COLLUMN_TAG + "= ?";
        String[] argsTarefaTag = {id};

        Cursor c = db.query(ToDoListContract.TarefaTag.TABLE_NAME,camposTarefaTag,selecaoTarefaTag,argsTarefaTag,null,null,null);
        int idxTarefa = c.getColumnIndex(ToDoListContract.TarefaTag.COLLUMN_TAREFA);
        c.move(-1);

        List<Long> listIdsTarefa = new ArrayList<>();
        while(c.moveToNext()){
            Long idTarefa = c.getLong(idxTarefa);
            listIdsTarefa.add(idTarefa);
        }
        /*String selecaoTarefa = ToDoListContract.Tarefa._ID + "= ?";
        String sort = ToDoListContract.Tarefa.COLLUMN_STATUSID + " ASC";
        Cursor result = db.query(ToDoListContract.Tarefa.TABLE_NAME,camposTarefa,selecaoTarefa,argsTarefa,null,null,sort);*/
        return listIdsTarefa;
    }

    public List<Tarefa> getListTarefasByTag(String id){
        
        List<Tarefa> result = new ArrayList<>();
        List<Long> listIdsTarefas = getCursorTarefasByTag(id);

        for (Long idTarefa :
                listIdsTarefas) {
            result.add(getTarefaById(idTarefa+""));
        }
        return result;
    }

    public Status getStatusById(Long id){
        return getStatusById(id+"");
    }

    public Status getStatusById(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = ToDoListContract.Status._ID+ "= ?";
        String[] args = {id};

        Cursor c = db.query(ToDoListContract.Status.TABLE_NAME,camposStatus,selecao,args,null,null,null);
        c.moveToFirst();
        int idxId = c.getColumnIndex(ToDoListContract.Status._ID);
        int idxNome = c.getColumnIndex(ToDoListContract.Status.COLLUMN_NOME);

        Status status = new Status();
        status.setId(c.getLong(idxId));
        status.setNome(c.getString(idxNome));
        return status;
    }

    public List<Status> getAllStatusList(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.query(ToDoListContract.Status.TABLE_NAME,camposStatus,null,null,null,null,null);
        int idxId = c.getColumnIndex(ToDoListContract.Status._ID);
        int idxNome = c.getColumnIndex(ToDoListContract.Status.COLLUMN_NOME);
        c.move(-1);

        List<Status> statusList = new ArrayList<>();
        while(c.moveToNext()){
            Long id = c.getLong(idxId);
            String nome = c.getString(idxNome);

            System.out.println(String.format("%x %s \n",id,nome));
            Status s = new Status(id,nome);
            statusList.add(s);

        }

        return statusList;
    }

    public Tarefa getTarefaById(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = ToDoListContract.Tarefa._ID+ "= ?";
        String[] args = {id};

        Cursor c = db.query(ToDoListContract.Tarefa.TABLE_NAME,camposTarefa,selecao,args,null,null,null);
        c.moveToFirst();
        int idxId = c.getColumnIndex(ToDoListContract.Tarefa._ID);
        int idxTitulo = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_TITULO);
        int idxDescricao = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_DESCRICAO);
        int idxDificuldade = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_DIFICULDADE);
        int idxDtHrLimite = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_DTHORALIMITE);
        int idxDtHrAtualizacao = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_DTHORAATUALIZACAO);
        int idxStatusId = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_STATUSID);
        Long idTarefa = c.getLong(idxId);
        String titulo = c.getString(idxTitulo);
        String descricao = c.getString(idxDescricao);
        Integer dificuldade = c.getInt(idxDificuldade);
        String dtHrLimite = c.getString(idxDtHrLimite);
        String dtHrAtualizacao = c.getString(idxDtHrAtualizacao);
        Long statusId = c.getLong(idxStatusId);


        return new Tarefa(idTarefa,titulo,descricao,dificuldade,statusId,dtHrLimite,dtHrAtualizacao);
    }

    public Tag getTagById(Long id) {
        return getTagById(id+"");
    }

    public Tag getTagById(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        String selecao = ToDoListContract.Tag._ID+ "= ?";
        String[] args = {id};
        Cursor c = db.query(ToDoListContract.Tag.TABLE_NAME,camposTag,selecao,args,null,null,null);
        int idxId = c.getColumnIndex(ToDoListContract.Tag._ID);
        int idxNome = c.getColumnIndex(ToDoListContract.Tag.COLLUMN_NOME);
        c.moveToFirst();
        if (c.getCount()>0){

            Long idTag = c.getLong(idxId);
            String nome = c.getString(idxNome);

            return new Tag(idTag,nome);
        }
        return null;

    }

    public void atualizarTarefa(Tarefa t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueTarefa(t);

        String selecao = ToDoListContract.Tarefa._ID+ "= ?";
        String[] args = {t.getId()+""};

        db.update(ToDoListContract.Tarefa.TABLE_NAME,values,selecao, args);
    }

    public void atualizarTag(Tag t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueTag(t);

        String selecao = ToDoListContract.Tag._ID+ "= ?";
        String[] args = {t.getId()+""};

        db.update(ToDoListContract.Tag.TABLE_NAME,values,selecao, args);
    }

    public void inserirTarefa(Tarefa t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueTarefa(t);

        db.insert(ToDoListContract.Tarefa.TABLE_NAME,null,values);
    }

    public void inserirTag(Tag t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = populateContentValueTag(t);

        db.insert(ToDoListContract.Tag.TABLE_NAME,null,values);
    }

    private ContentValues populateContentValueTag(Tag t){

        ContentValues values = new ContentValues();
        values.put(ToDoListContract.Tag.COLLUMN_NOME,t.getNome());

        return values;

    }
    private ContentValues populateContentValueTarefa(Tarefa t){

        ContentValues values = new ContentValues();
        values.put(ToDoListContract.Tarefa.COLLUMN_TITULO,t.getTitulo());
        values.put(ToDoListContract.Tarefa.COLLUMN_DESCRICAO,t.getDescricao());
        values.put(ToDoListContract.Tarefa.COLLUMN_DIFICULDADE,t.getDificuldade());
        values.put(ToDoListContract.Tarefa.COLLUMN_DTHORALIMITE,t.getDataHoraLimite());
        values.put(ToDoListContract.Tarefa.COLLUMN_DTHORAATUALIZACAO,Helper.getDataHoraAtualFormatada());
        values.put(ToDoListContract.Tarefa.COLLUMN_STATUSID,t.getStatusId());

        return values;

    }

    public Cursor getCursorTodasAsTags(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sort = ToDoListContract.Tag.COLLUMN_NOME + " ASC";
        Cursor c = db.query(ToDoListContract.Tag.TABLE_NAME, camposTag, null, null, null, null, sort);
        return c;
    }

    public void associarTags(String idTarefa, List<Long> tagsIds){
        SQLiteDatabase db = this.getWritableDatabase();
        cleanTags(idTarefa);
        ContentValues values;
        for (Long tagid :
                tagsIds) {
            values = new ContentValues();
            values.put(ToDoListContract.TarefaTag.COLLUMN_TAG,tagid);
            values.put(ToDoListContract.TarefaTag.COLLUMN_TAREFA,idTarefa);
            db.insert(ToDoListContract.TarefaTag.TABLE_NAME,null,values);
        }
    }

    public void cleanTags(String idTarefa){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = ToDoListContract.TarefaTag.COLLUMN_TAREFA+" = ?";

        String[] selectArgs = {idTarefa};
        db.delete(ToDoListContract.TarefaTag.TABLE_NAME,select,selectArgs);
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
    private final String[] camposTarefaTag = {
            ToDoListContract.TarefaTag._ID,
            ToDoListContract.TarefaTag.COLLUMN_TAG,
            ToDoListContract.TarefaTag.COLLUMN_TAREFA
    };
    private final String[] camposTag = {
            ToDoListContract.Tag._ID,
            ToDoListContract.Tag.COLLUMN_NOME
    };
    private final String[] camposStatus = {
            ToDoListContract.Status._ID,
            ToDoListContract.Status.COLLUMN_NOME
    };

}
