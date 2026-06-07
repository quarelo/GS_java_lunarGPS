package application;

import domain.Satelite;
import domain.enums.StatusSatelite;
import infrastructure.SateliteRepositorio;

import java.util.List;

public class SateliteService {

    private final SateliteRepositorio repositorio = new SateliteRepositorio();

    public void cadastrar(Satelite satelite) {
        if (repositorio.buscarPorCodigo(satelite.getCodigoIdentificacao()) != null) {
            throw new IllegalArgumentException("Codigo ja cadastrado: " + satelite.getCodigoIdentificacao());
        }
        repositorio.salvar(satelite);
        System.out.println("[OK] Satelite cadastrado com ID: " + satelite.getId());
    }

    public Satelite buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    public List<Satelite> listarTodos() {
        return repositorio.listarTodos();
    }

    public List<Satelite> listarAtivos() {
        return repositorio.listarPorStatus(StatusSatelite.ATIVO);
    }

    public void atualizarStatus(int idSatelite, StatusSatelite novoStatus) {
        Satelite satelite = repositorio.buscarPorId(idSatelite);
        if (satelite == null) {
            System.out.println("[ERRO] Satelite nao encontrado: " + idSatelite);
            return;
        }
        satelite.setStatus(novoStatus);
        System.out.println("[OK] Status do satelite " + satelite.getNome() + " atualizado para " + novoStatus);
    }
}
