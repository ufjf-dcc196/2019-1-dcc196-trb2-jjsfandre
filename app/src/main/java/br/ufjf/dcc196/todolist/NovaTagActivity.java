package br.ufjf.dcc196.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.ufjf.dcc196.todolist.Model.Status;
import br.ufjf.dcc196.todolist.Model.Tag;
import br.ufjf.dcc196.todolist.Model.Tarefa;

public class NovaTagActivity extends AppCompatActivity {

    public EditText nome;
    public Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tag);

        nome = findViewById(R.id.editNomeNovo);

        Button btnSaveNovaTag = findViewById(R.id.btnSaveNovaTag);

        btnSaveNovaTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                tag = new Tag();
                tag.setNome(nome.getText().toString());

                dbHelper.inserirTag(tag);

                Intent resultado = new Intent();

                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
