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

import br.ufjf.dcc196.todolist.Model.Tag;
import br.ufjf.dcc196.todolist.Model.Tarefa;


public class TarefaByTagAdapter extends RecyclerView.Adapter<TarefaByTagAdapter.ViewHolder> {
    private List<Tarefa> tarefaList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public TarefaByTagAdapter(List<Tarefa> tarefaList,Context context){
        this.tarefaList = tarefaList;
        this.context = context;
    }

    public void setContext(Context c){
        context = c;
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
        Tarefa current = tarefaList.get(i);
        String titulo =current.getTitulo();
        viewHolder.txtId.setText(current.getId()+"");

        ToDoListDBHelper dbHelper = new ToDoListDBHelper(context);

        viewHolder.txtTitulo.setText(titulo);

        viewHolder.txtStatus.setText(dbHelper.getStatusById(current.getStatusId()).getNome());

    }

    @Override
    public int getItemCount() {
        return tarefaList.size();
    }

    public void setTarefaList(List<Tarefa> t){
        tarefaList = t;
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
