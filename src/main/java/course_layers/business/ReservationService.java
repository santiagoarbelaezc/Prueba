// business/ReservationService.java
package course_layers.business;

import course_layers.entities.Reserva;

public class ReservationService {

    public boolean puedeCancelarReserva(int diasFaltantes, int tipo) {
        return diasFaltantes >= 3 && tipo == 2;
    }

    public String obtenerTipoTexto(int tipo) {
        return switch (tipo) {
            case 1 -> "Apartamento";
            case 2 -> "Habitación";
            case 3 -> "Casa";
            default -> "Desconocido";
        };
    }
}
