/**
 * Clase que representa una transacción bancaria.
 *
 * <p>Esta clase almacena información sobre operaciones financieras como depósitos,
 * retiros o transferencias, incluyendo el tipo, monto y fecha/hora de la transacción.</p>
 *
 * @author [Nombre del autor]
 * @version 1.0
 * @since 2023
 */
package org.uniquindio.edu.co.poo.bancouq.model;

import java.time.LocalDateTime;

public class Transaccion {
    /**
     * Tipo de transacción (ej: "Depósito", "Retiro", "Transferencia")
     */
    private String tipo;

    /**
     * Cantidad de dinero involucrada en la transacción
     */
    private double monto;

    /**
     * Fecha y hora exacta en que se realizó la transacción
     */
    private LocalDateTime fecha;

    /**
     * Constructor que crea una nueva transacción con la fecha actual.
     *
     * @param tipo  Tipo de transacción (no debe ser nulo o vacío)
     * @param monto Valor monetario de la transacción (debe ser positivo)
     */
    public Transaccion(String tipo, double monto) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    /**
     * Obtiene el tipo de transacción.
     *
     * @return String que representa el tipo de transacción
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene el monto de la transacción.
     *
     * @return double con el valor monetario de la transacción
     */
    public double getMonto() {
        return monto;
    }

    /**
     * Obtiene la fecha y hora de la transacción.
     *
     * @return LocalDateTime con la fecha/hora exacta de la transacción
     */
    public LocalDateTime getFecha() {
        return fecha;
    }
}