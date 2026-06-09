package domain;

import domain.enums.PrioridadeMissao;
import domain.enums.StatusMissao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Missao extends EntidadeBase {

    private String nome;
    private String descricao;
    private String objetivo;
    private StatusMissao status;
    private PrioridadeMissao prioridade;
    private LocalDateTime dataInicio;
    private LocalDateTime dataPrevisaoFim;
    private LocalDateTime dataFimReal;
    private float distanciaTotal;

    public Missao(int id, String nome, String descricao, String objetivo,
                  StatusMissao status, PrioridadeMissao prioridade,
                  LocalDateTime dataInicio, LocalDateTime dataPrevisaoFim,
                  LocalDateTime dataFimReal, float distanciaTotal) {
        super(id);
        this.nome = nome;
        this.descricao = descricao;
        this.objetivo = objetivo;
        this.status = status;
        this.prioridade = prioridade;
        this.dataInicio = dataInicio;
        this.dataPrevisaoFim = dataPrevisaoFim;
        this.dataFimReal = dataFimReal;
        this.distanciaTotal = distanciaTotal;
    }

    public Missao(int id, String nome, String objetivo, PrioridadeMissao prioridade) {
        super(id);
        this.nome = nome;
        this.descricao = "";
        this.objetivo = objetivo;
        this.status = StatusMissao.PLANEJADA;
        this.prioridade = prioridade;
        this.dataInicio = LocalDateTime.now();
    }

    public Missao() {}

    @Override
    public String gerarRelatorio() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("==================================================");
        System.out.println("         RELATORIO DE MISSAO                      ");
        System.out.println("==================================================");
        System.out.println("ID: " + id + " | Nome: " + nome);
        System.out.println("Descricao: " + descricao);
        System.out.println("Objetivo: " + objetivo);
        System.out.println("Status: " + status + " | Prioridade: " + prioridade);
        System.out.println("Inicio: " + (dataInicio != null ? dataInicio.format(fmt) : "N/A"));
        System.out.println("Prev. Fim: " + (dataPrevisaoFim != null ? dataPrevisaoFim.format(fmt) : "N/A"));
        System.out.println("Fim Real: " + (dataFimReal != null ? dataFimReal.format(fmt) : "Em andamento"));
        System.out.println("Distancia Total: " + distanciaTotal + " km");
        System.out.println("==================================================");
        return "";
    }

    public String getResumo() {
        return String.format("[%d] %-28s | %-15s | Prio: %s", id, nome, status, prioridade);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public StatusMissao getStatus() { return status; }
    public void setStatus(StatusMissao status) { this.status = status; }

    public PrioridadeMissao getPrioridade() { return prioridade; }
    public void setPrioridade(PrioridadeMissao prioridade) { this.prioridade = prioridade; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataPrevisaoFim() { return dataPrevisaoFim; }
    public void setDataPrevisaoFim(LocalDateTime dataPrevisaoFim) { this.dataPrevisaoFim = dataPrevisaoFim; }

    public LocalDateTime getDataFimReal() { return dataFimReal; }
    public void setDataFimReal(LocalDateTime dataFimReal) { this.dataFimReal = dataFimReal; }

    public float getDistanciaTotal() { return distanciaTotal; }
    public void setDistanciaTotal(float distanciaTotal) { this.distanciaTotal = distanciaTotal; }
}
