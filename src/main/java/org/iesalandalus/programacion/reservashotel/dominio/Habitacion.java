package org.iesalandalus.programacion.reservashotel.dominio;

import java.util.Objects;

public class Habitacion {
    // Constantes para los límites de planta, puerta y precio
    private static final int MIN_NUMERO_PLANTA = 1;
    private static final int MAX_NUMERO_PLANTA = 3;
    private static final int MIN_NUMERO_PUERTA = 1;
    private static final int MAX_NUMERO_PUERTA = 15;
    private static final double MIN_PRECIO_HABITACION = 40.0;
    private static final double MAX_PRECIO_HABITACION = 150.0;

    // Atributos de la clase Habitacion
    private String identificador;
    private int planta;
    private int puerta;
    private double precio;
    private TipoHabitacion tipoHabitacion;

    // Constructor con parámetros que utiliza los métodos set para asignar los valores a los atributos
    public Habitacion(int planta, int puerta, double precio, TipoHabitacion tipoHabitacion) {
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        this.tipoHabitacion = tipoHabitacion;
        setIdentificador();
    }

    public Habitacion(Habitacion habitacion) {
    }

    public Habitacion(int numeroPlanta, int numeroPuerta, TipoHabitacion tipoHabitacion) {
    }

    public Habitacion(TipoHabitacion habitacion) {
    }

    // Métodos get y set para cada atributo, con validaciones según las especificaciones
    public String getIdentificador() {
        return identificador;
    }

    private void setIdentificador() {
        this.identificador = String.format("%d-%d", planta, puerta);
    }

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta) {
        if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA) {
            throw new IllegalArgumentException("ERROR: La planta de una habitación debe ser un número entre " + MIN_NUMERO_PLANTA + " y " + MAX_NUMERO_PLANTA + ".");
        }
        this.planta = planta;
    }

    public int getPuerta() {
        return puerta;
    }

    public void setPuerta(int puerta) {
        if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA) {
            throw new IllegalArgumentException("ERROR: La puerta de una habitación debe ser un número entre " + MIN_NUMERO_PUERTA + " y " + MAX_NUMERO_PUERTA + ".");
        }
        this.puerta = puerta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION) {
            throw new IllegalArgumentException("ERROR: El precio de una habitación debe ser un número entre " + MIN_PRECIO_HABITACION + " y " + MAX_PRECIO_HABITACION + ".");
        }
        this.precio = precio;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null) {
            throw new NullPointerException("ERROR: El tipo de una habitación no puede ser nulo.");
        }
        this.tipoHabitacion = tipoHabitacion;
    }

    // Métodos equals y hashCode basados en el identificador
    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Habitacion other = (Habitacion) obj;
        return Objects.equals(identificador, other.identificador);
    }

    // Método toString para representar el objeto como una cadena
    @Override
    public String toString() {
        return String.format("identificador=%s, planta=%d, puerta=%d, precio=%.2f, tipo habitación=%s", identificador, planta, puerta, precio, tipoHabitacion);
    }
}
