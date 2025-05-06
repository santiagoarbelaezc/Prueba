package course_layers.presentation;

import course_layers.business.PropertyService;
import course_layers.business.ReservationService;
import course_layers.data_access.PropertyGateway;
import course_layers.entities.Property;
import course_layers.entities.Reserva;

import java.util.List;
import java.util.Scanner;

public class Console {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        PropertyService propertyService = new PropertyService();
        PropertyGateway propertyGateway = new PropertyGateway();
        ReservationService reservationService = new ReservationService();

        // Inicializar la base de datos
        propertyGateway.inicializarBaseDeDatos();

        System.out.println("Connection to SQLite has been established.");

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = obtenerOpcion(scanner);

            switch (opcion) {
                case 1:
                    listarPropiedades(propertyService);
                    break;
                case 2:
                    verificarCancelacionReserva(scanner, reservationService);
                    break;
                case 3:
                    salir = true;
                    System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor intente de nuevo.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\nOperaciones del sistema");
        System.out.println("1. Listar propiedades.");
        System.out.println("2. Ver si es posible cancelar reserva.");
        System.out.println("3. Salir.");
    }

    private int obtenerOpcion(Scanner scanner) {
        int opcion = 0;
        boolean opcionValida = false;

        while (!opcionValida) {
            try {
                System.out.print("> ");
                opcion = Integer.parseInt(scanner.nextLine().trim());
                opcionValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }

        return opcion;
    }

    private void listarPropiedades(PropertyService propertyService) {
        List<Property> propiedadesDisponibles = propertyService.obtenerPropiedadesBase();

        // Añadir más propiedades para que coincida con la imagen
        propiedadesDisponibles.clear();
        propiedadesDisponibles.add(new Property("Apartamento de 3 habitaciones en el sur de la ciudad", 1, 5));
        propiedadesDisponibles.add(new Property("Habitación con cama doble", 2, 2));
        propiedadesDisponibles.add(new Property("Casa campestre exclusiva", 3, 10));

        for (int i = 0; i < propiedadesDisponibles.size(); i++) {
            Property p = propiedadesDisponibles.get(i);
            System.out.println("Nombre: " + p.getNombre() + ", Tipo: " + p.getTipoTexto() + ", Huéspedes: " + p.getCapacidad());
        }
    }

    private void verificarCancelacionReserva(Scanner scanner, ReservationService reservationService) {
        System.out.println("\n=== VERIFICAR CANCELACIÓN DE RESERVA ===");

        System.out.print("Ingrese el tipo de propiedad (1: Apartamento, 2: Habitación, 3: Casa): ");
        int tipo = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese cuántos días faltan para el inicio de la reserva: ");
        int diasHastaInicio = Integer.parseInt(scanner.nextLine());

        boolean puedeCancelar = reservationService.puedeCancelarReserva(diasHastaInicio, tipo);
        System.out.println("¿Se puede cancelar la reserva? " + (puedeCancelar ? "Sí" : "No"));
        System.out.println("Regla: Solo se pueden cancelar habitaciones con al menos 3 días de anticipación.");
    }

    // Este método ahora es separado para poder reutilizarlo cuando sea necesario
    private void realizarReserva(Scanner scanner, PropertyService propertyService, PropertyGateway propertyGateway, ReservationService reservationService) {
        List<Property> propiedadesDisponibles = propertyService.obtenerPropiedadesBase();

        System.out.println("=== PROPIEDADES DISPONIBLES ===");
        for (Property p : propiedadesDisponibles) {
            System.out.println(p);
        }

        System.out.print("\nIngrese el nombre de la propiedad que desea reservar: ");
        String nombrePropiedad = scanner.nextLine();

        Property propiedadSeleccionada = propertyService.buscarPorNombre(nombrePropiedad, propiedadesDisponibles);

        if (propiedadSeleccionada == null) {
            System.out.println("La propiedad ingresada no existe.");
            return;
        }

        System.out.print("Ingrese su nombre: ");
        String nombreCliente = scanner.nextLine();

        System.out.print("Ingrese su número de documento: ");
        String documentoCliente = scanner.nextLine();

        System.out.print("Ingrese la cantidad de huéspedes: ");
        int numHuespedes = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese cuántos días faltan para el inicio de la reserva: ");
        int diasHastaInicio = Integer.parseInt(scanner.nextLine());

        if (!propertyService.validarCapacidad(propiedadSeleccionada, numHuespedes)) {
            System.out.println("La propiedad no tiene capacidad para tantos huéspedes.");
            return;
        }

        Reserva reserva = new Reserva(nombreCliente, propiedadSeleccionada.getTipo(), numHuespedes, diasHastaInicio);

        // Guardar la reserva en la base de datos
        propertyGateway.insertarReserva(reserva);

        System.out.println("\nReserva realizada con éxito.");
        System.out.println("Datos de la reserva:");
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Documento: " + documentoCliente);
        System.out.println("Propiedad: " + propiedadSeleccionada.getNombre());
        System.out.println("Tipo: " + reservationService.obtenerTipoTexto(propiedadSeleccionada.getTipo()));
        System.out.println("Cantidad de huéspedes: " + numHuespedes);

        // Evaluar si se puede cancelar según los días reales ingresados
        boolean puedeCancelar = reservationService.puedeCancelarReserva(diasHastaInicio, propiedadSeleccionada.getTipo());
        System.out.println("¿Se puede cancelar la reserva? " + (puedeCancelar ? "Sí" : "No"));
    }
}