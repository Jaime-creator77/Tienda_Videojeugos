package com.tienda.ui;

import com.tienda.exception.ItemNoEncontradoException;
import com.tienda.exception.StockAgotadoException;
import com.tienda.model.Consola;
import com.tienda.model.Item;
import com.tienda.model.Videojuego;
import com.tienda.service.GestorDescuentos;
import com.tienda.service.Inventario;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que gestiona la interfaz de usuario por consola de la tienda de videojuegos.
 *
 * <p>Proporciona un menú interactivo con las siguientes opciones:</p>
 * <ul>
 *   <li>Ver inventario completo</li>
 *   <li>Buscar ítems por nombre</li>
 *   <li>Vender unidades de un ítem</li>
 *   <li>Aplicar descuentos individuales o globales</li>
 *   <li>Salir de la aplicación</li>
 * </ul>
 *
 * @author Alumno
 * @version 1.0.0
 */
public class Menu {

    /** Inventario que gestiona esta sesión de la tienda. */
    private final Inventario inventario;

    /** Gestor de descuentos asociado al inventario. */
    private final GestorDescuentos gestorDescuentos;

    /** Scanner para la lectura de entrada del usuario. */
    private final Scanner scanner;

    /**
     * Constructor que inicializa el menú con un inventario y su gestor de descuentos.
     *
     * @param inventario       inventario de la tienda a gestionar
     * @param gestorDescuentos gestor encargado de las operaciones de descuento
     */
    public Menu(Inventario inventario, GestorDescuentos gestorDescuentos) {
        this.inventario = inventario;
        this.gestorDescuentos = gestorDescuentos;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia el bucle principal del menú de consola.
     *
     * <p>El menú permanece activo hasta que el usuario selecciona la opción de salida.</p>
     */
    public void iniciar() {
        boolean activo = true;
        while (activo) {
            mostrarOpciones();
            int opcion = leerEntero("Selecciona una opción: ");
            switch (opcion) {
                case 1 -> inventario.mostrarInventario();
                case 2 -> buscarPorNombre();
                case 3 -> venderItem();
                case 4 -> aplicarDescuentoIndividual();
                case 5 -> aplicarDescuentoGlobal();
                case 0 -> {
                    System.out.println("¡Hasta pronto!");
                    activo = false;
                }
                default -> System.out.println("⚠ Opción no válida. Intenta de nuevo.");
            }
        }
        scanner.close();
    }

    /**
     * Imprime en consola el menú principal de opciones disponibles.
     */
    private void mostrarOpciones() {
        System.out.println("""

                ╔══════════════════════════════════════╗
                ║   TIENDA DE VIDEOJUEGOS - MENÚ PRINCIPAL ║
                ╠══════════════════════════════════════╣
                ║  1. Ver inventario                   ║
                ║  2. Buscar por nombre                ║
                ║  3. Vender ítem                      ║
                ║  4. Aplicar descuento individual     ║
                ║  5. Aplicar descuento global         ║
                ║  0. Salir                            ║
                ╚══════════════════════════════════════╝""");
    }

    /**
     * Gestiona el flujo de búsqueda de ítems por nombre.
     *
     * <p>Lee un texto del usuario y muestra todos los ítems cuyo nombre lo contenga.</p>
     */
    private void buscarPorNombre() {
        System.out.print("Introduce el nombre a buscar: ");
        String texto = scanner.nextLine();
        List<Item> resultados = inventario.buscarPorNombre(texto);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron ítems con ese nombre.");
        } else {
            resultados.forEach(System.out::println);
        }
    }

    /**
     * Gestiona el flujo de venta de un ítem solicitando ID y cantidad al usuario.
     *
     * @throws StockAgotadoException     capturada internamente; se muestra mensaje de error
     * @throws ItemNoEncontradoException capturada internamente; se muestra mensaje de error
     */
    private void venderItem() {
        int id = leerEntero("ID del ítem a vender: ");
        int cantidad = leerEntero("Cantidad: ");
        try {
            inventario.venderItem(id, cantidad);
        } catch (ItemNoEncontradoException | StockAgotadoException e) {
            System.out.println("✖ Error: " + e.getMessage());
        }
    }

    /**
     * Gestiona el flujo para aplicar un descuento a un ítem específico.
     */
    private void aplicarDescuentoIndividual() {
        int id = leerEntero("ID del ítem: ");
        System.out.print("Porcentaje de descuento (ej: 0.10 para 10%): ");
        double pct = scanner.nextDouble();
        scanner.nextLine();
        try {
            inventario.aplicarDescuento(id, pct);
        } catch (ItemNoEncontradoException e) {
            System.out.println("✖ Error: " + e.getMessage());
        }
    }

    /**
     * Gestiona el flujo para aplicar un descuento global a todos los ítems vendibles.
     */
    private void aplicarDescuentoGlobal() {
        System.out.print("Porcentaje de descuento global (ej: 0.15 para 15%): ");
        double pct = scanner.nextDouble();
        scanner.nextLine();
        gestorDescuentos.aplicarDescuentoGlobal(pct);
    }

    /**
     * Lee un número entero de la entrada estándar con gestión de errores.
     *
     * <p>Si el usuario introduce un valor no numérico, solicita de nuevo la entrada.</p>
     *
     * @param mensaje mensaje a mostrar al usuario antes de leer
     * @return entero introducido por el usuario
     */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("⚠ Por favor, introduce un número entero válido.");
                scanner.nextLine();
            }
        }
    }
}
