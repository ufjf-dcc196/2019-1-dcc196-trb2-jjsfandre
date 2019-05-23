package br.ufjf.dcc196.todolist;

import android.provider.BaseColumns;


public final class ToDoListContract {

    public static class Tarefa implements BaseColumns {

        //region TAREFA
        public static final String TABLE_NAME_TAREFA="Tarefa";
        public static final String COLLUMN_TAREFA_TITULO="titulo";
        public static final String COLLUMN_TAREFA_DESCRICAO="descricao";
        public static final String COLLUMN_TAREFA_DIFICULDADE="dificuldade";
        public static final String COLLUMN_TAREFA_DTHORALIMITE="dthoralimite";
        public static final String COLLUMN_TAREFA_DTHORAATUALIZACAO="dthoraatualizacao";
        public static final String COLLUMN_TAREFA_STATUSID="statusid";

        public static final String CREATE_TABLE_TAREFA=String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT, %s INTEGER)",
                TABLE_NAME_TAREFA,_ID,COLLUMN_TAREFA_TITULO,COLLUMN_TAREFA_DESCRICAO,COLLUMN_TAREFA_DIFICULDADE,
                COLLUMN_TAREFA_DTHORALIMITE,COLLUMN_TAREFA_DTHORAATUALIZACAO,COLLUMN_TAREFA_STATUSID
        );
        public static final String DROP_TABLE_TAREFA=String.format("DROP TABLE %s", TABLE_NAME_TAREFA);
        //endregion

        //region STATUS
        public static final String TABLE_NAME_STATUS="Status";
        public static final String COLLUMN_STATUS_NOME="nome";

        public static final String CREATE_TABLE_STATUS=String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT)",TABLE_NAME_STATUS,_ID,COLLUMN_STATUS_NOME
        );
        public static final String DROP_TABLE_STATUS=String.format("DROP TABLE %s", TABLE_NAME_STATUS);
        //endregion

        //region TAG
        public static final String TABLE_NAME_TAG="Tag";
        public static final String COLLUMN_TAG_NOME="nome";

        public static final String CREATE_TABLE_TAG=String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT)",TABLE_NAME_TAG,_ID,COLLUMN_TAG_NOME
        );
        public static final String DROP_TABLE_TAG=String.format("DROP TABLE %s", TABLE_NAME_TAG);
        //endregion

        //region TAREFATAG
        public static final String TABLE_NAME_TAREFATAG="TarefaTag";
        public static final String COLLUMN_TAG_STATUS="statusid";
        public static final String COLLUMN_TAG_TAREFA="tarefaid";

        public static final String CREATE_TABLE_TAREFATAG=String.format("CREATE TABLE %s (%s INTEGER," +
                "%s INTEGER)",TABLE_NAME_TAREFATAG,COLLUMN_TAG_STATUS,COLLUMN_TAG_TAREFA
        );
        public static final String DROP_TABLE_TAREFATAG=String.format("DROP TABLE %s", TABLE_NAME_TAREFATAG);
        //endregion
    }

}
