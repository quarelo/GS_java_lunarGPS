package infrastructure;

import domain.Missao;
import domain.enums.StatusMissao;

import java.util.ArrayList;
import java.util.List;

public class MissaoRepositorio {

    private final List<Missao> missoes = new ArrayList<>();
    private int contadorId = 1;

    public void salvar(Missao missao) {
        missao.setId(contadorId++);
        missoes.add(missao);
    }

    public Missao buscarPorId(int id) {
        for (Missao m : missoes) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    public List<Missao> listarTodos() {
        return missoes;
    }

    public List<Missao> listarPorStatus(StatusMissao status) {
        List<Missao> resultado = new ArrayList<>();
        for (Missao m : missoes) {
            if (m.getStatus() == status) resultado.add(m);
        }
        return resultado;
    }
}
