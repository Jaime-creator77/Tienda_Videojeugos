package com.tienda.model;

/**
 * Representa un videojuego disponible en el inventario de la tienda.
 *
 * <p>Extiende {@link Item} e implementa {@link Vendible}.
 * El precio final de un videojuego incorpora el IVA cultural (4%)
 * y cualquier descuento temporal que se haya aplicado.</p>
 *
 * <ul>
 *   <li>IVA aplicado: <b>10%</b> (tipo reducido cultural)</li>
 *   <li>Descuentos acumulables con el precio base</li>
 * </ul>
 *
 * @author Alumno
 * @version 1.0.0
 */
public class Videojuego extends Item implements Vendible {

    /** Plataforma para la que está disponible el videojuego (ej: PC, PS5, Xbox). */
    private String plataforma;

    /** Género del videojuego (ej: Acción, RPG, Estrategia). */
    private String genero;

    /** Descuento aplicado al videojuego, entre 0.0 y 1.0. */
    private double descuento;

    /**
     * Constructor de la clase {@code Videojuego}.
     *
     * @param id         identificador único
     * @param nombre     título del videojuego
     * @param precio     precio base sin IVA en euros
     * @param stock      unidades disponibles en almacén
     * @param plataforma plataforma de juego (PS5, Xbox, PC, etc.)
     * @param genero     género del videojuego
     */
    public Videojuego(int id, String nombre, double precio, int stock,
                      String plataforma, String genero) {
        super(id, nombre, precio, stock);
        this.plataforma = plataforma;
        this.genero = genero;
        this.descuento = 0.0;
    }

    /**
     * Calcula el precio final del videojuego aplicando IVA y descuento.
     *
     * <p>La fórmula aplicada es:</p>
     * <pre>precioFinal = precioBase * (1 + IVA) * (1 - descuento)</pre>
     *
     * @return precio final en euros con dos decimales de precisión
     */
    @Override
    public double calcularPrecioFinal() {
        // RESOLUCION CONFLICTO: IVA 10% (hotfix) + fidelidad 5% (feature) — ambas mejoras son válidas
        double conIva = getPrecio() * 1.10;
        return conIva * (1 - descuento) * 0.95;
    }

    /**
     * Aplica un descuento porcentual al videojuego.
     *
     * <p>Si el porcentaje supera 1.0, se limita automáticamente a 1.0
     * para evitar precios negativos.</p>
     *
     * @param porcentaje descuento entre 0.0 (sin descuento) y 1.0 (gratis)
     */
    @Override
    public void aplicarDescuento(double porcentaje) {
        this.descuento = Math.min(porcentaje, 1.0);
    }

    /**
     * Devuelve la categoría comercial del videojuego.
     *
     * @return cadena {@code "Videojuego - "} seguida del género
     */
    @Override
    public String getCategoria() {
        return "Videojuego - " + genero;
    }

    /**
     * Devuelve la plataforma del videojuego.
     *
     * @return nombre de la plataforma
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * Devuelve el género del videojuego.
     *
     * @return género del juego
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Devuelve el descuento actualmente aplicado.
     *
     * @return valor del descuento entre 0.0 y 1.0
     */
    public double getDescuento() {
        return descuento;
    }

    /**
     * Representación textual del videojuego incluyendo plataforma y género.
     *
     * @return cadena con todos los datos relevantes del videojuego
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | %s | %s", plataforma, genero);
    }
}
