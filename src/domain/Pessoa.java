package domain;

public abstract class Pessoa extends EntidadeBase {

    protected String nome;

    public Pessoa(int id, String nome) {
        super(id);
        this.nome = nome;
    }

    public Pessoa() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
