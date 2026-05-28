package com.tienda.service;

import com.tienda.exception.ItemNoEncontradoException;
import com.tienda.exception.StockAgotadoException;
import com.tienda.model.Item;
import com.tienda.model.Vendible;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase de servicio que gestiona el inventario completo de la tienda de videojuegos.
 *
 * <p>Proporciona operaciones <b>CRUD</b> sobre los ítems del inventario,
 * así como funcionalidades de búsqueda, filtrado y venta.</p>
 *
 * <ul>
 *   <li>Añadir y eliminar ítems del catálogo.</li>
 *   <li>Buscar por ID o por nombre parcial.</li>
 *   <li>Vender unidades descontando stock automáticamente.</li>
 *   <li>Aplicar descuentos a ítems que implementen {@link Vendible}.</li>
 * </ul>
 *
 * @author Alumno
 * @version 1.0.0
 */
public class Inventario {

    /** Lista interna que almacena todos los ítems del inventario. */
    private final List<Item> items;

    /**
     * Constructor que inicializa un inventario vacío.
     */
    public Inventario() {
        this.items = new ArrayList<>();
    }

    /**
     * Añade un nuevo ítem al inventario.
     *
     * <p>Si ya existe un ítem con el mismo ID, la operación es ignorada
     * y se informa por consola.</p>
     *
     * @param item ítem a añadir; no puede ser {@code null}
     * @throws IllegalArgumentException si el ítem es {@code null}
     */
    public void añadirItem(Item item) {
        if (item == null) return;
        boolean existe = items.stream().anyMatch(i -> i.getId() == item.getId());
        if (existe) {
            System.out.println("⚠ Ya existe un ítem con ID " + item.getId() + ". No se añadió.");
        } else {
            items.add(item);
        }
    }
	
    /**
     * Elimina un ítem del inventario por su identificador.
     *
     * @param id identificador del ítem a eliminar
     * @throws ItemNoEncontradoException si no existe ningún ítem con ese ID
     */
    public void eliminarItem(int id) throws ItemNoEncontradoException {
        Item item = buscarPorId(id);
        items.remove(item);
    }

    /**
     * Busca y devuelve un ítem por su identificador único.
     *
     * @param id identificador del ítem buscado
     * @return el {@link Item} encontrado
     * @throws ItemNoEncontradoException si no existe ningún ítem con ese ID
     */
    public Item buscarPorId(int id) throws ItemNoEncontradoException {
        return items.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ItemNoEncontradoException(id));
    }

    /**
     * Busca ítems cuyo nombre contenga el texto especificado (sin distinción de mayúsculas).
     *
     * @param texto fragmento de texto a buscar en el nombre del ítem
     * @return lista de ítems que coinciden con la búsqueda (puede estar vacía)
     */
    public List<Item> buscarPorNombre(String texto) {
        String textoBuscado = texto.toLowerCase();
        return items.stream()
                .filter(i -> i.getNombre().toLowerCase().contains(textoBuscado))
                .collect(Collectors.toList());
    }

    /**
     * Registra la venta de un ítem descontando las unidades del stock.
     *
     * <p>Si el stock resultante sería negativo, lanza {@link StockAgotadoException}
     * sin modificar el inventario.</p>
     *
     * @param id       identificador del ítem a vender
     * @param cantidad unidades a vender (debe ser &gt; 0)
     * @throws ItemNoEncontradoException si el ID no existe en el inventario
     * @throws StockAgotadoException     si el stock disponible es menor que {@code cantidad}
     */
    public void venderItem(int id, int cantidad)
            throws ItemNoEncontradoException, StockAgotadoException {
        Item item = buscarPorId(id);
        if (item.getStock() < cantidad) {
            throw new StockAgotadoException(item.getNombre(), cantidad, item.getStock());
        }
        item.setStock(item.getStock() - cantidad);
        System.out.printf("✔ Vendidas %d unidad(es) de '%s'. Stock restante: %d%n",
                cantidad, item.getNombre(), item.getStock());
    }

    /**
     * Aplica un descuento a un ítem si este implementa la interfaz {@link Vendible}.
     *
     * <p>Si el ítem no es vendible, informa al usuario y no modifica nada.</p>
     *
     * @param id         identificador del ítem
     * @param porcentaje descuento a aplicar entre 0.0 y 1.0
     * @throws ItemNoEncontradoException si el ID no existe en el inventario
     */
    public void aplicarDescuento(int id, double porcentaje) throws ItemNoEncontradoException {
        Item item = buscarPorId(id);
        if (item instanceof Vendible vendible) {
            vendible.aplicarDescuento(porcentaje);
            System.out.printf("✔ Descuento de %.0f%% aplicado a '%s'. Nuevo precio: %.2f€%n",
                    porcentaje * 100, item.getNombre(), item.calcularPrecioFinal());
        } else {
            System.out.println("⚠ El ítem '" + item.getNombre() + "' no admite descuentos.");
        }
    }

    /**
     * Devuelve una copia de la lista completa de ítems en el inventario.
     *
     * @return lista no modificable de todos los ítems
     */
    public List<Item> getTodosLosItems() {
        return List.copyOf(items);
    }

    /**
     * Devuelve el número total de ítems registrados en el inventario.
     *
     * @return cantidad de ítems en el catálogo
     */
    public int getTotalItems() {
        return items.size();
    }

    /**
     * Muestra por consola todos los ítems del inventario con su información completa.
     *
     * <p>Si el inventario está vacío, muestra un mensaje informativo.</p>
     */
    public void mostrarInventario() {
        if (items.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        System.out.println("\n═══════════════════ INVENTARIO ═══════════════════");
        items.forEach(System.out::println);
        System.out.println("══════════════════════════════════════════════════\n");
    }
}
