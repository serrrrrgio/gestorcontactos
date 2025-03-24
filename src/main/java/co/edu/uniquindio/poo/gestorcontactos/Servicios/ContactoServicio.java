package co.edu.uniquindio.poo.gestorcontactos.Servicios;

import co.edu.uniquindio.poo.gestorcontactos.Model.Contacto;
import co.edu.uniquindio.poo.gestorcontactos.Model.TipoFiltro;
import co.edu.uniquindio.poo.gestorcontactos.Repositorios.RepositorioInterfaceUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.MonthDay;

public class ContactoServicio {

    private final RepositorioInterfaceUsuario repositorioContacto;

    public ContactoServicio(RepositorioInterfaceUsuario repositorioContacto) {
        this.repositorioContacto = repositorioContacto;
    }

    public void registrarContacto(String nombre, String apellido, String telefono, String correo,
                                  MonthDay cumpleanos) throws Exception {
        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty() || cumpleanos == null) {
            throw new Exception("Todos los campos son obligatorios");
        }

        if (telefono.length() != 10) {
            throw new Exception("El teléfono debe tener exactamente 10 dígitos");
        }

        if (!telefono.startsWith("3")) {
            throw new Exception("El teléfono debe empezar con el número 3");
        }

        if (!esNumero(telefono)) {
            throw new Exception("El teléfono solo debe contener números");
        }

        if (!correo.contains("@") || !correo.substring(correo.indexOf("@")).contains(".")) {
            throw new Exception("El correo debe contener un '@' y un '.' después del '@'");
        }

        if (!esCorreoValido(correo)) {
            throw new Exception("El correo contiene caracteres no permitidos");
        }

        for (Contacto contacto : repositorioContacto.obtenerContactos()) {
            if (contacto.getTelefono().equals(telefono)) {
                throw new Exception("El contacto ya esta registrado");
            }
        }

        Contacto contacto = new Contacto(nombre, apellido, telefono, correo, cumpleanos);
        repositorioContacto.agregarContacto(contacto);
    }

    private boolean esCorreoValido(String correo) {
        for (char caracter : correo.toCharArray()) {
            if (!(Character.isLetterOrDigit(caracter) || caracter == '.' || caracter == '@' ||
                    caracter == '_' || caracter == '-' || caracter == '+' || caracter == '%')) {
                return false;
            }
        }
        return true;
    }

    public void eliminarContacto(String telefono) throws Exception {
        Contacto contactoEliminar = repositorioContacto.obtenerContactoTelefono(telefono);
        if (contactoEliminar == null) {
            throw new Exception("Contacto no encontrado");
        }
        repositorioContacto.eliminarContacto(contactoEliminar);
    }

    public void actualizarContacto(String telefonoActualizar, String nombre, String apellido, String telefonoNuevo, String correo, MonthDay cumpleanos) throws Exception {
        Contacto contactoActualizar = repositorioContacto.obtenerContactoTelefono(telefonoActualizar);

        if (nombre.isEmpty() || apellido.isEmpty() || telefonoNuevo.isEmpty() || correo.isEmpty() || cumpleanos == null) {
            throw new Exception("Todos los campos son obligatorrios");
        }

        if (telefonoNuevo.length() != 10) {
            throw new Exception("El teléfono debe tener exactamente 10 dígitos");
        }

        if (!telefonoNuevo.startsWith("3")) {
            throw new Exception("El teléfono debe empezar con el número 3");
        }

        if (!esNumero(telefonoNuevo)) {
            throw new Exception("El teléfono solo debe contener números");
        }


        if (contactoActualizar == null) {
            throw new Exception("Contacto no encontrado");
        }
        contactoActualizar.setNombre(nombre);
        contactoActualizar.setApellido(apellido);
        contactoActualizar.setTelefono(telefonoNuevo);
        contactoActualizar.setCorreo(correo);
        contactoActualizar.setFechaCumplanos(cumpleanos);
    }

    private boolean esNumero(String telefono) {
        for (char caracter : telefono.toCharArray()) {
            if (!Character.isDigit(caracter)) {
                return false;
            }
        }
        return true;
    }

    private ObservableList<Contacto> filtrarContacto(TipoFiltro tipoBusqueda, String nombretelefono) throws Exception {
        ObservableList<Contacto> contactosFiltrados = FXCollections.observableArrayList();;
        switch (tipoBusqueda) {
            case NOMBRE:
                contactosFiltrados = obtenerContactosNombre(nombretelefono);
                break;
            case TELEFONO:
                contactosFiltrados.add(buscarContactoTelefono(nombretelefono));
                break;
        }
        return contactosFiltrados;
    }


    private ObservableList<Contacto> obtenerContactosNombre(String nombre) throws Exception {
        ObservableList<Contacto> contactosNombre = repositorioContacto.obtenerContactosNombre(nombre);
        if(contactosNombre.isEmpty()) {throw new Exception("El contacto no existe");}
        return contactosNombre;
    }

    private Contacto buscarContactoTelefono(String telefono) throws Exception {
        Contacto contactoBuscado = repositorioContacto.obtenerContactoTelefono(telefono);
        if (contactoBuscado == null) {
            throw new Exception("El contacto no existe");
        }
        return contactoBuscado;
    }
}


