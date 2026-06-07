package domain;

import domain.enums.NivelRisco;

public class Obstaculo extends EntidadeBase {

    private String tipo;
    private String descricao;
    private NivelRisco nivelRisco;
    private float latitude;
    private float longitude;
    private float raioImpacto;
    private boolean contornavel;
    private String acaoRecomendada;
    private Rota rota;

    // Construtor completo (com associacao de Rota do UML)
    public Obstaculo(int id, String tipo, String descricao, NivelRisco nivelRisco,
                     float latitude, float longitude, float raioImpacto,
                     boolean contornavel, String acaoRecomendada, Rota rota) {
        super(id);
        this.tipo = tipo;
        this.descricao = descricao;
        this.nivelRisco = nivelRisco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.raioImpacto = raioImpacto;
        this.contornavel = contornavel;
        this.acaoRecomendada = acaoRecomendada;
        this.rota = rota;
    }

    // Construtor sem rota associada
    public Obstaculo(int id, String tipo, String descricao, NivelRisco nivelRisco,
                     float latitude, float longitude, float raioImpacto,
                     boolean contornavel, String acaoRecomendada) {
        this(id, tipo, descricao, nivelRisco, latitude, longitude, raioImpacto,
                contornavel, acaoRecomendada, null);
    }

    public Obstaculo() {}

    @Override
    public String gerarRelatorio() {
        System.out.println("==================================================");
        System.out.println("         RELATORIO DE OBSTACULO                   ");
        System.out.println("==================================================");
        System.out.println("ID: " + id + " | Tipo: " + tipo);
        System.out.println("Descricao: " + descricao);
        System.out.println("Nivel de Risco: " + nivelRisco);
        System.out.println("Localizacao: Lat " + latitude + " | Lon " + longitude);
        System.out.println("Raio de Impacto: " + raioImpacto + " m");
        System.out.println("Contornavel: " + (contornavel ? "Sim" : "Nao"));
        System.out.println("Acao Recomendada: " + acaoRecomendada);
        if (rota != null) System.out.println("Rota Associada: " + rota.getNome());
        System.out.println("==================================================");
        return "";
    }

    public String getResumo() {
        return String.format("[%d] %-12s | Risco: %-8s | Lat: %.3f Lon: %.3f | Contornavel: %s",
                id, tipo, nivelRisco, latitude, longitude, contornavel ? "Sim" : "Nao");
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public NivelRisco getNivelRisco() { return nivelRisco; }
    public void setNivelRisco(NivelRisco nivelRisco) { this.nivelRisco = nivelRisco; }

    public float getLatitude() { return latitude; }
    public void setLatitude(float latitude) { this.latitude = latitude; }

    public float getLongitude() { return longitude; }
    public void setLongitude(float longitude) { this.longitude = longitude; }

    public float getRaioImpacto() { return raioImpacto; }
    public void setRaioImpacto(float raioImpacto) { this.raioImpacto = raioImpacto; }

    public boolean isContornavel() { return contornavel; }
    public void setContornavel(boolean contornavel) { this.contornavel = contornavel; }

    public String getAcaoRecomendada() { return acaoRecomendada; }
    public void setAcaoRecomendada(String acaoRecomendada) { this.acaoRecomendada = acaoRecomendada; }

    public Rota getRota() { return rota; }
    public void setRota(Rota rota) { this.rota = rota; }
}
