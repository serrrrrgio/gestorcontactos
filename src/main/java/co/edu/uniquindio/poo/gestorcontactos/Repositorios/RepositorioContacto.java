package co.edu.uniquindio.poo.gestorcontactos.Repositorios;

import co.edu.uniquindio.poo.gestorcontactos.Model.Contacto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RepositorioContacto implements RepositorioInterfaceUsuario {

private ObservableList<Contacto> contactos;
    public RepositorioContacto() {
        this.contactos = FXCollections.observableArrayList();
    }

    @Override
    public void agregarContacto(Contacto contacto) {
        contactos.add(contacto);
        
    }

    @Override
    public void eliminarContacto(Contacto contacto) {
        contactos.remove(contacto);
        
    }

    @Override
    public Contacto obtenerContactoTelefono(String telefono) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObservableList<Contacto> obtenerContactos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObservableList<Contacto> obtenerContactosNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    
}

