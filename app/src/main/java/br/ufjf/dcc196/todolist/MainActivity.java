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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToDoListDBHelper helper = new ToDoListDBHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ToDoListContract.Tarefa.COLLUMN_TITULO,"Fazer compras");
        values.put(ToDoListContract.Tarefa.COLLUMN_DESCRICAO,"Ir ao mercado fazer compras");
        values.put(ToDoListContract.Tarefa.COLLUMN_DIFICULDADE,1);
        values.put(ToDoListContract.Tarefa.COLLUMN_DTHORALIMITE,"30/05/2019 12:00");
        String dtHrNow = new Date().toString();
        values.put(ToDoListContract.Tarefa.COLLUMN_DTHORAATUALIZACAO,dtHrNow);
        values.put(ToDoListContract.Tarefa.COLLUMN_STATUSID,2);
        long novoId = db.insert(ToDoListContract.Tarefa.TABLE_NAME,null,values);
        Toast.makeText(this, "Novo registro criado com id:"+novoId, Toast.LENGTH_LONG).show();


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
        TextView txtTarefas = findViewById(R.id.txtTarefas);
        txtTarefas.setText(tarefas.toString());
    }
}
