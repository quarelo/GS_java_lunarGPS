package domain;

import domain.enums.StatusRota;
import domain.enums.TipoRota;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rota extends EntidadeBase {

    private String nome;
    private StatusRota status;
    private float distanciaTotal;
    private float consumoEnergiaEst;
    private float tempoEstimado;
    private TipoRota tipoRota;
    private LocalDateTime dataCriacao;
    private Posicao origem;
    private Posicao destino;
    private Missao missao;

    // Construtor completo (com associacoes do UML)
    public Rota(int id, String nome, StatusRota status, float distanciaTotal,
                float consumoEnergiaEst, float tempoEstimado, TipoRota tipoRota,
                LocalDateTime dataCriacao, Posicao origem, Posicao destino, Missao missao) {
        super(id);
        this.nome = nome;
        this.status = status;
        this.distanciaTotal = distanciaTotal;
        this.consumoEnergiaEst = consumoEnergiaEst;
        this.tempoEstimado = tempoEstimado;
        this.tipoRota = tipoRota;
        this.dataCriacao = dataCriacao;
        this.origem = origem;
        this.destino = destino;
        this.missao = missao;
    }

    // Construtor sem missao
    public Rota(int id, String nome, StatusRota status, float distanciaTotal,
                float consumoEnergiaEst, float tempoEstimado, TipoRota tipoRota,
                LocalDateTime dataCriacao, Posicao origem, Posicao destino) {
        this(id, nome, status, distanciaTotal, consumoEnergiaEst, tempoEstimado,
                tipoRota, dataCriacao, origem, destino, null);
    }

    public Rota() {}

    @Override
    public String gerarRelatorio() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("==================================================");
        System.out.println("         RELATORIO DE ROTA                        ");
        System.out.println("==================================================");
        System.out.println("ID: " + id + " | Nome: " + nome);
        System.out.println("Status: " + status + " | Tipo: " + tipoRota);
        System.out.println("Distancia: " + String.format("%.2f km", distanciaTotal));
        System.out.println("Consumo Estimado: " + String.format("%.2f kWh", consumoEnergiaEst));
        System.out.println("Tempo Estimado: " + String.format("%.1f h", tempoEstimado));
        System.out.println("Criada em: " + (dataCriacao != null ? dataCriacao.format(fmt) : "N/A"));
        if (origem != null)  System.out.println("Origem: " + origem.getResumo());
        if (destino != null) System.out.println("Destino: " + destino.getResumo());
        if (missao != null)  System.out.println("Missao: " + missao.getNome());
        System.out.println("==================================================");
        return "";
    }

    public String getResumo() {
        return String.format("[%d] %-28s | %-10s | %-12s | %.2f km | %.2f kWh",
                id, nome, status, tipoRota, distanciaTotal, consumoEnergiaEst);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public StatusRota getStatus() { return status; }
    public void setStatus(StatusRota status) { this.status = status; }

    public float getDistanciaTotal() { return distanciaTotal; }
    public void setDistanciaTotal(float distanciaTotal) { this.distanciaTotal = distanciaTotal; }

    public float getConsumoEnergiaEst() { return consumoEnergiaEst; }
    public void setConsumoEnergiaEst(float consumoEnergiaEst) { this.consumoEnergiaEst = consumoEnergiaEst; }

    public float getTempoEstimado() { return tempoEstimado; }
    public void setTempoEstimado(float tempoEstimado) { this.tempoEstimado = tempoEstimado; }

    public TipoRota getTipoRota() { return tipoRota; }
    public void setTipoRota(TipoRota tipoRota) { this.tipoRota = tipoRota; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public Posicao getOrigem() { return origem; }
    public void setOrigem(Posicao origem) { this.origem = origem; }

    public Posicao getDestino() { return destino; }
    public void setDestino(Posicao destino) { this.destino = destino; }

    public Missao getMissao() { return missao; }
    public void setMissao(Missao missao) { this.missao = missao; }
}
