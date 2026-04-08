package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.InMemoryUsuarioRepository;
import vallegrande.edu.pe.model.Usuario;

import java.util.List;

public class UsuarioController {

    private InMemoryUsuarioRepository repo = new InMemoryUsuarioRepository();

    public void agregar(String nombre, String apellido, String email, String telefono) {
        repo.agregar(new Usuario(nombre, apellido, email, telefono));
    }

    public List<Usuario> listar() {
        return repo.listar();
    }

    public void eliminar(int index) {
        repo.eliminar(index);
    }

    public void editar(int index, String nombre, String apellido, String email, String telefono) {
        repo.actualizar(index, new Usuario(nombre, apellido, email, telefono));
    }
}
