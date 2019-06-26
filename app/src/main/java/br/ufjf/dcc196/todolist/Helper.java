package br.ufjf.dcc196.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static final String getDataHoraAtualFormatada(){

        String formato = "dd/MM/yyyy h:mm";
        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(agora);
    }
}
