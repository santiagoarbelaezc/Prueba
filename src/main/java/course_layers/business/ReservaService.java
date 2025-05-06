// business/ReservaService.java
package course_layers.business;

import course_layers.entities.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservaService {

    private List<Reserva> reservas;

    public ReservaService() {
        this.reservas = new ArrayList<>();
    }

    public void guardar(Reserva reserva) {
        reservas.add(reserva);
    }

    public List<Reserva> listarTodas() {
        return reservas;
    }
}
