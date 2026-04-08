package vallegrande.edu.pe.model;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLibroRepository {

    private static final List<Libro> lista = new ArrayList<>();

    public void agregar(Libro libro) {
        lista.add(libro);
    }

    public List<Libro> listar() {
        return lista;
    }

    public void eliminar(int index) {
        lista.remove(index);
    }

    public void actualizar(int index, Libro libro) {
        lista.set(index, libro);
    }
}