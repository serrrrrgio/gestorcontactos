package co.edu.uniquindio.poo.gestorcontactos.Controller;

import co.edu.uniquindio.poo.gestorcontactos.App;
import co.edu.uniquindio.poo.gestorcontactos.Model.Contacto;
import co.edu.uniquindio.poo.gestorcontactos.Model.TipoFiltro;
import co.edu.uniquindio.poo.gestorcontactos.Servicios.ContactoServicio;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.time.MonthDay;

public class InicioUsuarioController {

    @FXML
    private ChoiceBox<TipoFiltro> choiceFiltrar;

    @FXML
    private TextField txtFiltrar;

    @FXML
    private TableColumn<Contacto, String> tlcNombre;

    @FXML
    private TableColumn<Contacto, String> tlcTelefono;

    @FXML
    private TableColumn<Contacto, LocalDate> tlcCumpleanos;

    @FXML
    private TextField txtCorreo;

    @FXML
    private DatePicker dateCumpleanos;

    @FXML
    private TableView<Contacto> tblContactos;

    @FXML
    private TableColumn<Contacto, String> tlcApellido;

    @FXML
    private TableColumn<Contacto, String> tlcCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnAnadirFoto;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtApellido;

    @FXML
    private Button btnAnadir;

    @FXML
    private TableColumn<?, ?> tlcFoto;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnLimpiarCampos;

    @FXML
    private ImageView imagenContacto;

    private ContactoServicio contactoServicio;

    public void initialize() {
        contactoServicio = App.getContactoServicio();
        choiceFiltrar.setItems(FXCollections.observableArrayList(TipoFiltro.values()));
        configurarColumnasTabla();
        cargarContactos();
        agregarListenerTabla();
        txtFiltrar.textProperty().addListener((observable, oldValue, newValue) -> filtrarAutomaticamente(newValue));
    }

    private void configurarColumnasTabla() {
        tlcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tlcApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        tlcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tlcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tlcCumpleanos.setCellValueFactory(cellData -> {
            MonthDay monthDay = cellData.getValue().getFechaCumpleanos();
            LocalDate fechaCompleta = monthDay.atYear(LocalDate.now().getYear());
            return new SimpleObjectProperty<>(fechaCompleta);
        });
    }

    private void agregarListenerTabla() {
        tblContactos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtNombre.setText(newSelection.getNombre());
                txtApellido.setText(newSelection.getApellido());
                txtTelefono.setText(newSelection.getTelefono());
                txtCorreo.setText(newSelection.getCorreo());

                // Convertir MonthDay a LocalDate usando el año actual
                MonthDay monthDay = newSelection.getFechaCumpleanos();
                LocalDate fechaCompleta = monthDay.atYear(LocalDate.now().getYear());
                dateCumpleanos.setValue(fechaCompleta);
            }
        });
    }

    private void cargarContactos() {
        try {
            tblContactos.setItems(FXCollections.observableArrayList(contactoServicio.filtrarContacto(TipoFiltro.NOMBRE, "")));
        } catch (Exception e) {
            App.mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    void Registrar(ActionEvent event) {
        try {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            LocalDate fecha = dateCumpleanos.getValue();
            if (fecha == null) throw new Exception("Debe seleccionar una fecha de cumpleaños");

            contactoServicio.registrarContacto(nombre, apellido, telefono, correo, MonthDay.of(fecha.getMonth(), fecha.getDayOfMonth()));
            App.mostrarMensaje("Éxito", "Contacto registrado correctamente.");
            limpiarCampos();
            cargarContactos();
        } catch (Exception e) {
            App.mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    void Eliminar(ActionEvent event) {
        try {
            Contacto contactoSeleccionado = tblContactos.getSelectionModel().getSelectedItem();
            if (contactoSeleccionado == null) throw new Exception("Seleccione un contacto para eliminar");

            contactoServicio.eliminarContacto(contactoSeleccionado.getTelefono());
            App.mostrarMensaje("Éxito", "Contacto eliminado correctamente.");
            cargarContactos();
        } catch (Exception e) {
            App.mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    void Actualizar(ActionEvent event) {
        try {
            Contacto contactoSeleccionado = tblContactos.getSelectionModel().getSelectedItem();
            if (contactoSeleccionado == null) throw new Exception("Seleccione un contacto para actualizar");

            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String telefonoNuevo = txtTelefono.getText();
            String correo = txtCorreo.getText();
            LocalDate fecha = dateCumpleanos.getValue();
            if (fecha == null) throw new Exception("Debe seleccionar una fecha de cumpleaños");

            contactoServicio.actualizarContacto(contactoSeleccionado.getTelefono(), nombre, apellido, telefonoNuevo, correo, MonthDay.of(fecha.getMonth(), fecha.getDayOfMonth()));
            App.mostrarMensaje("Correcto", "Contacto actualizado correctamente.");
            limpiarCampos();
            cargarContactos();
        } catch (Exception e) {
            App.mostrarAlerta("Error", e.getMessage());
        }
    }

    private void filtrarAutomaticamente(String criterio) {
        try {
            TipoFiltro filtroSeleccionado = choiceFiltrar.getValue();

            if (filtroSeleccionado == null || criterio.trim().isEmpty()) {
                tblContactos.setItems(contactoServicio.obtenerTodosLosContactos());
                return;
            }

            if (filtroSeleccionado == TipoFiltro.TELEFONO && !criterio.matches("\\d*")) {
                return;
            }
            if (filtroSeleccionado == TipoFiltro.NOMBRE && !criterio.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                return;
            }

            ObservableList<Contacto> contactosFiltrados = contactoServicio.filtrarContacto(filtroSeleccionado, criterio);
            tblContactos.setItems(contactosFiltrados);

        } catch (Exception e) {
            App.mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    void LimpiarCampos(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        dateCumpleanos.setValue(null);
    }

    @FXML
    void perfilButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de perfil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", ".png", ".jpg", ".jpeg", ".gif")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            try {
                Image imagenSeleccionada = new Image(archivoSeleccionado.toURI().toString());
                imagenContacto.setImage(imagenSeleccionada);
            } catch (Exception e) {
                App.mostrarAlerta("No se pudo cargar la imagen seleccionada", "");
            }
        }
    }


}
