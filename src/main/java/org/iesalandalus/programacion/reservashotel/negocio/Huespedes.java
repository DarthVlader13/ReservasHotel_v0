package org.iesalandalus.programacion.reservashotel.negocio;

import org.iesalandalus.programacion.reservashotel.dominio.Huesped;

import java.util.Arrays;

public class Huespedes {
    private static final int MAX_NUMERO_HUESPEDES = 100;
    private Huesped[] coleccionHuespedes;
    private int capacidad;
    private int tamano;

    public Huespedes(int capacidad) {
        if (capacidad <= 0 || capacidad > MAX_NUMERO_HUESPEDES) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser un número entre 1 y " + MAX_NUMERO_HUESPEDES + ".");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionHuespedes = new Huesped[capacidad];
    }

    public Huesped[] get() {
        return copiaProfundaHuespedes();
    }

    private Huesped[] copiaProfundaHuespedes() {
        Huesped[] copia = new Huesped[tamano];
        for (int i = 0; i < tamano; i++) {
            copia[i] = new Huesped(coleccionHuespedes[i]);
        }
        return copia;
    }

    public void insertar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        int indice = buscarIndice(huesped);
        if (indice != -1) {
            throw new IllegalArgumentException("ERROR: Ya existe un huésped con ese DNI.");
        } else if (tamano == capacidad) {
            throw new IllegalArgumentException("ERROR: No se aceptan más huéspedes.");
        } else {
            coleccionHuespedes[tamano] = new Huesped(huesped);
            tamano++;
        }
    }

    private int buscarIndice(Huesped huesped) {
        int indice = -1;
        for (int i = 0; i < tamano; i++) {
            if (coleccionHuespedes[i].equals(huesped)) {
                indice = i;
            }
        }
        return indice;
    }

    public Huesped buscar(Huesped huesped) {
        int indice = buscarIndice(huesped);
        if (indice == -1) {
            return null;
        } else {
            return new Huesped(coleccionHuespedes[indice]);
        }
    }

    public void borrar(Huesped huesped) {
        int indice = buscarIndice(huesped);
        if (indice == -1) {
            throw new IllegalArgumentException("ERROR: No existe ningún huésped con ese DNI.");
        } else {
            desplazarUnaPosicionHaciaIzquierda(indice);
        }
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            coleccionHuespedes[i] = coleccionHuespedes[i + 1];
        }
        tamano--;
    }

    // Métodos equals y hashCode basados en el identificador
    @Override
    public int hashCode() {
        return Arrays.hashCode(coleccionHuespedes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Huespedes other = (Huespedes) obj;
        return Arrays.equals(coleccionHuespedes, other.coleccionHuespedes);
    }

    // Método toString para representar el objeto como una cadena
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamano; i++) {
            sb.append(coleccionHuespedes[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public Huesped[] getHuespedes() {
        return new Huesped[0];
    }
}

