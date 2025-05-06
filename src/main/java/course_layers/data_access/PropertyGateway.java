// data_access/PropertyGateway.java
package course_layers.data_access;

import course_layers.entities.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyGateway {
    private static final String DB_URL = "jdbc:sqlite:reservas.db";

    public void inicializarBaseDeDatos() {
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

    public void insertarReserva(Reserva r) {
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

    public List<Reserva> listarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT nombre, tipo, num_huespedes, dias_hasta_inicio FROM reservas";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservas.add(new Reserva(
                        rs.getString("nombre"),
                        rs.getInt("tipo"),
                        rs.getInt("num_huespedes"),
                        rs.getInt("dias_hasta_inicio")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar reservas: " + e.getMessage());
        }
        return reservas;
    }
}
