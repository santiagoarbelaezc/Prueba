package org.example;

import java.sql.*;
import java.util.Scanner;

public class SistemaReservas {
    private static final String DB_URL = "jdbc:sqlite:reservas.db";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarBaseDeDatos();
        inicializarReservas();

        int opcion;
        do {
            mostrarMenu();
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    listarPropiedades();
                    break;
                case 2:
                    verificarCancelacion();
                    break;
                case 3:
                    System.out.println("¡Gracias por usar el sistema! Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 3);
    }

    private static void mostrarMenu() {
        System.out.println("\nOperaciones del sistema");
        System.out.println("1. Listar propiedades.");
        System.out.println("2. Ver si es posible cancelar reserva.");
        System.out.println("3. Salir.");
        System.out.print("> ");
    }

    private static void inicializarBaseDeDatos() {
        String sql = """
                CREATE TABLE IF NOT EXISTS reservas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    tipo INTEGER NOT NULL,
                    num_huespedes INTEGER NOT NULL,
                    dias_hasta_inicio INTEGER NOT NULL
                );
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creando base de datos: " + e.getMessage());
        }
    }

    private static void inicializarReservas() {
        insertarReserva(new Reserva("Apartamento de 3 habitaciones en el sur de la ciudad", 1, 5, 7));
        insertarReserva(new Reserva("Habitación con cama doble", 2, 2, 2));
        insertarReserva(new Reserva("Casa campestre exclusiva", 3, 10, 5));
    }

    private static void insertarReserva(Reserva r) {
        String sql = "INSERT INTO reservas(nombre, tipo, num_huespedes, dias_hasta_inicio) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, r.getNombre());
            pstmt.setInt(2, r.getTipo());
            pstmt.setInt(3, r.getNumHuespedes());
            pstmt.setInt(4, r.getDiasHastaInicio());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error insertando reserva: " + e.getMessage());
        }
    }

    private static void listarPropiedades() {
        String sql = "SELECT nombre, tipo, num_huespedes FROM reservas";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== LISTADO DE PROPIEDADES ===");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int tipo = rs.getInt("tipo");
                int numHuespedes = rs.getInt("num_huespedes");

                System.out.println("Nombre: " + nombre +
                        ", Tipo: " + obtenerTipoTexto(tipo) +
                        ", Huéspedes: " + numHuespedes);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar propiedades: " + e.getMessage());
        }
    }

    private static void verificarCancelacion() {
        System.out.println("\n=== VERIFICAR CANCELACIÓN DE RESERVA ===");

        System.out.println("¿Cuántos días faltan para el inicio de la reserva?");
        System.out.print("> ");
        int diasFaltantes = Integer.parseInt(scanner.nextLine());

        System.out.println("¿Qué tipo de propiedad es? (1 = Apartamento, 2 = Habitación, 3 = Casa)");
        System.out.print("> ");
        int tipo = Integer.parseInt(scanner.nextLine());

        if (diasFaltantes < 3) {
            System.out.println("No se puede cancelar la reserva");
            System.out.println("No se puede cancelar si los días que faltan son menor a 3");
        } else if (tipo == 1 || tipo == 3) {
            System.out.println("No se puede cancelar la reserva");
            System.out.println("No se pueden cancelar si el tipo es Apartamento o Casa");
        } else {
            System.out.println("Es posible cancelar la reserva.");
        }
    }

    private static String obtenerTipoTexto(int tipo) {
        return switch (tipo) {
            case 1 -> "Apartamento";
            case 2 -> "Habitación";
            case 3 -> "Casa";
            default -> "Desconocido";
        };
    }

    // Clase interna para representar una reserva
    static class Reserva {
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
}
