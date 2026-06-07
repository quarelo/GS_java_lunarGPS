package application;

import domain.Missao;
import domain.enums.PrioridadeMissao;
import domain.enums.StatusMissao;
import infrastructure.MissaoRepositorio;

import java.time.LocalDateTime;
import java.util.List;

public class MissaoService {

    private final MissaoRepositorio repositorio = new MissaoRepositorio();

    // Overload 1: criacao rapida (nome + objetivo)
    public Missao criar(String nome, String objetivo) {
        Missao missao = new Missao(0, nome, objetivo, PrioridadeMissao.MEDIA);
        repositorio.salvar(missao);
        System.out.println("[OK] Missao criada com ID: " + missao.getId());
        return missao;
    }

    // Overload 2: criacao com descricao e prioridade
    public Missao criar(String nome, String descricao, String objetivo, PrioridadeMissao prioridade) {
        Missao missao = new Missao(0, nome, descricao, objetivo,
                StatusMissao.PLANEJADA, prioridade, LocalDateTime.now(), null, null, 0);
        repositorio.salvar(missao);
        System.out.println("[OK] Missao criada com ID: " + missao.getId());
        return missao;
    }

    // Overload 3: criacao completa com datas
    public Missao criar(String nome, String descricao, String objetivo, PrioridadeMissao prioridade,
                        LocalDateTime dataInicio, LocalDateTime dataPrevisaoFim) {
        Missao missao = new Missao(0, nome, descricao, objetivo,
                StatusMissao.PLANEJADA, prioridade, dataInicio, dataPrevisaoFim, null, 0);
        repositorio.salvar(missao);
        System.out.println("[OK] Missao criada com ID: " + missao.getId());
        return missao;
    }

    public Missao buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    public List<Missao> listarTodos() {
        return repositorio.listarTodos();
    }

    public List<Missao> listarPorStatus(StatusMissao status) {
        return repositorio.listarPorStatus(status);
    }

    public void iniciar(int idMissao) {
        Missao missao = repositorio.buscarPorId(idMissao);
        if (missao == null) { System.out.println("[ERRO] Missao nao encontrada: " + idMissao); return; }
        missao.setStatus(StatusMissao.EM_ANDAMENTO);
        missao.setDataInicio(LocalDateTime.now());
        System.out.println("[OK] Missao iniciada: " + missao.getNome());
    }

    public void concluir(int idMissao) {
        Missao missao = repositorio.buscarPorId(idMissao);
        if (missao == null) { System.out.println("[ERRO] Missao nao encontrada: " + idMissao); return; }
        missao.setStatus(StatusMissao.CONCLUIDA);
        missao.setDataFimReal(LocalDateTime.now());
        System.out.println("[OK] Missao concluida: " + missao.getNome());
    }

    public void cancelar(int idMissao) {
        Missao missao = repositorio.buscarPorId(idMissao);
        if (missao == null) { System.out.println("[ERRO] Missao nao encontrada: " + idMissao); return; }
        missao.setStatus(StatusMissao.CANCELADA);
        System.out.println("[OK] Missao cancelada: " + missao.getNome());
    }
}
