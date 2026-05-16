package com.tienda;

import com.tienda.model.Consola;
import com.tienda.model.Videojuego;
import com.tienda.service.GestorDescuentos;
import com.tienda.service.Inventario;
import com.tienda.ui.Menu;

/**
 * Clase principal de la aplicación <b>Gestor de Inventario de Tienda de Videojuegos</b>.
 *
 * <p>Punto de entrada de la aplicación de consola. Se encarga de:</p>
 * <ul>
 *   <li>Crear e inicializar el inventario con datos de ejemplo.</li>
 *   <li>Instanciar los servicios necesarios ({@link GestorDescuentos}).</li>
 *   <li>Arrancar el menú interactivo de consola ({@link Menu}).</li>
 * </ul>
 *
 * <p>Para compilar y ejecutar:</p>
 * <pre>
 *   javac -d out src/main/java/com/tienda/**&#47;*.java
 *   java -cp out com.tienda.Main
 * </pre>
 *
 * @author Alumno
 * @version 1.0.0
 */
public class Main {

    /**
     * Método principal que arranca la aplicación.
     *
     * @param args argumentos de línea de comandos (no utilizados en esta versión)
     */
    public static void main(String[] args) {
        Inventario inventario = new Inventario();

        // Añadir videojuegos de ejemplo
        inventario.añadirItem(new Videojuego(1, "The Legend of Zelda: Tears of the Kingdom",
                59.99, 15, "Nintendo Switch", "Aventura"));
        inventario.añadirItem(new Videojuego(2, "Elden Ring",
                49.99, 8, "PS5", "RPG"));
        inventario.añadirItem(new Videojuego(3, "FIFA 25",
                69.99, 20, "PS5", "Deportes"));
        inventario.añadirItem(new Videojuego(4, "Hollow Knight",
                14.99, 5, "PC", "Metroidvania"));

        // Añadir consolas de ejemplo
        inventario.añadirItem(new Consola(10, "PlayStation 5",
                499.99, 3, "Sony", "9ª Generación"));
        inventario.añadirItem(new Consola(11, "Xbox Series X",
                499.99, 2, "Microsoft", "9ª Generación"));
        inventario.añadirItem(new Consola(12, "Nintendo Switch OLED",
                349.99, 7, "Nintendo", "Híbrida"));

        GestorDescuentos gestorDescuentos = new GestorDescuentos(inventario);
        Menu menu = new Menu(inventario, gestorDescuentos);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  Bienvenido a la Tienda de Videojuegos ║");
        System.out.println("╚══════════════════════════════════════╝");

        menu.iniciar();
    }
}
