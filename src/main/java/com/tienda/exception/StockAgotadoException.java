package com.tienda.exception;

/**
 * Excepción lanzada cuando se intenta vender o reservar un ítem
 * cuyo stock en inventario es insuficiente o nulo.
 *
 * <p>Esta excepción extiende {@link Exception} y es una excepción
 * <b>comprobada</b> (<i>checked</i>), por lo que el compilador obliga
 * a capturarla o declararla con {@code throws}.</p>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>
 *   if (item.getStock() &lt; cantidad) {
 *       throw new StockAgotadoException(item.getNombre(), cantidad, item.getStock());
 *   }
 * </pre>
 *
 * @author Alumno
 * @version 1.0.0
 */
public class StockAgotadoException extends Exception {

    /** Nombre del ítem que generó la excepción. */
    private final String nombreItem;

    /** Cantidad solicitada que superó el stock disponible. */
    private final int cantidadSolicitada;

    /** Stock real disponible en el momento del error. */
    private final int stockDisponible;

    /**
     * Construye una nueva {@code StockAgotadoException} con información detallada.
     *
     * @param nombreItem         nombre del ítem con stock insuficiente
     * @param cantidadSolicitada unidades que se intentaron retirar
     * @param stockDisponible    unidades realmente disponibles en inventario
     * @throws IllegalArgumentException si {@code cantidadSolicitada} es menor o igual a cero
     */
    public StockAgotadoException(String nombreItem, int cantidadSolicitada, int stockDisponible) {
        super(String.format(
                "Stock insuficiente para '%s': se solicitaron %d unidades pero solo hay %d disponibles.",
                nombreItem, cantidadSolicitada, stockDisponible
        ));
        this.nombreItem = nombreItem;
        this.cantidadSolicitada = cantidadSolicitada;
        this.stockDisponible = stockDisponible;
    }

    /**
     * Devuelve el nombre del ítem que causó la excepción.
     *
     * @return nombre del ítem
     */
    public String getNombreItem() {
        return nombreItem;
    }

    /**
     * Devuelve la cantidad que se intentó solicitar.
     *
     * @return unidades solicitadas
     */
    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    /**
     * Devuelve el stock disponible en el momento del error.
     *
     * @return unidades disponibles
     */
    public int getStockDisponible() {
        return stockDisponible;
    }
}
