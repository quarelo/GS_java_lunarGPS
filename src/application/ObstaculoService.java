package application;

import domain.Obstaculo;
import domain.enums.NivelRisco;
import infrastructure.ObstaculoRepositorio;

import java.util.List;

public class ObstaculoService {

    private final ObstaculoRepositorio repositorio = new ObstaculoRepositorio();

    public void registrar(Obstaculo obstaculo) {
        repositorio.salvar(obstaculo);
        System.out.println("[OK] Obstaculo registrado com ID: " + obstaculo.getId());
    }

    public Obstaculo buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    public List<Obstaculo> listarTodos() {
        return repositorio.listarTodos();
    }

    public List<Obstaculo> listarPorNivelRisco(NivelRisco nivel) {
        return repositorio.listarPorNivelRisco(nivel);
    }

    public List<Obstaculo> listarNaoContornaveis() {
        return repositorio.listarNaoContornaveis();
    }

    public boolean verificarConflito(float latitude, float longitude) {
        for (Obstaculo o : repositorio.listarTodos()) {
            double distGraus = Math.sqrt(
                    Math.pow(o.getLatitude() - latitude, 2) + Math.pow(o.getLongitude() - longitude, 2));
            double distMetros = distGraus * 111000;
            if (distMetros <= o.getRaioImpacto()) return true;
        }
        return false;
    }
}
