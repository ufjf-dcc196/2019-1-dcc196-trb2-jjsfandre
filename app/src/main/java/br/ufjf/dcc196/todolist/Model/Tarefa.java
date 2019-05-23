package br.ufjf.dcc196.todolist.Model;

public class Tarefa {

    private Long id;
    private String titulo;
    private String descricao;
    private int dificuldade;
    private long statusId;
    private Status status;
    private String dataHoraLimite;
    private String dataHoraAtualizacao;


    public Tarefa() {
    }

    public Tarefa(Long id, String titulo, String descricao, int dificuldade, long statusId, Status status, String dataHoraLimite, String dataHoraAtualizacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.statusId = statusId;
        this.status = status;
        this.dataHoraLimite = dataHoraLimite;
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    //region GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDataHoraLimite() {
        return dataHoraLimite;
    }

    public void setDataHoraLimite(String dataHoraLimite) {
        this.dataHoraLimite = dataHoraLimite;
    }

    public String getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(String dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    //endregion
}
