module co.edu.uniquindio.poo.gestorcontactos {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.gestorcontactos to javafx.fxml;
    exports co.edu.uniquindio.poo.gestorcontactos;
}