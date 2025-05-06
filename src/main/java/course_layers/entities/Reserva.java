// entities/Reserva.java
package course_layers.entities;

public class Reserva {
    private String nombre;
    private int tipo;
    private int numHuespedes;
    private int diasHastaInicio;

    public Reserva(String nombre, int tipo, int numHuespedes, int diasHastaInicio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.numHuespedes = numHuespedes;
        this.diasHastaInicio = diasHastaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public int getNumHuespedes() {
        return numHuespedes;
    }

    public int getDiasHastaInicio() {
        return diasHastaInicio;
    }
}
