package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.util.ArrayList;

class ComboTest {

    private Combo combo;
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @BeforeEach
    void setUp() {
        producto1 = new ProductoMenu("Hamburguesa", 15000);
        producto2 = new ProductoMenu("Papas Fritas", 5000);
        
        // Crear una lista de productos para el combo
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(producto1);
        items.add(producto2);

        // Crear el combo con un descuento del 10% (0.1)
        combo = new Combo("Combo Clásico", 0.9, items);
    }

    @Test
    void testGetNombre() {
        assertEquals("Combo Clásico", combo.getNombre());
    }

    @Test
    void testGetDescuento() {
        assertEquals(0.9, combo.getDescuento());
    }

    @Test
    void testGetPrecio() {
        // El precio total sin descuento es 15000 + 5000 = 20000
        // Con un descuento del 10%, el precio debe ser 20000 * 0.9 = 18000
        assertEquals(18000, combo.getPrecio());
    }

    @Test
    void testGenerarTextoFactura() {
        String expected = "Combo Combo Clásico\n" +
                          " Descuento: 0.9\n" +
                          "            18000\n";
        assertEquals(expected, combo.generarTextoFactura());
    }
}
