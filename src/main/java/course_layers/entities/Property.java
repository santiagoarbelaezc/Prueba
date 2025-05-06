// entities/Property.java
package course_layers.entities;

public class Property {
    private String nombre;
    private int tipo;
    private int capacidad;

    public Property(String nombre, int tipo, int capacidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getTipoTexto() {
        return switch (tipo) {
            case 1 -> "Apartamento";
            case 2 -> "Habitación";
            case 3 -> "Casa";
            default -> "Desconocido";
        };
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Tipo: " + getTipoTexto() + ", Capacidad: " + capacidad;
    }
}
