package br.ufjf.dcc196.todolist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    /*
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
    } */


    public TarefaAdapter tAdapter;

    public static final int REQUEST_LISTAR_TAGS = 100;
    public static final int REQUEST_DETALHE_TAREFA = 200;
    public static final int REQUEST_NOVA_TAREFA = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch switch1 = (Switch)findViewById(R.id.switchExcluirTarefa);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    tAdapter.setOnItemClickListener(listenerDeleteTarefa);
                else
                    tAdapter.setOnItemClickListener(listenerEditTarefa);
            }
        });

        final RecyclerView rv = findViewById(R.id.rvTarefas);
        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());

        tAdapter = new TarefaAdapter(dbHelper.getCursorTodasAsTarefas(),getApplicationContext());
        tAdapter.setOnItemClickListener(listenerEditTarefa);
        rv.setAdapter(tAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private TarefaAdapter.OnItemClickListener listenerEditTarefa =
        new TarefaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                TextView txtId = (TextView) itemView.findViewById(R.id.txtIdTarefa);

                Cursor c = dbHelper.getCursorTarefaById(txtId.getText().toString());

                Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                intent.putExtra("idTarefa", txtId.getText().toString());

                startActivityForResult(intent, REQUEST_DETALHE_TAREFA);
            }
        };

    private TarefaAdapter.OnItemClickListener listenerDeleteTarefa =
            new TarefaAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                    TextView txtId = (TextView) itemView.findViewById(R.id.txtIdTarefa);
                    TextView txtTitulo = (TextView) itemView.findViewById(R.id.txtTituloTarefa);
                    dbHelper.deleteTarefaById(txtId.getText().toString(), txtTitulo.getText().toString());

                    tAdapter.setCursor(dbHelper.getCursorTodasAsTarefas());
                    tAdapter.notifyItemRemoved(position);
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            switch (requestCode){
                case REQUEST_DETALHE_TAREFA:
                    if (resultCode == Activity.RESULT_OK) {
                        ToDoListDBHelper dbHelper = new ToDoListDBHelper(getApplicationContext());
                        tAdapter.setCursor(dbHelper.getCursorTodasAsTarefas());
                        tAdapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_NOVA_TAREFA:
                    if (resultCode == Activity.RESULT_OK) {
                        tAdapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_LISTAR_TAGS:
                    if (resultCode == Activity.RESULT_OK) {
                        tAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void showMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.main_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.addTarefa:
                adicionarTarefa();
                return true;
            case R.id.listTags:
                listarTags();
                return true;
            default:
                return false;
        }

    }

    public void listarTags(){
        Toast.makeText(this,"listarTags", Toast.LENGTH_SHORT).show();
    }

    public void adicionarTarefa(){
        Toast.makeText(this,"adicionarTarefa", Toast.LENGTH_SHORT).show();
    }


}
