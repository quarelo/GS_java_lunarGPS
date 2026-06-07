package domain;

public abstract class EntidadeBase {

    protected int id;

    public EntidadeBase(int id) {
        this.id = id;
    }

    public EntidadeBase() {}

    public abstract String gerarRelatorio();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return gerarRelatorio();
    }
}
