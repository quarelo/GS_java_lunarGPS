package infrastructure;

import domain.Satelite;
import domain.enums.StatusSatelite;

import java.util.ArrayList;
import java.util.List;

public class SateliteRepositorio {

    private final List<Satelite> satelites = new ArrayList<>();
    private int contadorId = 1;

    public void salvar(Satelite satelite) {
        satelite.setId(contadorId++);
        satelites.add(satelite);
    }

    public Satelite buscarPorId(int id) {
        for (Satelite s : satelites) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public Satelite buscarPorCodigo(String codigo) {
        for (Satelite s : satelites) {
            if (s.getCodigoIdentificacao().equalsIgnoreCase(codigo)) return s;
        }
        return null;
    }

    public List<Satelite> listarTodos() {
        return satelites;
    }

    public List<Satelite> listarPorStatus(StatusSatelite status) {
        List<Satelite> resultado = new ArrayList<>();
        for (Satelite s : satelites) {
            if (s.getStatus() == status) resultado.add(s);
        }
        return resultado;
    }
}
