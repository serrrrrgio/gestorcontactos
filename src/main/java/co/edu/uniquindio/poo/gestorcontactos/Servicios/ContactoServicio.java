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
        validarDatos(nombre, apellido, telefono, correo, cumpleanos);

        for (Contacto contacto : repositorioContacto.obtenerContactos()) {
            if (contacto.getTelefono().equals(telefono)) {
                throw new Exception("El contacto ya está registrado");
            }
        }

        Contacto contacto = Contacto.builder()
                .nombre(nombre)
                .apellido(apellido)
                .telefono(telefono)
                .correo(correo)
                .fechaCumpleanos(cumpleanos)
                .build();

        repositorioContacto.agregarContacto(contacto);
    }

    public void eliminarContacto(String telefono) throws Exception {
        Contacto contactoEliminar = repositorioContacto.obtenerContactoTelefono(telefono);
        if (contactoEliminar == null) {
            throw new Exception("Contacto no encontrado");
        }
        repositorioContacto.eliminarContacto(contactoEliminar);
    }

    public void actualizarContacto(String telefonoActualizar, String nombre, String apellido, String telefonoNuevo, String correo, MonthDay cumpleanos) throws Exception {
        validarDatos(nombre, apellido, telefonoNuevo, correo, cumpleanos);

        Contacto contactoExistente = repositorioContacto.obtenerContactoTelefono(telefonoActualizar);
        if (contactoExistente == null) {
            throw new Exception("Contacto no encontrado");
        }

        Contacto contactoActualizado = Contacto.builder()
                .nombre(nombre)
                .apellido(apellido)
                .telefono(telefonoNuevo)
                .correo(correo)
                .fechaCumpleanos(cumpleanos)
                .build();

        repositorioContacto.eliminarContacto(contactoExistente);
        repositorioContacto.agregarContacto(contactoActualizado);
    }

    public ObservableList<Contacto> obtenerTodosLosContactos() {
        return repositorioContacto.obtenerContactos();
    }

    private void validarDatos(String nombre, String apellido, String telefono, String correo, MonthDay cumpleanos) throws Exception {
        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty() || cumpleanos == null) {
            throw new Exception("Todos los campos son obligatorios");
        }

        if (telefono.length() != 10 || !telefono.startsWith("3") || !esNumero(telefono)) {
            throw new Exception("El teléfono debe tener exactamente 10 dígitos, empezar con '3' y contener solo números");
        }

        if (!correo.contains("@") || !correo.substring(correo.indexOf("@")).contains(".")) {
            throw new Exception("El correo debe contener un '@' y un '.' después del '@'");
        }

        if (!esCorreoValido(correo)) {
            throw new Exception("El correo contiene caracteres no permitidos");
        }
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

    private boolean esNumero(String telefono) {
        for (char caracter : telefono.toCharArray()) {
            if (!Character.isDigit(caracter)) {
                return false;
            }
        }
        return true;
    }



    public ObservableList<Contacto> filtrarContacto(TipoFiltro tipoBusqueda, String nombreTelefono) throws Exception {
        ObservableList<Contacto> contactosFiltrados = FXCollections.observableArrayList();
        switch (tipoBusqueda) {
            case NOMBRE:
                contactosFiltrados = obtenerContactosNombre(nombreTelefono);
                break;
            case TELEFONO:
                contactosFiltrados.add(buscarContactoTelefono(nombreTelefono));
                break;
        }
        return contactosFiltrados;
    }

    private ObservableList<Contacto> obtenerContactosNombre(String nombre) throws Exception {
        ObservableList<Contacto> contactosNombre = repositorioContacto.obtenerContactosNombre(nombre);
        if (contactosNombre.isEmpty()) {
            throw new Exception("El contacto no existe");
        }
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