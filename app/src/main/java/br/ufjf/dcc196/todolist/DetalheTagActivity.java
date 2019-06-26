package br.ufjf.dcc196.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufjf.dcc196.todolist.Model.Tag;

public class DetalheTagActivity extends AppCompatActivity {

    public EditText nome;
    public Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_tag);
        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

        nome = findViewById(R.id.editNomeDetalhe);

        String id = getIntent().getStringExtra("idTag");
        tag = dbHelper.getTagById(id);

        nome.setText(tag.getNome());

        Button btnSaveDetalheTag = findViewById(R.id.btnSaveDetalheTag);

        btnSaveDetalheTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

                tag.setNome(nome.getText().toString());
                dbHelper.atualizarTag(tag);

                Intent resultado = new Intent();
                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}
