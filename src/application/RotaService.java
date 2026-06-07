package application;

import domain.Posicao;
import domain.Rota;
import domain.enums.StatusRota;
import domain.enums.TipoRota;
import infrastructure.RotaRepositorio;

import java.time.LocalDateTime;
import java.util.List;

public class RotaService {

    private final RotaRepositorio repositorio = new RotaRepositorio();
    private final NavegacaoService navegacaoService;

    public RotaService(NavegacaoService navegacaoService) {
        this.navegacaoService = navegacaoService;
    }

    public Rota gerar(String nome, TipoRota tipoRota,
                      float latOrigem, float lonOrigem,
                      float latDestino, float lonDestino) {
        double distancia = navegacaoService.calcularDistancia(latOrigem, lonOrigem, latDestino, lonDestino);
        double consumo   = navegacaoService.calcularConsumoEnergetico(distancia, tipoRota.name());
        double tempo     = distancia / 5.0;

        Posicao origem  = new Posicao(0, latOrigem,  lonOrigem,  0f, 0f, LocalDateTime.now(), 'A');
        Posicao destino = new Posicao(0, latDestino, lonDestino, 0f, 0f, LocalDateTime.now(), 'A');
        navegacaoService.registrarPosicao(origem);
        navegacaoService.registrarPosicao(destino);

        Rota rota = new Rota(0, nome, StatusRota.PLANEJADA, (float) distancia,
                (float) consumo, (float) tempo, tipoRota, LocalDateTime.now(), origem, destino);
        repositorio.salvar(rota);
        System.out.println("[OK] Rota gerada com ID: " + rota.getId());
        return rota;
    }

    public Rota buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    public List<Rota> listarTodos() {
        return repositorio.listarTodos();
    }

    public List<Rota> listarPorStatus(StatusRota status) {
        return repositorio.listarPorStatus(status);
    }

    public void ativar(int idRota) {
        Rota rota = repositorio.buscarPorId(idRota);
        if (rota == null) { System.out.println("[ERRO] Rota nao encontrada: " + idRota); return; }
        rota.setStatus(StatusRota.ATIVA);
        System.out.println("[OK] Rota ativada: " + rota.getNome());
    }

    public void concluir(int idRota) {
        Rota rota = repositorio.buscarPorId(idRota);
        if (rota == null) { System.out.println("[ERRO] Rota nao encontrada: " + idRota); return; }
        rota.setStatus(StatusRota.CONCLUIDA);
        System.out.println("[OK] Rota concluida: " + rota.getNome());
    }
}
