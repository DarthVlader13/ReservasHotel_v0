package org.iesalandalus.programacion.reservashotel.negocio;

import org.iesalandalus.programacion.reservashotel.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Arrays;

public class Reservas {

    private int capacidad;
    private int tamano;
    private Reserva[] reservas;

    public Reservas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.reservas = new Reserva[capacidad];
    }

    public Reserva[] get() {
        return copiaProfundaReservas();
    }

    public int getTamano() {
        return tamano;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }

        if (tamanoSuperado(1) || capacidadSuperada(1) || buscar(reserva) != null) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más reservas.");
        }

        reservas[tamano++] = reserva;
    }

    public Reserva buscar(Reserva reserva) {
        int indice = buscarIndice(reserva);
        if (indice != -1) {
            return reservas[indice];
        }
        return null;
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }

        int indice = buscarIndice(reserva);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }

        desplazarUnaPosicionHaciaIzquierda(indice);
        tamano--;
    }



    public Reserva[] getReservas(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }

        Reserva[] reservasHuesped = new Reserva[tamano];
        int contador = 0;

        for (int i = 0; i < tamano; i++) {
            if (reservas[i].getHuesped().equals(huesped)) {
                reservasHuesped[contador++] = reservas[i];
            }
        }

        return Arrays.copyOf(reservasHuesped, contador);
    }

    public Reserva[] getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }

        Reserva[] reservasTipo = new Reserva[tamano];
        int contador = 0;

        for (int i = 0; i < tamano; i++) {
            if (reservas[i].getHabitacion().getTipoHabitacion().equals(tipoHabitacion)) {
                reservasTipo[contador++] = reservas[i];
            }
        }

        return Arrays.copyOf(reservasTipo, contador);
    }

    public Reserva[] getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }

        Reserva[] reservasFuturas = new Reserva[tamano];
        int contador = 0;

        for (int i = 0; i < tamano; i++) {
            if (reservas[i].getHabitacion().equals(habitacion) && reservas[i].getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasFuturas[contador++] = reservas[i];
            }
        }

        return Arrays.copyOf(reservasFuturas, contador);
    }

    private Reserva[] copiaProfundaReservas() {
        Reserva[] copia = new Reserva[capacidad];
        for (int i = 0; i < tamano; i++) {
            copia[i] = new Reserva(reservas[i]);
        }
        return copia;
    }

    private int buscarIndice(Reserva reserva) {
        for (int i = 0; i < tamano; i++) {
            if (reservas[i].equals(reserva)) {
                return i;
            }
        }
        return -1;
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            reservas[i] = reservas[i + 1];
        }
        reservas[tamano - 1] = null;
    }

    private boolean tamanoSuperado(int elementos) {
        return tamano + elementos > capacidad;
    }

    private boolean capacidadSuperada(int elementos) {
        return tamano + elementos > reservas.length;
    }

    public boolean desplazarUnaPosicionHaciaIzquierda(Object tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva) {
        return false;
    }

    public void consultarDisponibilidad() {
    }
}

