package application;

import domain.Astronauta;
import infrastructure.AstronautaRepositorio;

import java.util.ArrayList;
import java.util.List;

public class AstronautaService {

    private final AstronautaRepositorio repositorio = new AstronautaRepositorio();

    public void cadastrar(Astronauta astronauta) {
        if (repositorio.buscarPorMatricula(astronauta.getMatricula()) != null) {
            throw new IllegalArgumentException("Matricula ja cadastrada: " + astronauta.getMatricula());
        }
        repositorio.salvar(astronauta);
        System.out.println("[OK] Astronauta cadastrado com ID: " + astronauta.getId());
    }

    public Astronauta buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    public List<Astronauta> listarTodos() {
        return repositorio.listarTodos();
    }

    public List<Astronauta> listarEmNivelCritico() {
        List<Astronauta> criticos = new ArrayList<>();
        for (Astronauta a : repositorio.listarTodos()) {
            if (a.getNivelOxigenio() < 20.0f || a.getNivelEnergia() < 15.0f) {
                criticos.add(a);
            }
        }
        return criticos;
    }

    public void atualizarPosicao(int idAstronauta, int idPosicao) {
        Astronauta astronauta = repositorio.buscarPorId(idAstronauta);
        if (astronauta == null) {
            System.out.println("[ERRO] Astronauta nao encontrado: " + idAstronauta);
            return;
        }
        astronauta.setPosicaoAtual(idPosicao);
        System.out.println("[OK] Posicao do astronauta " + astronauta.getNome() + " atualizada.");
    }
}
