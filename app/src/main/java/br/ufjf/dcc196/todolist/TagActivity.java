package br.ufjf.dcc196.todolist;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class TagActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener  {

    public TagAdapter tAdapter;

    public static final int REQUEST_DETALHE_TAG = 400;
    public static final int REQUEST_LISTAR_TAREFAS_TAG = 500;
    public static final int REQUEST_NOVA_TAG= 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        Switch switchExcluirTag = (Switch)findViewById(R.id.switchExcluirTag);
        Switch switchEditarTag = (Switch)findViewById(R.id.switchEditarTag);

        switchExcluirTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch switchEditarTag = (Switch)findViewById(R.id.switchEditarTag);
                if (isChecked)
                    switchEditarTag.setChecked(false);

            }
        });

        switchEditarTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch switchExcluirTag = (Switch)findViewById(R.id.switchExcluirTag);
                if (isChecked)
                    switchExcluirTag.setChecked(false);
            }
        });

        final RecyclerView rv = findViewById(R.id.rvTags);
        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

        tAdapter = new TagAdapter(dbHelper.getCursorTodasAsTags(),getApplicationContext());
        tAdapter.setOnItemClickListener(listenerDefault);
        rv.setAdapter(tAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private TagAdapter.OnItemClickListener listenerDefault =
            new TagAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    Switch switchExcluirTag = (Switch)findViewById(R.id.switchExcluirTag);
                    Switch switchEditarTag = (Switch)findViewById(R.id.switchEditarTag);

                    if (switchExcluirTag.isChecked()){
                        listenerDeleteTag.onItemClick(itemView,position);
                    }else if (switchEditarTag.isChecked()){
                        listenerEditTag.onItemClick(itemView,position);
                    }else{
                        listenerListTarefasByTag.onItemClick(itemView,position);
                    }

                }
            };

    private TagAdapter.OnItemClickListener listenerEditTag =
            new TagAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdTag);

                    Intent intent = new Intent(TagActivity.this, DetalheTagActivity.class);
                    intent.putExtra("idTag", txtId.getText().toString());

                    startActivityForResult(intent, REQUEST_DETALHE_TAG);
                }
            };

    private TagAdapter.OnItemClickListener listenerDeleteTag =
            new TagAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdTag);
                    TextView txtTitulo = (TextView) itemView.findViewById(R.id.txtTituloTag);
                    dbHelper.deleteTagById(txtId.getText().toString(), txtTitulo.getText().toString());

                    tAdapter.setCursor(dbHelper.getCursorTodasAsTags());
                    tAdapter.notifyItemRemoved(position);
                }
            };

    private TagAdapter.OnItemClickListener listenerListTarefasByTag =
            new TagAdapter.OnItemClickListener(){
                @Override
                public void onItemClick(View itemView, int position){
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdTag);

                    Intent intent = new Intent(TagActivity.this, ListaTarefasByTagActivity.class);
                    intent.putExtra("idTag", txtId.getText().toString());

                    startActivityForResult(intent, REQUEST_LISTAR_TAREFAS_TAG);
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            switch (requestCode){
                case REQUEST_DETALHE_TAG:
                    if (resultCode == Activity.RESULT_OK) {
                        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                        tAdapter.setCursor(dbHelper.getCursorTodasAsTags());
                    }
                    break;
                case REQUEST_LISTAR_TAREFAS_TAG:
                    if (resultCode == Activity.RESULT_OK) {
                        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                        tAdapter.setCursor(dbHelper.getCursorTodasAsTags());
                    }
                    break;
                case REQUEST_NOVA_TAG:
                    if (resultCode == Activity.RESULT_OK) {
                        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                        tAdapter.setCursor(dbHelper.getCursorTodasAsTags());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void showMenuTag(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.tag_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.addTag:
                adicionarTag();
                return true;
            default:
                return false;
        }

    }

    public void listarTarefasByTag(){

        Intent intent = new Intent(TagActivity.this, ListaTarefasByTagActivity.class);

        startActivityForResult(intent, REQUEST_LISTAR_TAREFAS_TAG);
    }

    public void adicionarTag(){

        Intent intent = new Intent(TagActivity.this, NovaTagActivity.class);

        startActivityForResult(intent, REQUEST_NOVA_TAG);
    }
}
