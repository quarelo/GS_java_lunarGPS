package infrastructure;

import domain.Posicao;

import java.util.ArrayList;
import java.util.List;

public class PosicaoRepositorio {

    private final List<Posicao> posicoes = new ArrayList<>();
    private int contadorId = 1;

    public void salvar(Posicao posicao) {
        posicao.setId(contadorId++);
        posicoes.add(posicao);
    }

    public Posicao buscarPorId(int id) {
        for (Posicao p : posicoes) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public List<Posicao> listarTodos() {
        return posicoes;
    }
}
