package org.iesalandalus.programacion.reservashotel.dominio;

public enum TipoHabitacion {
    SUITE("SUITE", 4),
    SIMPLE("SIMPLE", 1),
    DOBLE("DOBLE", 2),
    TRIPLE("TRIPLE", 3);

    private final String cadenaAMostrar;
    private final int numeroMaximoPersonas;

    // Constructor con la visibilidad adecuada
    TipoHabitacion(String cadenaAMostrar, int numeroMaximoPersonas) {
        this.cadenaAMostrar = cadenaAMostrar;
        this.numeroMaximoPersonas = numeroMaximoPersonas;
    }

    // Método getter
    public int getNumeroMaximoPersonas() {
        return numeroMaximoPersonas;
    }

    // Método toString que devuelve la cadena esperada por los tests
    @Override
    public String toString() {
        return cadenaAMostrar;
    }

    public Object getTipoHabitacion() {
        return null;
    }
}
