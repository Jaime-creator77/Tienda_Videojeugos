package com.tienda.model;

/**
 * Interfaz que define el contrato para los ítems vendibles de la tienda.
 *
 * <p>Todo producto que pueda ser vendido al cliente debe implementar
 * esta interfaz, garantizando que expone la información de venta y
 * permite aplicar descuentos.</p>
 *
 * @author Alumno
 * @version 1.0.0
 */
public interface Vendible {

    /**
     * Aplica un descuento porcentual al ítem.
     *
     * <p>El descuento se expresa como un valor entre {@code 0.0} y {@code 1.0},
     * donde {@code 0.10} equivale al 10% de descuento.</p>
     *
     * @param porcentaje porcentaje de descuento a aplicar (entre 0.0 y 1.0)
     */
    void aplicarDescuento(double porcentaje);

    /**
     * Devuelve la categoría comercial del ítem vendible.
     *
     * @return cadena con el nombre de la categoría
     */
    String getCategoria();
}
