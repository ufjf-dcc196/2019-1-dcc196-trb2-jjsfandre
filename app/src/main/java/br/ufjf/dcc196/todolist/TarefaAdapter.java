package br.ufjf.dcc196.todolist;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {
    private Cursor cursor;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public TarefaAdapter(Cursor c){
        cursor = c;
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        int idxId = cursor.getColumnIndexOrThrow(ToDoListContract.Tarefa._ID);
        int idxTitulo = cursor.getColumnIndexOrThrow(ToDoListContract.Tarefa.COLLUMN_TITULO);
        cursor.moveToPosition(i);
        viewHolder.txtTitulo.setText(cursor.getString(idxTitulo));
        viewHolder.txtId.setText(cursor.getLong(idxId)+"");

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

        public ViewHolder(final View itemView){
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloTarefa);
            txtId = itemView.findViewById(R.id.txtIdTarefa);

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

