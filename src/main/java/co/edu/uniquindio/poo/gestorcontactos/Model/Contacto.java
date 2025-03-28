package co.edu.uniquindio.poo.gestorcontactos.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.MonthDay;

import javafx.scene.image.Image;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;
    String correo;
    private MonthDay fechaCumpleanos;
    private Image imagen;

}
