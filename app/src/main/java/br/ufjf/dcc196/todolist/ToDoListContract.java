package br.ufjf.dcc196.todolist;

import android.provider.BaseColumns;


public final class ToDoListContract {

    public static class Tarefa implements BaseColumns {

        public static final String TABLE_NAME = "Tarefa";
        public static final String COLLUMN_TITULO = "titulo";
        public static final String COLLUMN_DESCRICAO = "descricao";
        public static final String COLLUMN_DIFICULDADE = "dificuldade";
        public static final String COLLUMN_DTHORALIMITE = "dthoralimite";
        public static final String COLLUMN_DTHORAATUALIZACAO = "dthoraatualizacao";
        public static final String COLLUMN_STATUSID = "statusid";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT, %s INTEGER)",
                TABLE_NAME, _ID, COLLUMN_TITULO, COLLUMN_DESCRICAO, COLLUMN_DIFICULDADE,
                COLLUMN_DTHORALIMITE, COLLUMN_DTHORAATUALIZACAO, COLLUMN_STATUSID
        );
        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);

    }
    public static class Status implements BaseColumns {
        public static final String TABLE_NAME = "Status";
        public static final String COLLUMN_NOME = "nome";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT)", TABLE_NAME, _ID, COLLUMN_NOME
        );
        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);
    }

    public static class Tag implements BaseColumns {
        public static final String TABLE_NAME = "Tag";
        public static final String COLLUMN_NOME = "nome";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT)", TABLE_NAME, _ID, COLLUMN_NOME
        );
        public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);
    }

    public static class TarefaTag implements BaseColumns {
        public static final String TABLE_NAME="TarefaTag";
        public static final String COLLUMN_TAG ="tagid";
        public static final String COLLUMN_TAREFA="tarefaid";

        public static final String CREATE_TABLE=String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s INTEGER," +
                "%s INTEGER)",TABLE_NAME,_ID, COLLUMN_TAG,COLLUMN_TAREFA
        );
        public static final String DROP_TABLE=String.format("DROP TABLE %s", TABLE_NAME);
    }

}
