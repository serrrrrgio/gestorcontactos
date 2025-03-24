package co.edu.uniquindio.poo.gestorcontactos.Repositorios;

import co.edu.uniquindio.poo.gestorcontactos.Model.Contacto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
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
        for (Contacto contacto : contactos){
            if(contacto.getTelefono().equals(telefono)){
                return contacto;
            }
        }
       return null; 

    }

    @Override
    public ObservableList<Contacto> obtenerContactos() {
        return contactos;
    }

    @Override
    public ObservableList<Contacto> obtenerContactosNombre(String nombre) {
        ObservableList<Contacto> encontrados = FXCollections.observableArrayList();

        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre)) {
                encontrados.add(contacto);
            }
        }
    
        return encontrados;
    }
     
        
}
