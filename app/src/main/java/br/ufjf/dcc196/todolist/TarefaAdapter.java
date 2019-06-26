package br.ufjf.dcc196.todolist;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {
    private Cursor cursor;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public TarefaAdapter(Cursor c,Context context){
        cursor = c;
        this.context = context;
    }

    public void setContext(Context c){
        context = c;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View linha = inflater.inflate(R.layout.tarefa_layout,parent,false);
        ViewHolder vh = new ViewHolder(linha);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int idxId = cursor.getColumnIndexOrThrow(ToDoListContract.Tarefa._ID);
        int idxTitulo = cursor.getColumnIndexOrThrow(ToDoListContract.Tarefa.COLLUMN_TITULO);
        int idxStatus = cursor.getColumnIndexOrThrow(ToDoListContract.Tarefa.COLLUMN_STATUSID);
        cursor.moveToPosition(i);
        String titulo =cursor.getString(idxTitulo);
        viewHolder.txtId.setText(cursor.getLong(idxId)+"");

        ToDoListDBHelper dbHelper = new ToDoListDBHelper(context);
        Cursor cursorTags = dbHelper.getCursorTagsByTarefa(cursor.getLong(idxId)+"");
        List<String> sb = new ArrayList<>();
        cursorTags.move(-1);

        int idxNome = cursorTags.getColumnIndex(ToDoListContract.Tag.COLLUMN_NOME);
        while(cursorTags.moveToNext()){
            sb.add(cursorTags.getString(idxNome));
        }

        String tags = "";
        if (sb.size()>0){
            tags = " ["+String.join(";",sb)+ "]";
        }
        titulo +=tags;

        viewHolder.txtTitulo.setText(titulo);

        viewHolder.txtStatus.setText(dbHelper.getStatusById(cursor.getLong(idxStatus)).getNome());

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void setCursor(Cursor c){
        cursor = c;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitulo;
        public TextView txtId;
        public TextView txtStatus;

        public ViewHolder(final View itemView){
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloTarefa);
            txtId = itemView.findViewById(R.id.txtIdTarefa);
            txtStatus = itemView.findViewById(R.id.txtStatusTarefa);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(itemView,position);
                        }
                    }
                }
            });
        }
    }
}

