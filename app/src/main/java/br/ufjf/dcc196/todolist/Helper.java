package br.ufjf.dcc196.todolist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Helper {

    public static final String getDataHoraAtualFormatada(){

        String formato = "dd/MM/yyyy h:mm";
        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(agora);
    }

    public static final ArrayList<String> getListaDificuldades(){
        return new ArrayList<String>(){{
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
        }};
    }
}
