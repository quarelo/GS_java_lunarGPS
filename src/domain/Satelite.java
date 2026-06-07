package domain;

import domain.enums.StatusSatelite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Satelite extends EntidadeBase {

    private String nome;
    private String codigoIdentificacao;
    private float altitudeOrbital;
    private float inclinacaoOrbital;
    private float periodoOrbital;
    private StatusSatelite status;
    private LocalDateTime ultimoContato;

    public Satelite(int id, String nome, String codigoIdentificacao, float altitudeOrbital,
                    float inclinacaoOrbital, float periodoOrbital, StatusSatelite status,
                    LocalDateTime ultimoContato) {
        super(id);
        this.nome = nome;
        this.codigoIdentificacao = codigoIdentificacao;
        this.altitudeOrbital = altitudeOrbital;
        this.inclinacaoOrbital = inclinacaoOrbital;
        this.periodoOrbital = periodoOrbital;
        this.status = status;
        this.ultimoContato = ultimoContato;
    }

    public Satelite() {}

    @Override
    public String gerarRelatorio() {
        String contato = ultimoContato != null
                ? ultimoContato.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "N/A";
        System.out.println("==================================================");
        System.out.println("         RELATORIO DO SATELITE                    ");
        System.out.println("==================================================");
        System.out.println("ID: " + id + " | Nome: " + nome + " (" + codigoIdentificacao + ")");
        System.out.println("Orbita: " + altitudeOrbital + " km | Inclinacao: " + inclinacaoOrbital + " graus");
        System.out.println("Periodo Orbital: " + periodoOrbital + " h");
        System.out.println("Status: " + status + " | Ultimo Contato: " + contato);
        System.out.println("==================================================");
        return "";
    }

    public String getResumo() {
        return String.format("[%d] %s (%s) | Orbita: %.0f km | Status: %s",
                id, nome, codigoIdentificacao, altitudeOrbital, status);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigoIdentificacao() { return codigoIdentificacao; }
    public void setCodigoIdentificacao(String cod) { this.codigoIdentificacao = cod; }

    public float getAltitudeOrbital() { return altitudeOrbital; }
    public void setAltitudeOrbital(float altitudeOrbital) { this.altitudeOrbital = altitudeOrbital; }

    public float getInclinacaoOrbital() { return inclinacaoOrbital; }
    public void setInclinacaoOrbital(float inclinacaoOrbital) { this.inclinacaoOrbital = inclinacaoOrbital; }

    public float getPeriodoOrbital() { return periodoOrbital; }
    public void setPeriodoOrbital(float periodoOrbital) { this.periodoOrbital = periodoOrbital; }

    public StatusSatelite getStatus() { return status; }
    public void setStatus(StatusSatelite status) { this.status = status; }

    public LocalDateTime getUltimoContato() { return ultimoContato; }
    public void setUltimoContato(LocalDateTime ultimoContato) { this.ultimoContato = ultimoContato; }
}
