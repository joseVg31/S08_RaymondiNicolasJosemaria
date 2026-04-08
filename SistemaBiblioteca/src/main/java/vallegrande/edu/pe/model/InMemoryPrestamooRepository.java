package vallegrande.edu.pe.model;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPrestamooRepository {

    private static final List<Prestamo> lista = new ArrayList<>();

    public void agregar(Prestamo prestamo) {
        lista.add(prestamo);
    }

    public List<Prestamo> listar() {
        return lista;
    }

    public void eliminar(int index) {
        lista.remove(index);
    }

    public void actualizar(int index, Prestamo prestamo) {
        lista.set(index, prestamo);
    }
}