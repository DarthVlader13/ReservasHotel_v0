package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.dominio.*;
import org.iesalandalus.programacion.reservashotel.negocio.Habitaciones;
import org.iesalandalus.programacion.reservashotel.negocio.Huespedes;
import org.iesalandalus.programacion.reservashotel.negocio.Reservas;
import org.iesalandalus.programacion.reservashotel.vista.Consola;
import org.iesalandalus.programacion.reservashotel.vista.Opcion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;

public class MainApp {

    private static final int CAPACIDAD = 100; // ajusta la capacidad según sea necesario
    private static final Reservas reservas = new Reservas(CAPACIDAD);
    private static final Habitaciones habitaciones = new Habitaciones(CAPACIDAD);
    private static final Huespedes huespedes = new Huespedes(CAPACIDAD);

    public static void main(String[] args) {
        Consola consola = new Consola();

        do {
            Consola.mostrarMenu();
            Opcion opcion = consola.elegirOpcion();
            ejecutarOpcion(opcion, consola);
        } while (true);
    }

    private static void ejecutarOpcion(Opcion opcion, Consola consola) {
        switch (opcion) {
            case INSERTAR_HUESPED:
                insertarHuesped(consola);
                break;
            case BUSCAR_HUESPED:
                buscarHuesped(consola);
                break;
            case BORRAR_HUESPED:
                borrarHuesped(consola);
                break;
            case MOSTRAR_HUESPEDES:
                mostrarHuespedes();
                break;
            case INSERTAR_HABITACION:
                insertarHabitacion(consola);
                break;
            case BUSCAR_HABITACION:
                buscarHabitacion(consola);
                break;
            case BORRAR_HABITACION:
                borrarHabitacion(consola);
                break;
            case MOSTRAR_HABITACIONES:
                mostrarHabitaciones();
                break;
            case INSERTAR_RESERVA:
                insertarReserva(consola);
                break;
            case MOSTRAR_RESERVAS:
                listarReservasHuesped(consola);
                break;
            case ANULAR_RESERVA:
                anularReserva(consola);
                break;
            case CONSULTAR_DISPONIBILIDAD:
                reservas.consultarDisponibilidad();
                break;
            case SALIR:
                System.out.println("Saliendo del programa.");
                System.exit(0);
                break;
        }
    }

    private static void anularReserva(Consola consola) {
    }

    private static void mostrarReservas() {
    }

    private static void insertarHuesped(Consola consola) {
        try {
            Huesped nuevoHuesped = consola.leerHuesped();
            huespedes.insertar(nuevoHuesped);
            System.out.println("Huésped insertado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void buscarHuesped(Consola consola) {
        try {
            Huesped huespedBuscado = consola.getHuespedPorDni();
            Huesped encontrado = huespedes.buscar(huespedBuscado);
            if (encontrado != null) {
                System.out.println("Huesped encontrado: " + encontrado);
            } else {
                System.out.println("Huesped no encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void borrarHuesped(Consola consola) {
        try {
            Huesped huespedBorrar = consola.getHuespedPorDni();
            huespedes.borrar(huespedBorrar);
            System.out.println("Huésped borrado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void mostrarHuespedes() {
        Huesped[] arrayHuespedes = huespedes.getHuespedes();
        if (arrayHuespedes.length > 0) {
            for (Huesped huesped : arrayHuespedes) {
                System.out.println(huesped);
            }
        } else {
            System.out.println("No hay huéspedes.");
        }
    }

    private static void insertarHabitacion(Consola consola) {
        try {
            Habitacion nuevaHabitacion = consola.leerHabitacion();
            habitaciones.insertar(nuevaHabitacion);
            System.out.println("Habitación insertada correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void buscarHabitacion(Consola consola) {
        try {
            Habitacion habitacionBuscada = consola.leerHabitacionPorIdentificador();
            Habitacion encontrada = habitaciones.buscar(habitacionBuscada);
            if (encontrada != null) {
                System.out.println("Habitación encontrada: " + encontrada);
            } else {
                System.out.println("Habitación no encontrada.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void borrarHabitacion(Consola consola) {
        try {
            Habitacion habitacionBorrar = consola.leerHabitacionPorIdentificador();
            habitaciones.borrar(habitacionBorrar);
            System.out.println("Habitación borrada correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void mostrarHabitaciones() {
        Habitacion[] arrayHabitaciones = habitaciones.getHabitaciones();
        if (arrayHabitaciones.length > 0) {
            for (Habitacion habitacion : arrayHabitaciones) {
                System.out.println(habitacion);
            }
        } else {
            System.out.println("No hay habitaciones.");
        }
    }

    private static void insertarReserva(Consola consola) {
        try {
            Reserva nuevaReserva = consola.leerReserva();
            if (reservas.desplazarUnaPosicionHaciaIzquierda(nuevaReserva.getTipoHabitacion(), nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva())) {
                reservas.insertar(nuevaReserva);
                System.out.println("Reserva insertada correctamente.");
            } else {
                System.out.println("No hay disponibilidad para la habitación y fechas indicadas.");
            }
        } catch (OperationNotSupportedException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarReservasHuesped(Consola consola) {
        try {
            Huesped huesped = consola.getHuespedPorDni();
            Reserva[] reservasHuesped = reservas.getReservas(huesped);
            mostrarListaReservas(reservasHuesped);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarReservasTipoHabitacion(Consola consola) {
        TipoHabitacion tipoHabitacion = consola.leerTipoHabitacion();
        Reserva[] reservasTipoHabitacion = reservas.getReservas(tipoHabitacion);
        mostrarListaReservas(reservasTipoHabitacion);
    }

    private static void mostrarListaReservas(Reserva[] reservas) {
        if (reservas.length > 0) {
            for (int i = 0; i < reservas.length; i++) {
                System.out.println((i + 1) + ". " + reservas[i]);
            }
        } else {
            System.out.println("No hay reservas.");
        }
    }

}
