package domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Astronauta extends Pessoa {

    private String matricula;
    private String nacionalidade;
    private LocalDate dataNascimento;
    private float nivelOxigenio;
    private float nivelEnergia;
    private int posicaoAtual;

    public Astronauta(int id, String nome, String matricula, String nacionalidade,
                      LocalDate dataNascimento, float nivelOxigenio, float nivelEnergia,
                      int posicaoAtual) {
        super(id, nome);
        this.matricula = matricula;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
        this.nivelOxigenio = nivelOxigenio;
        this.nivelEnergia = nivelEnergia;
        this.posicaoAtual = posicaoAtual;
    }

    public Astronauta() {}

    @Override
    public String gerarRelatorio() {
        String statusSaude = (nivelOxigenio < 20.0f || nivelEnergia < 15.0f) ? "[CRITICO]" : "[OK]";
        String nasc = dataNascimento != null
                ? dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "N/A";
        System.out.println("==================================================");
        System.out.println("         RELATORIO DO ASTRONAUTA                  ");
        System.out.println("==================================================");
        System.out.println("ID: " + id + " | Nome: " + nome);
        System.out.println("Matricula: " + matricula + " | Nacionalidade: " + nacionalidade);
        System.out.println("Nascimento: " + nasc);
        System.out.println("Nivel O2: " + nivelOxigenio + "% | Energia: " + nivelEnergia + "%");
        System.out.println("Status de Saude: " + statusSaude);
        System.out.println("ID Posicao Atual: " + (posicaoAtual == 0 ? "Nao registrada" : posicaoAtual));
        System.out.println("==================================================");
        return "";
    }

    public String getResumo() {
        String status = (nivelOxigenio < 20.0f || nivelEnergia < 15.0f) ? "[CRITICO]" : "[OK]";
        return String.format("[%d] %s | %s | O2: %.1f%% | Energia: %.1f%% | %s",
                id, nome, matricula, nivelOxigenio, nivelEnergia, status);
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public float getNivelOxigenio() { return nivelOxigenio; }
    public void setNivelOxigenio(float nivelOxigenio) { this.nivelOxigenio = nivelOxigenio; }

    public float getNivelEnergia() { return nivelEnergia; }
    public void setNivelEnergia(float nivelEnergia) { this.nivelEnergia = nivelEnergia; }

    public int getPosicaoAtual() { return posicaoAtual; }
    public void setPosicaoAtual(int posicaoAtual) { this.posicaoAtual = posicaoAtual; }
}
