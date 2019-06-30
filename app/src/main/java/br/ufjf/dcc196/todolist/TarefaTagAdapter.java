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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufjf.dcc196.todolist.Model.Tag;

public class TarefaTagAdapter extends RecyclerView.Adapter<TarefaTagAdapter.ViewHolder> {
    private Cursor cursor;
    private OnItemClickListener listener;
    private Context context;
    private List<Tag> tags;
    private List<Long> tagsIds;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public TarefaTagAdapter(Cursor c, List<Tag> tags, Context context){
        cursor = c;
        this.context = context;
        this.tags = tags;
        this.tagsIds = new ArrayList<>();
    }

    public List<Long> getTagsIds(){
        return tagsIds;
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

        View linha = inflater.inflate(R.layout.tarefa_tag_layout,parent,false);
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
        viewHolder.checkBoxTarefaTag.setChecked(verifyIfTagIsChecked(cursor.getLong(idxId)));
    }

    private boolean verifyIfTagIsChecked(Long id){
        for (Tag tag :
                tags) {
            if (tag.getId().equals(id))
                return true;
        }
        return false;
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
        public CheckBox checkBoxTarefaTag;

        public ViewHolder(final View itemView){
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloTag);
            txtId = itemView.findViewById(R.id.txtIdTag);
            checkBoxTarefaTag = itemView.findViewById(R.id.checkBoxTarefaTag);

            checkBoxTarefaTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if (isChecked) {
                        tagsIds.add(Long.parseLong(txtId.getText().toString()));
                    } else {
                        tagsIds.remove(tagsIds.indexOf(Long.parseLong(txtId.getText().toString())));
                    }
                }
            });

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
