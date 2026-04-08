package vallegrande.edu.pe.model;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUsuarioRepository {

    private static final List<Usuario> lista = new ArrayList<>();

    public void agregar(Usuario usuario) {
        lista.add(usuario);
    }

    public List<Usuario> listar() {
        return lista;
    }

    public void eliminar(int index) {
        lista.remove(index);
    }

    public void actualizar(int index, Usuario usuario) {
        lista.set(index, usuario);
    }
}