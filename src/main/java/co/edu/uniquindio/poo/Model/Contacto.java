package co.edu.uniquindio.poo.Model;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class Contacto {
    private String nombre;
    private String apellido;
    private String numeroTelefonico;
    private LocalDate fechaCumpleamos;
    private String correoElectronico;

}
