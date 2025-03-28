package co.edu.uniquindio.poo.gestorcontactos;

import co.edu.uniquindio.poo.gestorcontactos.Repositorios.RepositorioContacto;
import co.edu.uniquindio.poo.gestorcontactos.Servicios.ContactoServicio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;

public class App extends Application {

    private static RepositorioContacto repositorioContacto;
    @Getter
    private static ContactoServicio contactoServicio;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/uniquindio/poo/gestorcontactos/View/inicioUsuario.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Gestor de Contactos");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        repositorioContacto = new RepositorioContacto();
        contactoServicio = new ContactoServicio(repositorioContacto);
        launch(args);
    }

    public static void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
