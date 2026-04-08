package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.InMemoryPrestamooRepository;
import vallegrande.edu.pe.model.Prestamo;

import java.util.List;

public class PrestamoController {

    private InMemoryPrestamooRepository repo = new InMemoryPrestamooRepository();

    public void agregar(String libro, String usuario, String fecha) {
        repo.agregar(new Prestamo(libro, usuario, fecha));
    }

    public List<Prestamo> listar() {
        return repo.listar();
    }

    public void eliminar(int index) {
        repo.eliminar(index);
    }

    public void editar(int index, String libro, String usuario, String fecha) {
        repo.actualizar(index, new Prestamo(libro, usuario, fecha));
    }
}