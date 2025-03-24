package co.edu.uniquindio.poo.gestorcontactos.Repositorios;

import co.edu.uniquindio.poo.gestorcontactos.Model.Contacto;
import javafx.collections.ObservableList;

public interface RepositorioInterfaceUsuario {
    public void agregarContacto(Contacto contacto);
    public void eliminarContacto(Contacto contacto);
    public Contacto obtenerContactoTelefono(String telefono);
    public ObservableList<Contacto> obtenerContactosNombre(String nombre);
    public ObservableList<Contacto> obtenerContactos();
}
