package br.ufjf.dcc196.todolist.Model;

public class Status {

    private Long id;
    private String nome;

    public Status(){

    }

    public Status(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    //region GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    //endregion


    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
