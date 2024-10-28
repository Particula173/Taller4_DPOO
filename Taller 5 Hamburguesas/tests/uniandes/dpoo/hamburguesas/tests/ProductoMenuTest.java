package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

class ProductoMenuTest {

    private ProductoMenu producto;

    @BeforeEach
    void setUp() {
        producto = new ProductoMenu("Hamburguesa", 15000);
    }

    @Test
    void testGetNombre() {
        assertEquals("Hamburguesa", producto.getNombre());
    }

    @Test
    void testGetPrecio() {
        assertEquals(15000, producto.getPrecio());
    }


}
