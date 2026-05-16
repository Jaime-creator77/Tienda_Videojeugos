package com.tienda.exception;

/**
 * Excepción lanzada cuando se busca un ítem en el inventario
 * mediante un identificador que no existe.
 *
 * <p>Es una excepción <b>comprobada</b> (<i>checked</i>) que obliga
 * al llamador a gestionar el caso en que un ID no esté registrado.</p>
 *
 * @author Alumno
 * @version 1.0.0
 */
public class ItemNoEncontradoException extends Exception {

    /** Identificador que no pudo ser localizado en el inventario. */
    private final int idBuscado;

    /**
     * Construye una nueva {@code ItemNoEncontradoException}.
     *
     * @param idBuscado identificador del ítem que no fue encontrado
     */
    public ItemNoEncontradoException(int idBuscado) {
        super("No se encontró ningún ítem con ID: " + idBuscado);
        this.idBuscado = idBuscado;
    }

    /**
     * Devuelve el identificador que causó la excepción.
     *
     * @return ID no encontrado
     */
    public int getIdBuscado() {
        return idBuscado;
    }
}
