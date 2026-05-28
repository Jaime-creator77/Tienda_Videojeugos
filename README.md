#  Gestor de Inventario — Tienda de Videojuegos

Aplicación de consola en Java para gestionar el inventario de una tienda de videojuegos.
Permite añadir productos (videojuegos y consolas), vender unidades, aplicar descuentos y buscar en el catálogo.

---

##  Descripción

El proyecto implementa un sistema de gestión de inventario con las siguientes características:

- **Jerarquía de clases**: `Item` (abstracta) → `Videojuego` / `Consola`, con la interfaz `Vendible`.
- **Gestión de excepciones**: `StockAgotadoException` e `ItemNoEncontradoException`.
- **Sistema de descuentos**: aplicación individual o masiva mediante `GestorDescuentos`.
- **Menú interactivo**: interfaz de consola completa con `Menu`.

---

##  Compilación y Ejecución

### Requisitos

- Java 17 o superior

### Compilar

```bash
# Desde la raíz del proyecto
javac -d out $(find src -name "*.java")
```

### Ejecutar

```bash
java -cp out com.tienda.Main
```

### Generar Javadoc

```bash
javadoc -d docs -sourcepath src/main/java -subpackages com.tienda \
        -windowtitle "Tienda de Videojuegos" \
        -use -author -version
```

---

##  Estructura del Proyecto

```
tienda-videojuegos/
├── src/
│   └── main/java/com/tienda/
│       ├── Main.java
│       ├── model/
│       │   ├── Item.java
│       │   ├── Videojuego.java
│       │   ├── Consola.java
│       │   └── Vendible.java
│       ├── exception/
│       │   ├── StockAgotadoException.java
│       │   └── ItemNoEncontradoException.java
│       ├── service/
│       │   ├── Inventario.java
│       │   └── GestorDescuentos.java
│       └── ui/
│           └── Menu.java
├── docs/               ← Javadoc generado (GitHub Pages)
├── .gitignore
└── README.md
```

---

##  Javadoc

La documentación completa del proyecto está disponible en GitHub Pages:

  **[Ver Javadoc en línea](https://Jaime-creator77.github.io/Tienda_Videojeugos/)**

---

##  Versión

`v1.0.0` — Versión de entrega final.

---

##  Autor

Desarrollado como proyecto de mejora de la asignatura de Programación.
