package infrastructure;

import domain.Obstaculo;
import domain.enums.NivelRisco;

import java.util.ArrayList;
import java.util.List;

public class ObstaculoRepositorio {

    private final List<Obstaculo> obstaculos = new ArrayList<>();
    private int contadorId = 1;

    public void salvar(Obstaculo obstaculo) {
        obstaculo.setId(contadorId++);
        obstaculos.add(obstaculo);
    }

    public Obstaculo buscarPorId(int id) {
        for (Obstaculo o : obstaculos) {
            if (o.getId() == id) return o;
        }
        return null;
    }

    public List<Obstaculo> listarTodos() {
        return obstaculos;
    }

    public List<Obstaculo> listarPorNivelRisco(NivelRisco nivel) {
        List<Obstaculo> resultado = new ArrayList<>();
        for (Obstaculo o : obstaculos) {
            if (o.getNivelRisco() == nivel) resultado.add(o);
        }
        return resultado;
    }

    public List<Obstaculo> listarNaoContornaveis() {
        List<Obstaculo> resultado = new ArrayList<>();
        for (Obstaculo o : obstaculos) {
            if (!o.isContornavel()) resultado.add(o);
        }
        return resultado;
    }
}
