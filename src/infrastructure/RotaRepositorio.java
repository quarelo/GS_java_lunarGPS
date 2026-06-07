package infrastructure;

import domain.Rota;
import domain.enums.StatusRota;

import java.util.ArrayList;
import java.util.List;

public class RotaRepositorio {

    private final List<Rota> rotas = new ArrayList<>();
    private int contadorId = 1;

    public void salvar(Rota rota) {
        rota.setId(contadorId++);
        rotas.add(rota);
    }

    public Rota buscarPorId(int id) {
        for (Rota r : rotas) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    public List<Rota> listarTodos() {
        return rotas;
    }

    public List<Rota> listarPorStatus(StatusRota status) {
        List<Rota> resultado = new ArrayList<>();
        for (Rota r : rotas) {
            if (r.getStatus() == status) resultado.add(r);
        }
        return resultado;
    }
}
