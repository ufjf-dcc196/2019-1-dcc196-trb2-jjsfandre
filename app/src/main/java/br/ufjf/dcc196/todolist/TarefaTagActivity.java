package br.ufjf.dcc196.todolist;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import br.ufjf.dcc196.todolist.Model.Tag;

public class TarefaTagActivity extends AppCompatActivity {
    public TarefaTagAdapter tAdapter;
    String idTarefa;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa_tag);

        final RecyclerView rv = findViewById(R.id.rvTagsTarefa);
        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

        idTarefa = getIntent().getLongExtra("idTarefa",0) + "";
        List<Tag> listaTags = dbHelper.getListTagsByTarefa(idTarefa);
        tAdapter = new TarefaTagAdapter(dbHelper.getCursorTodasAsTags(),listaTags, getApplicationContext());
        //tAdapter.setOnItemClickListener(listenerDefault);
        rv.setAdapter(tAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Button btnAssociarTarefaTag = findViewById(R.id.btnAssociarTarefaTag);
        btnAssociarTarefaTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

                tarefa.setTitulo(titulo.getText().toString());
                tarefa.setDescricao(descricao.getText().toString());
                tarefa.setDataHoraLimite(dtHrLimite.getText().toString());

                tarefa.setDificuldade(Integer.parseInt((String)dificuldade.getSelectedItem()));
                tarefa.setStatusId(listaStatusIds.get(listaStatusNomes.indexOf(status.getSelectedItem())));


                dbHelper.atualizarTarefa(tarefa);
                 */
                ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                dbHelper.associarTags(idTarefa,tAdapter.getTagsIds());
                Intent resultado = new Intent();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });

    }
}
