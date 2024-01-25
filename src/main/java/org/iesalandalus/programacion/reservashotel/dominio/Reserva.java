package org.iesalandalus.programacion.reservashotel.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reserva {
    private static final int MAX_NUMERO_MESES_RESERVA = 2;
    private static final int MAX_HORAS_POSTERIOR_CHECKOUT = 12;
    private static final String FORMATO_FECHA_RESERVA = "dd/MM/yyyy";

    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double precio;
    private int numeroPersonas;

    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen, LocalDate fechaInicioReserva, LocalDate fechaFinReserva, int numeroPersonas) {
        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
        setPrecio();
    }

    public Reserva(Reserva reserva) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No es posible copiar una reserva nula.");
        }
        setHuesped(new Huesped(reserva.getHuesped()));
        setHabitacion(new Habitacion(reserva.getHabitacion()));
        setRegimen(reserva.getRegimen());
        setFechaInicioReserva(reserva.getFechaInicioReserva());
        setFechaFinReserva(reserva.getFechaFinReserva());
        setNumeroPersonas(reserva.getNumeroPersonas());
        setPrecio();
    }

    public Reserva(Huesped huesped, Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin, Regimen regimen) {
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: El huésped de una reserva no puede ser nulo.");
        }
        this.huesped = huesped;
    }

    public TipoHabitacion getHabitacion() {
        return habitacion.getTipoHabitacion();
    }

    public void setHabitacion(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: La habitación de una reserva no puede ser nula.");
        }
        this.habitacion = habitacion;
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        if (regimen == null) {
            throw new NullPointerException("ERROR: El régimen de una reserva no puede ser nulo.");
        }
        this.regimen = regimen;
    }

    public LocalDate getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(LocalDate fechaInicioReserva) {
        if (fechaInicioReserva == null) {
            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        }
        if (fechaInicioReserva.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("ERROR: La fecha de inicio de una reserva no puede ser anterior al día que se intenta hacer la reserva.");
        }
        if (fechaInicioReserva.isAfter(LocalDate.now().plusMonths(MAX_NUMERO_MESES_RESERVA))) {
            throw new IllegalArgumentException("ERROR: La fecha de inicio de una reserva no puede ser posterior al número de meses indicado por la constante MAX_NUMERO_MESES_RESERVA.");
        }
        this.fechaInicioReserva = fechaInicioReserva;
    }

    public LocalDate getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(LocalDate fechaFinReserva) {
        if (fechaFinReserva == null) {
            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");
        }
        if (fechaFinReserva.isBefore(fechaInicioReserva)) {
            throw new IllegalArgumentException("ERROR: La fecha de fin de una reserva debe ser posterior a la de inicio.");
        }
        this.fechaFinReserva = fechaFinReserva;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        if (checkIn == null) {
            throw new NullPointerException("ERROR: El checkin de una reserva no puede ser nulo.");
        }
        if (checkIn.isBefore(fechaInicioReserva.atStartOfDay())) {
            throw new IllegalArgumentException("ERROR: El checkin no puede ser anterior al inicio de la reserva.");
        }
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        if (checkOut == null) {
            throw new NullPointerException("ERROR: El checkout de una reserva no puede ser nulo.");
        }
        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("ERROR: El checkout no puede ser anterior al checkin.");
        }
        if (checkOut.isAfter(fechaFinReserva.atStartOfDay().plusHours(MAX_HORAS_POSTERIOR_CHECKOUT))) {
            throw new IllegalArgumentException("ERROR: El checkout debe hacerse como máximo a las 12:00 horas del día en que finaliza la reserva.");
        }
        this.checkOut = checkOut;
    }

    public double getPrecio() {
        return precio;
    }

    private void setPrecio() {
        long dias = ChronoUnit.DAYS.between(fechaInicioReserva, fechaFinReserva);
        this.precio = dias * (habitacion.getPrecio() + regimen.getIncrementoPrecio() * numeroPersonas);
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        if (numeroPersonas < 1 || numeroPersonas > habitacion.getTipoHabitacion().getNumeroMaximoPersonas()) {
            throw new IllegalArgumentException("ERROR: El número de personas que se van a alojar en la habitación no puede superar al número máximo de personas que, por el tipo de habitación reservada, se permiten alojar.");
        }
        this.numeroPersonas = numeroPersonas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, fechaInicioReserva);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Reserva other = (Reserva) obj;
        return Objects.equals(habitacion, other.habitacion) && Objects.equals(fechaInicioReserva, other.fechaInicioReserva);
    }

    @Override
    public String toString() {
        String checkInStr = checkIn == null ? "No registrado" : checkIn.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA));
        String checkOutStr = checkOut == null ? "No registrado" : checkOut.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA));
        return String.format("Huesped: %s %s Habitación:%s - %s Fecha Inicio Reserva: %s Fecha Fin Reserva: %s Checkin: %s Checkout: %s Precio: %.2f Personas: %d",
                huesped.getNombre(), huesped.getDni(), habitacion.getIdentificador(), habitacion.getTipoHabitacion(),
                fechaInicioReserva.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)),
                fechaFinReserva.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)),
                checkInStr, checkOutStr, precio, numeroPersonas);
    }


    public Object getTipoHabitacion() {
        return habitacion.getTipoHabitacion();
    }
}

