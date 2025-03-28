package co.edu.uniquindio.poo.gestorcontactos.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class InicioUsuarioController {

    @FXML
    private TableColumn<?, ?> lblNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TableColumn<?, ?> lblApellido;

    @FXML
    private TableColumn<?, ?> lblTelefono;

    @FXML
    private TableColumn<?, ?> lblCumpleanos;

    @FXML
    private TableView<?> tblContactos;

    @FXML
    private TextField txtNombre;

    @FXML
    private TableColumn<?, ?> lblCorreo;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnAnadirFoto;

    @FXML
    private TextField txtTelefono;

    @FXML
    private DatePicker dateFechaCumplanos;

    @FXML
    private TextField txtApellido;

    @FXML
    private Button btnAnadir;

    @FXML
    private Button btnActualizar;

}

