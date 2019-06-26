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

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private Cursor cursor;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public TagAdapter(Cursor c,Context context){
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

        View linha = inflater.inflate(R.layout.tag_layout,parent,false);
        ViewHolder vh = new ViewHolder(linha);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int idxId = cursor.getColumnIndexOrThrow(ToDoListContract.Tag._ID);
        int idxTitulo = cursor.getColumnIndexOrThrow(ToDoListContract.Tag.COLLUMN_NOME);
        cursor.moveToPosition(i);
        viewHolder.txtId.setText(cursor.getLong(idxId)+"");
        viewHolder.txtTitulo.setText(cursor.getString(idxTitulo));
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
            txtTitulo = itemView.findViewById(R.id.txtTituloTag);
            txtId = itemView.findViewById(R.id.txtIdTag);

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
