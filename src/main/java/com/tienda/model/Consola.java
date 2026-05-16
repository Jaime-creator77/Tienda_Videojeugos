package com.tienda.model;

/**
 * Representa una consola de videojuegos en el inventario de la tienda.
 *
 * <p>Las consolas son hardware y tributan un IVA general del <b>21%</b>,
 * a diferencia de los videojuegos que aplican IVA reducido cultural.</p>
 *
 * <p>Extiende {@link Item} e implementa {@link Vendible}.</p>
 *
 * @author Alumno
 * @version 1.0.0
 */
public class Consola extends Item implements Vendible {

    /** Fabricante de la consola (ej: Sony, Microsoft, Nintendo). */
    private String fabricante;

    /** Generación de la consola (ej: 9ª generación). */
    private String generacion;

    /** Descuento aplicado a la consola, entre 0.0 y 1.0. */
    private double descuento;

    /**
     * Constructor de la clase {@code Consola}.
     *
     * @param id         identificador único
     * @param nombre     nombre comercial de la consola (ej: PlayStation 5)
     * @param precio     precio base sin IVA en euros
     * @param stock      unidades disponibles en almacén
     * @param fabricante empresa fabricante de la consola
     * @param generacion generación a la que pertenece la consola
     */
    public Consola(int id, String nombre, double precio, int stock,
                   String fabricante, String generacion) {
        super(id, nombre, precio, stock);
        this.fabricante = fabricante;
        this.generacion = generacion;
        this.descuento = 0.0;
    }

    /**
     * Calcula el precio final de la consola aplicando IVA general y descuento.
     *
     * <p>Las consolas tributan al <b>21% de IVA</b> (bienes de equipo electrónico).
     * La fórmula es: {@code precioBase * 1.21 * (1 - descuento)}</p>
     *
     * @return precio final en euros
     */
    @Override
    public double calcularPrecioFinal() {
        return getPrecio() * 1.21 * (1 - descuento);
    }

    /**
     * Aplica un porcentaje de descuento a la consola.
     *
     * @param porcentaje descuento entre 0.0 y 1.0; se limita a 1.0 si se supera
     */
    @Override
    public void aplicarDescuento(double porcentaje) {
        this.descuento = Math.min(porcentaje, 1.0);
    }

    /**
     * Devuelve la categoría comercial de la consola.
     *
     * @return cadena {@code "Consola - "} seguida del fabricante
     */
    @Override
    public String getCategoria() {
        return "Consola - " + fabricante;
    }

    /**
     * Devuelve el fabricante de la consola.
     *
     * @return nombre del fabricante
     */
    public String getFabricante() {
        return fabricante;
    }

    /**
     * Devuelve la generación de la consola.
     *
     * @return generación de la consola
     */
    public String getGeneracion() {
        return generacion;
    }

    /**
     * Representación textual de la consola incluyendo fabricante y generación.
     *
     * @return cadena con todos los datos relevantes de la consola
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | %s | %s", fabricante, generacion);
    }
}
