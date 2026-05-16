package com.tienda.model;

/**
 * Clase abstracta que representa un ítem genérico de la tienda.
 *
 * <p>Sirve como base para todos los productos que pueden ser almacenados
 * y gestionados en el inventario de la tienda de videojuegos.</p>
 *
 * @author Alumno
 * @version 1.0.0
 */
public abstract class Item {

    /** Identificador único del ítem. */
    private int id;

    /** Nombre del ítem. */
    private String nombre;

    /** Precio base del ítem en euros. */
    private double precio;

    /** Cantidad disponible en stock. */
    private int stock;

    /**
     * Constructor de la clase {@code Item}.
     *
     * @param id     identificador único del ítem
     * @param nombre nombre descriptivo del ítem
     * @param precio precio base en euros (debe ser &gt; 0)
     * @param stock  cantidad inicial en inventario (debe ser &ge; 0)
     */
    public Item(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Devuelve el identificador único del ítem.
     *
     * @return el {@code id} del ítem
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el nombre del ítem.
     *
     * @return el nombre del ítem
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del ítem.
     *
     * @param nombre nuevo nombre del ítem
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el precio base del ítem.
     *
     * @return precio en euros
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio base del ítem.
     *
     * @param precio nuevo precio en euros (debe ser &gt; 0)
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve el stock disponible del ítem.
     *
     * @return cantidad en inventario
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock disponible del ítem.
     *
     * @param stock nueva cantidad en inventario (debe ser &ge; 0)
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Calcula el precio final del ítem aplicando lógica específica de cada subclase.
     *
     * <p>Las subclases deben implementar este método para aplicar
     * descuentos, impuestos u otras modificaciones al precio base.</p>
     *
     * @return precio final calculado en euros
     */
    public abstract double calcularPrecioFinal();

    /**
     * Devuelve una representación en texto del ítem.
     *
     * @return cadena con los datos principales del ítem
     */
    @Override
    public String toString() {
        return String.format("[ID:%d] %s - %.2f€ (Stock: %d)", id, nombre, calcularPrecioFinal(), stock);
    }
}
