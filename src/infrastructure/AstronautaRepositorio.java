package infrastructure;

import domain.Astronauta;

import java.util.ArrayList;
import java.util.List;

public class AstronautaRepositorio {

    private final List<Astronauta> astronautas = new ArrayList<>();
    private int contadorId = 1;

    public void salvar(Astronauta astronauta) {
        astronauta.setId(contadorId++);
        astronautas.add(astronauta);
    }

    public Astronauta buscarPorId(int id) {
        for (Astronauta a : astronautas) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    public Astronauta buscarPorMatricula(String matricula) {
        for (Astronauta a : astronautas) {
            if (a.getMatricula().equalsIgnoreCase(matricula)) return a;
        }
        return null;
    }

    public List<Astronauta> listarTodos() {
        return astronautas;
    }
}
