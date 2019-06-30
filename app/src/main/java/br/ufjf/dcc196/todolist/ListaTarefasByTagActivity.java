package br.ufjf.dcc196.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class ListaTarefasByTagActivity extends AppCompatActivity {
    public TarefaByTagAdapter tAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas_by_tag);

        final RecyclerView rv = findViewById(R.id.rvTarefasByTag);
        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

        String id = getIntent().getStringExtra("idTag");
        String tituloTag = getIntent().getStringExtra("tituloTag");

        TextView infoTagList = findViewById(R.id.infoTagList);
        infoTagList.setText("Tarefas que contém a etiqueta "+tituloTag);
        tAdapter = new TarefaByTagAdapter(dbHelper.getListTarefasByTag(id),getApplicationContext());
        rv.setAdapter(tAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
