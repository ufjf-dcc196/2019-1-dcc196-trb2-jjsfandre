package br.ufjf.dcc196.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.ufjf.dcc196.todolist.Model.Status;
import br.ufjf.dcc196.todolist.Model.Tarefa;

public class NovaTarefaActivity extends AppCompatActivity {

    final List<String> listaDificuldades = Helper.getListaDificuldades();
    final List<String> listaStatusNomes = new ArrayList<>();
    final List<Long> listaStatusIds = new ArrayList<>();
    Tarefa tarefa;

    public EditText titulo;
    public EditText descricao;
    public Spinner dificuldade;
    public Spinner status;
    public EditText dtHrLimite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);

        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

        titulo = findViewById(R.id.editTituloNovo);
        descricao = findViewById(R.id.editDescricaoNovo);
        dificuldade = findViewById(R.id.editDificuldadeNovo);
        status = findViewById(R.id.editStatusNovo);
        dtHrLimite = findViewById(R.id.editDtHrLimiteNovo);

        List<Status> listaStatus = dbHelper.getAllStatusList();
        for (Status s :
                listaStatus) {
            listaStatusNomes.add(s.getNome());
            listaStatusIds.add(s.getId());
        }

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaStatusNomes);
        ArrayAdapter<String> dificuldadeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaDificuldades);

        dificuldade.setAdapter(dificuldadeAdapter);
        status.setAdapter(statusAdapter);


        Button btnSaveNovaTarefa = findViewById(R.id.btnSaveNovaTarefa);

        btnSaveNovaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                tarefa = new Tarefa();
                tarefa.setTitulo(titulo.getText().toString());
                tarefa.setDescricao(descricao.getText().toString());
                tarefa.setDataHoraLimite(dtHrLimite.getText().toString());

                tarefa.setDificuldade(Integer.parseInt((String)dificuldade.getSelectedItem()));
                tarefa.setStatusId(listaStatusIds.get(listaStatusNomes.indexOf(status.getSelectedItem())));


                dbHelper.inserirTarefa(tarefa);

                Intent resultado = new Intent();


                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
