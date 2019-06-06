package br.ufjf.dcc196.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufjf.dcc196.todolist.Model.*;

public class MainActivity extends AppCompatActivity {

    private void listAllData(){
        ToDoListDBHelper helper = new ToDoListDBHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        listTarefa(db);
        listTag(db);
        listStatus(db);
    }
    private void listTarefa(SQLiteDatabase db){
        String[] campos ={
                ToDoListContract.Tarefa._ID,
                ToDoListContract.Tarefa.COLLUMN_TITULO,
                ToDoListContract.Tarefa.COLLUMN_DESCRICAO,
                ToDoListContract.Tarefa.COLLUMN_DIFICULDADE,
                ToDoListContract.Tarefa.COLLUMN_DTHORALIMITE,
                ToDoListContract.Tarefa.COLLUMN_DTHORAATUALIZACAO,
                ToDoListContract.Tarefa.COLLUMN_STATUSID
        };
        Cursor c = db.query(ToDoListContract.Tarefa.TABLE_NAME,campos,null,null,null,null,null);
        int idxId = c.getColumnIndex(ToDoListContract.Tarefa._ID);
        int idxTitulo = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_TITULO);
        int idxDescricao = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_DESCRICAO);
        int idxDificuldade = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_DIFICULDADE);
        int idxDtHrLimite = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_DTHORALIMITE);
        int idxDtHrAtualizacao = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_DTHORAATUALIZACAO);
        int idxStatusId = c.getColumnIndex(ToDoListContract.Tarefa.COLLUMN_STATUSID);
        c.move(-1);

        List<Tarefa> tarefas = new ArrayList<>();
        while(c.moveToNext()){
            Long id = c.getLong(idxId);
            String titulo = c.getString(idxTitulo);
            String descricao = c.getString(idxDescricao);
            Integer dificuldade = c.getInt(idxDificuldade);
            String dtHrLimite = c.getString(idxDtHrLimite);
            String dtHrAtualizacao = c.getString(idxDtHrAtualizacao);
            Long statusId = c.getLong(idxStatusId);


            System.out.println(String.format("%x %s %s %d %s %s %x\n",id,titulo,descricao,dificuldade,dtHrLimite,
                    dtHrAtualizacao,statusId));
            Tarefa t = new Tarefa(id,titulo,descricao,dificuldade,statusId,dtHrLimite,dtHrAtualizacao);
            tarefas.add(t);

        }
        TextView txtOutput = findViewById(R.id.txtTarefas);
        txtOutput.setText(tarefas.toString());
    }

    private void listTag(SQLiteDatabase db){
        String[] campos ={
                ToDoListContract.Tag._ID,
                ToDoListContract.Tag.COLLUMN_NOME
        };
        Cursor c = db.query(ToDoListContract.Tag.TABLE_NAME,campos,null,null,null,null,null);
        int idxId = c.getColumnIndex(ToDoListContract.Tag._ID);
        int idxNome = c.getColumnIndex(ToDoListContract.Tag.COLLUMN_NOME);
        c.move(-1);

        List<Tag> tags = new ArrayList<>();
        while(c.moveToNext()){
            Long id = c.getLong(idxId);
            String nome = c.getString(idxNome);

            System.out.println(String.format("%x %s \n",id,nome));
            Tag t = new Tag(id,nome);
            tags.add(t);

        }
        TextView txtOutput = findViewById(R.id.txtTags);
        txtOutput.setText(tags.toString());
    }

    private void listStatus(SQLiteDatabase db){
        String[] campos ={
                ToDoListContract.Status._ID,
                ToDoListContract.Status.COLLUMN_NOME
        };
        Cursor c = db.query(ToDoListContract.Status.TABLE_NAME,campos,null,null,null,null,null);
        int idxId = c.getColumnIndex(ToDoListContract.Status._ID);
        int idxNome = c.getColumnIndex(ToDoListContract.Status.COLLUMN_NOME);
        c.move(-1);

        List<Status> tags = new ArrayList<>();
        while(c.moveToNext()){
            Long id = c.getLong(idxId);
            String nome = c.getString(idxNome);

            System.out.println(String.format("%x %s \n",id,nome));
            Status s = new Status(id,nome);
            tags.add(s);

        }
        TextView txtOutput = findViewById(R.id.txtStatus);
        txtOutput.setText(tags.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listAllData();

    }
}
