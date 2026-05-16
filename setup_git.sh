#!/usr/bin/env bash
# =============================================================
#  setup_git.sh — Inicializa el repositorio y crea el historial
#  completo de commits siguiendo las fases del proyecto.
#  Ejecutar desde la raíz del proyecto: bash setup_git.sh
# =============================================================
set -e

echo "════════════════════════════════════════"
echo "  Inicializando repositorio Git"
echo "════════════════════════════════════════"

git init
git config user.name  "Alumno"
git config user.email "alumno@ejemplo.com"

# ─────────────────────────────────────────
# FASE 1 — Estructura base
# ─────────────────────────────────────────
git add .gitignore
git commit -m "[add] añade .gitignore con reglas para Java, IDEs y SO"

git add README.md
git commit -m "[add] añade README.md con descripción y estructura del proyecto"

# Estructura de carpetas (archivos .gitkeep para carpetas vacías)
mkdir -p src/main/java/com/tienda/{model,exception,service,ui}
touch src/main/java/com/tienda/model/.gitkeep
touch src/main/java/com/tienda/exception/.gitkeep
touch src/main/java/com/tienda/service/.gitkeep
touch src/main/java/com/tienda/ui/.gitkeep
git add src/
git commit -m "[add] crea estructura de paquetes del proyecto Java"

# ─────────────────────────────────────────
# FASE 2 — Desarrollo y documentación
# ─────────────────────────────────────────
git add src/main/java/com/tienda/model/Vendible.java
git commit -m "[add] añade interfaz Vendible con contrato de descuentos"

git add src/main/java/com/tienda/model/Item.java
git commit -m "[add] añade clase abstracta Item con atributos y calcularPrecioFinal()"

git add src/main/java/com/tienda/model/Videojuego.java
git commit -m "[add] añade clase Videojuego con IVA cultural 4% y soporte de descuentos"

git add src/main/java/com/tienda/model/Consola.java
git commit -m "[add] añade clase Consola con IVA general 21% y soporte de descuentos"

git add src/main/java/com/tienda/exception/StockAgotadoException.java
git commit -m "[add] añade StockAgotadoException con información detallada del error"

git add src/main/java/com/tienda/exception/ItemNoEncontradoException.java
git commit -m "[add] añade ItemNoEncontradoException para búsquedas fallidas por ID"

git add src/main/java/com/tienda/service/Inventario.java
git commit -m "[add] añade clase Inventario con operaciones CRUD, búsqueda y venta"

git add src/main/java/com/tienda/ui/Menu.java
git commit -m "[add] añade clase Menu con menú interactivo de consola"

git add src/main/java/com/tienda/Main.java
git commit -m "[add] añade Main con datos de ejemplo e inicio de la aplicación"

# Javadoc (si el entorno tiene javadoc disponible)
if command -v javadoc &> /dev/null; then
    echo "Generando Javadoc..."
    javadoc -d docs -sourcepath src/main/java -subpackages com.tienda \
            -windowtitle "Tienda de Videojuegos API" \
            -use -author -version -quiet 2>/dev/null || true
    git add docs/
    git commit -m "[add] genera documentación Javadoc en carpeta docs/"
fi

echo ""
echo "════════════════════════════════════════"
echo "  FASE 3 — Gestión de ramas y conflicto"
echo "════════════════════════════════════════"

# ─────────────────────────────────────────
# RAMA feature/sistema-descuentos
# ─────────────────────────────────────────
git checkout -b feature/sistema-descuentos
echo "Creada rama feature/sistema-descuentos"

git add src/main/java/com/tienda/service/GestorDescuentos.java
git commit -m "[add] añade GestorDescuentos con descuento global y simulación"

# Modifica calcularPrecioFinal en Videojuego para el conflicto
# (versión con descuento acumulativo extra del 5%)
cat > src/main/java/com/tienda/model/Videojuego.java << 'JAVA_EOF'
package com.tienda.model;

/**
 * Representa un videojuego disponible en el inventario de la tienda.
 *
 * <p>Extiende {@link Item} e implementa {@link Vendible}.
 * El precio final incorpora IVA cultural (4%), descuento aplicado
 * y un descuento de fidelidad adicional del 5% por campaña.</p>
 *
 * <ul>
 *   <li>IVA aplicado: <b>4%</b> (tipo reducido cultural)</li>
 *   <li>Descuento de fidelidad adicional: <b>5%</b></li>
 * </ul>
 *
 * @author Alumno
 * @version 1.1.0
 */
public class Videojuego extends Item implements Vendible {

    private String plataforma;
    private String genero;
    private double descuento;

    /**
     * Constructor de la clase {@code Videojuego}.
     *
     * @param id         identificador único
     * @param nombre     título del videojuego
     * @param precio     precio base sin IVA en euros
     * @param stock      unidades disponibles en almacén
     * @param plataforma plataforma de juego
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
     * Calcula el precio final aplicando IVA, descuento y descuento de fidelidad (5%).
     *
     * <p>Fórmula: {@code precioBase * 1.04 * (1 - descuento) * 0.95}</p>
     *
     * @return precio final con descuento de fidelidad incluido
     */
    @Override
    public double calcularPrecioFinal() {
        // FEATURE: aplica descuento extra del 5% por campaña de fidelidad
        double conIva = getPrecio() * 1.04;
        return conIva * (1 - descuento) * 0.95;
    }

    /** {@inheritDoc} */
    @Override
    public void aplicarDescuento(double porcentaje) {
        this.descuento = Math.min(porcentaje, 1.0);
    }

    /** {@inheritDoc} */
    @Override
    public String getCategoria() {
        return "Videojuego - " + genero;
    }

    /** @return plataforma del videojuego */
    public String getPlataforma() { return plataforma; }

    /** @return género del videojuego */
    public String getGenero() { return genero; }

    /** @return descuento actual entre 0.0 y 1.0 */
    public double getDescuento() { return descuento; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | %s | %s", plataforma, genero);
    }
}
JAVA_EOF

git add src/main/java/com/tienda/model/Videojuego.java
git commit -m "[fix] aplica descuento de fidelidad 5% en calcularPrecioFinal de Videojuego"

# Vuelve a main
git checkout main

# ─────────────────────────────────────────
# RAMA hotfix/correccion-precios
# ─────────────────────────────────────────
git checkout -b hotfix/correccion-precios
echo "Creada rama hotfix/correccion-precios"

# Modifica calcularPrecioFinal en Videojuego — versión hotfix (corrección IVA a 10%)
cat > src/main/java/com/tienda/model/Videojuego.java << 'JAVA_EOF'
package com.tienda.model;

/**
 * Representa un videojuego disponible en el inventario de la tienda.
 *
 * <p>Extiende {@link Item} e implementa {@link Vendible}.
 * HOTFIX: se corrige el tipo de IVA al 10% según normativa actualizada.</p>
 *
 * <ul>
 *   <li>IVA aplicado: <b>10%</b> (corregido por normativa)</li>
 * </ul>
 *
 * @author Alumno
 * @version 1.0.1
 */
public class Videojuego extends Item implements Vendible {

    private String plataforma;
    private String genero;
    private double descuento;

    /**
     * Constructor de la clase {@code Videojuego}.
     *
     * @param id         identificador único
     * @param nombre     título del videojuego
     * @param precio     precio base sin IVA en euros
     * @param stock      unidades disponibles en almacén
     * @param plataforma plataforma de juego
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
     * Calcula el precio final aplicando IVA corregido (10%) y descuento.
     *
     * <p>HOTFIX: IVA corregido de 4% a 10% según normativa fiscal actualizada.</p>
     * <p>Fórmula: {@code precioBase * 1.10 * (1 - descuento)}</p>
     *
     * @return precio final en euros con IVA corregido
     */
    @Override
    public double calcularPrecioFinal() {
        // HOTFIX: IVA corregido a 10%
        double conIva = getPrecio() * 1.10;
        return conIva * (1 - descuento);
    }

    /** {@inheritDoc} */
    @Override
    public void aplicarDescuento(double porcentaje) {
        this.descuento = Math.min(porcentaje, 1.0);
    }

    /** {@inheritDoc} */
    @Override
    public String getCategoria() {
        return "Videojuego - " + genero;
    }

    /** @return plataforma del videojuego */
    public String getPlataforma() { return plataforma; }

    /** @return género del videojuego */
    public String getGenero() { return genero; }

    /** @return descuento actual entre 0.0 y 1.0 */
    public double getDescuento() { return descuento; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | %s | %s", plataforma, genero);
    }
}
JAVA_EOF

git add src/main/java/com/tienda/model/Videojuego.java
git commit -m "[fix] corrige IVA de videojuegos de 4% a 10% según normativa"

git add src/main/java/com/tienda/service/GestorDescuentos.java
git commit -m "[fix] actualiza GestorDescuentos para compatibilidad con corrección de IVA"

# ─────────────────────────────────────────
# Fusionar hotfix en main
# ─────────────────────────────────────────
git checkout main
git merge hotfix/correccion-precios -m "[fix] merge hotfix/correccion-precios en main — IVA corregido al 10%"
echo ""
echo "✔ hotfix/correccion-precios fusionado en main sin conflicto"

# ─────────────────────────────────────────
# Fusionar feature — GENERARÁ CONFLICTO
# ─────────────────────────────────────────
echo ""
echo "Intentando fusionar feature/sistema-descuentos → se esperan conflictos en Videojuego.java..."
git merge feature/sistema-descuentos || true
echo ""
echo "══════════════════════════════════════════════════════════"
echo "  ¡CONFLICTO DETECTADO en Videojuego.java!"
echo "  Resolviendo: conservamos IVA 10% (hotfix) + descuento"
echo "  de fidelidad 5% (feature) → solución combinada."
echo "══════════════════════════════════════════════════════════"

# Resolución manual del conflicto — versión final combinada
cat > src/main/java/com/tienda/model/Videojuego.java << 'JAVA_EOF'
package com.tienda.model;

/**
 * Representa un videojuego disponible en el inventario de la tienda.
 *
 * <p>Extiende {@link Item} e implementa {@link Vendible}.
 * Versión final resultado de la resolución de conflicto entre
 * {@code hotfix/correccion-precios} y {@code feature/sistema-descuentos}.</p>
 *
 * <ul>
 *   <li>IVA aplicado: <b>10%</b> (corregido por normativa — hotfix)</li>
 *   <li>Descuento de fidelidad adicional: <b>5%</b> (feature campaña)</li>
 * </ul>
 *
 * @author Alumno
 * @version 1.1.1
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
     * Calcula el precio final del videojuego.
     *
     * <p>Combina la corrección de IVA (10%, proveniente del hotfix) con el
     * descuento de fidelidad del 5% de la campaña (proveniente de la feature).
     * Resolución de conflicto: ambas mejoras son complementarias y se aplican juntas.</p>
     *
     * <p>Fórmula: {@code precioBase * 1.10 * (1 - descuento) * 0.95}</p>
     *
     * @return precio final en euros con IVA corregido y descuento de fidelidad
     */
    @Override
    public double calcularPrecioFinal() {
        // RESOLUCION CONFLICTO: IVA 10% (hotfix) + fidelidad 5% (feature)
        double conIva = getPrecio() * 1.10;
        return conIva * (1 - descuento) * 0.95;
    }

    /**
     * Aplica un descuento porcentual al videojuego.
     *
     * @param porcentaje descuento entre 0.0 y 1.0; se limita a 1.0 si se supera
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
    public String getPlataforma() { return plataforma; }

    /**
     * Devuelve el género del videojuego.
     *
     * @return género del juego
     */
    public String getGenero() { return genero; }

    /**
     * Devuelve el descuento actualmente aplicado.
     *
     * @return valor del descuento entre 0.0 y 1.0
     */
    public double getDescuento() { return descuento; }

    /**
     * Representación textual del videojuego.
     *
     * @return cadena con todos los datos relevantes del videojuego
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | %s | %s", plataforma, genero);
    }
}
JAVA_EOF

git add src/main/java/com/tienda/model/Videojuego.java
git commit -m "[fix] resuelve conflicto merge: combina IVA 10% (hotfix) y fidelidad 5% (feature)"

echo ""
echo "════════════════════════════════════════"
echo "  FASE 4 — Etiqueta de versión"
echo "════════════════════════════════════════"
git tag -a v1.0.0 -m "v1.0.0 — Versión de entrega final del proyecto"
echo "✔ Tag v1.0.0 creado"

echo ""
echo "════════════════════════════════════════"
echo "  ¡Todo listo! Historial de commits:"
echo "════════════════════════════════════════"
git log --oneline --graph --all

echo ""
echo "Próximos pasos manuales:"
echo "  1. Crear repo en GitHub y copiar la URL"
echo "  2. git remote add origin https://github.com/TU_USUARIO/tienda-videojuegos.git"
echo "  3. git push -u origin main"
echo "  4. git push origin feature/sistema-descuentos hotfix/correccion-precios"
echo "  5. git push origin v1.0.0"
echo "  6. En GitHub → Settings → Pages → Branch: main → Folder: /docs"
