package com.tienda.service;

import com.tienda.exception.ItemNoEncontradoException;
import com.tienda.model.Item;
import com.tienda.model.Vendible;

import java.util.List;

/**
 * Servicio dedicado a la gestión y aplicación de descuentos en la tienda.
 *
 * <p>Encapsula la lógica de negocio relacionada con promociones y rebajas,
 * permitiendo aplicar descuentos individuales o masivos sobre el inventario.</p>
 *
 * <p>Los métodos de esta clase trabajan sobre instancias de {@link Item}
 * que implementen la interfaz {@link Vendible}.</p>
 *
 * @author Alumno
 * @version 1.0.0
 */
public class GestorDescuentos {

    /** Referencia al inventario sobre el que se aplican los descuentos. */
    private final Inventario inventario;

    /**
     * Constructor que recibe el inventario a gestionar.
     *
     * @param inventario inventario de la tienda; no puede ser {@code null}
     */
    public GestorDescuentos(Inventario inventario) {
        this.inventario = inventario;
    }

    /**
     * Aplica una campaña de descuentos masiva a todos los ítems vendibles del inventario.
     *
     * <p>Solo afecta a ítems que implementen {@link Vendible}.
     * Los ítems no vendibles son ignorados silenciosamente.</p>
     *
     * @param porcentaje descuento global a aplicar entre 0.0 y 1.0
     * @throws IllegalArgumentException si el porcentaje es negativo o mayor que 1.0
     */
    public void aplicarDescuentoGlobal(double porcentaje) {
        List<Item> items = inventario.getTodosLosItems();
        int aplicados = 0;
        for (Item item : items) {
            if (item instanceof Vendible vendible) {
                vendible.aplicarDescuento(porcentaje);
                aplicados++;
            }
        }
        System.out.printf("✔ Descuento global de %.0f%% aplicado a %d ítem(s).%n",
                porcentaje * 100, aplicados);
    }

    /**
     * Calcula el precio final de un ítem sin modificar el inventario.
     *
     * <p>Útil para mostrar precios con descuento hipotético antes de confirmarlo.</p>
     *
     * @param id         identificador del ítem
     * @param porcentaje descuento hipotético entre 0.0 y 1.0
     * @return precio simulado con descuento aplicado
     * @throws ItemNoEncontradoException si el ID no existe en el inventario
     */
    public double simularPrecioConDescuento(int id, double porcentaje)
            throws ItemNoEncontradoException {
        Item item = inventario.buscarPorId(id);
        return item.calcularPrecioFinal() * (1 - porcentaje);
    }

    /**
     * Elimina todos los descuentos aplicados a los ítems vendibles del inventario.
     *
     * <p>Restablece el precio original de cada ítem llamando a
     * {@link Vendible#aplicarDescuento(double)} con valor {@code 0.0}.</p>
     */
    public void resetearDescuentos() {
        inventario.getTodosLosItems().stream()
                .filter(i -> i instanceof Vendible)
                .forEach(i -> ((Vendible) i).aplicarDescuento(0.0));
        System.out.println("✔ Todos los descuentos han sido eliminados.");
    }
}
// Actualizado para sistema de descuentos v2
