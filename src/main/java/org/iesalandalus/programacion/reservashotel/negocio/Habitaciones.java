package org.iesalandalus.programacion.reservashotel.negocio;

import org.iesalandalus.programacion.reservashotel.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.dominio.TipoHabitacion;

import java.util.ArrayList;
import java.util.List;

public class Habitaciones {
    private int capacidad;
    private int tamano;
    private List<Habitacion> habitaciones;

    public Habitaciones(int capacidad) {
        this.capacidad = capacidad;
        this.tamano = 0;
        this.habitaciones = new ArrayList<>(capacidad);
    }

    public List<Habitacion> get() {
        return copiaProfundaHabitaciones();
    }

    private List<Habitacion> copiaProfundaHabitaciones() {
        List<Habitacion> copia = new ArrayList<>(tamano);
        for (Habitacion habitacion : habitaciones) {
            copia.add(new Habitacion(habitacion));
        }
        return copia;
    }

    public List<Habitacion> get(TipoHabitacion tipo) {
        List<Habitacion> copia = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipoHabitacion().equals(tipo)) {
                copia.add(new Habitacion(habitacion));
            }
        }
        return copia;
    }

    public int getTamano() {
        return tamano;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void insertar(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if (buscar(habitacion) != null) {
            throw new IllegalArgumentException("ERROR: Ya existe una habitación con ese identificador.");
        }
        if (tamano == capacidad) {
            throw new IllegalArgumentException("ERROR: No se aceptan más habitaciones.");
        }
        habitaciones.add(new Habitacion(habitacion));
        tamano++;
    }

    public Habitacion buscar(Habitacion habitacion) {
        int indice = habitaciones.indexOf(habitacion);
        if (indice != -1) {
            return new Habitacion(habitaciones.get(indice));
        } else {
            return null;
        }
    }

    public void borrar(Habitacion habitacion) {
        if (buscar(habitacion) == null) {
            throw new IllegalArgumentException("ERROR: No existe ninguna habitación con ese identificador.");
        }
        habitaciones.remove(habitacion);
        tamano--;
    }

    public Habitacion[] getHabitaciones() {
        return new Habitacion[0];
    }
}