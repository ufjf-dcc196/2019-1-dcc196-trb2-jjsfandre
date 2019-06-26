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

public class TarefaActivity extends AppCompatActivity {


    final List<String> listaDificuldades = new ArrayList<String>(){{
        add("1");
        add("2");
        add("3");
        add("4");
        add("5");
    }};
    final List<String> listaStatusNomes = new ArrayList<>();
    final List<Long> listaStatusIds = new ArrayList<>();
    Tarefa tarefa;

    public EditText titulo;
    public EditText descricao;
    public Spinner dificuldade;
    public Spinner status;
    public EditText dtHrLimite;
    public EditText ultimaAtt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);
        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

        titulo = findViewById(R.id.editTituloDetalhe);
        descricao = findViewById(R.id.editDescricaoDetalhe);
        dificuldade = findViewById(R.id.editDificuldadeDetalhe);
        status = findViewById(R.id.editStatusDetalhe);
        dtHrLimite = findViewById(R.id.editDtHrLimiteDetalhe);
        ultimaAtt = findViewById(R.id.editUltimaAttDetalhe);

        List<Status> listaStatus = dbHelper.getAllStatusList();
        for (Status s :
                listaStatus) {
            listaStatusNomes.add(s.getNome());
            listaStatusIds.add(s.getId());
        }

        String id = getIntent().getStringExtra("idTarefa");
        tarefa = dbHelper.getTarefaById(id);

        titulo.setText(tarefa.getTitulo());
        descricao.setText(tarefa.getDescricao());
        dtHrLimite.setText(tarefa.getDataHoraLimite());
        ultimaAtt.setText(tarefa.getDataHoraAtualizacao());

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaStatusNomes);
        ArrayAdapter<String> dificuldadeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaDificuldades);

        dificuldade.setAdapter(dificuldadeAdapter);
        status.setAdapter(statusAdapter);


        dificuldade.setSelection(dificuldadeAdapter.getPosition(tarefa.getDificuldade()+""));
        status.setSelection(listaStatusIds.indexOf(tarefa.getStatusId()));
        Button btnSaveDetalheTarefa = findViewById(R.id.btnSaveDetalheTarefa);

        btnSaveDetalheTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

                tarefa.setTitulo(titulo.getText().toString());
                tarefa.setDescricao(descricao.getText().toString());
                tarefa.setDataHoraLimite(dtHrLimite.getText().toString());

                tarefa.setDificuldade(Integer.parseInt((String)dificuldade.getSelectedItem()));
                tarefa.setStatusId(listaStatusIds.get(listaStatusNomes.indexOf(status.getSelectedItem())));


                dbHelper.atualizarTarefa(tarefa);
                Intent resultado = new Intent();


                setResult(RESULT_OK, resultado);
                finish();
            }
        });


    }
}
