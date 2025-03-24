package co.edu.uniquindio.poo.gestorcontactos.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.MonthDay;

@Setter
@Getter
@AllArgsConstructor
public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;String correo;
    private MonthDay fechaCumplanos;
}
