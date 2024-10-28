package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

class ProductoAjustadoTest {
    
    private ProductoMenu productoBase;
    private ProductoAjustado productoAjustado;

    @BeforeEach
    void setUp() {
        productoBase = new ProductoMenu("Hamburguesa Clásica", 15000);
        productoAjustado = new ProductoAjustado(productoBase);
    }

    @Test
    void testGetNombre() {
        assertEquals("Hamburguesa Clásica", productoAjustado.getNombre());
    }

    @Test
    void testGetPrecioSinIngredientes() {
        assertEquals(0, productoAjustado.getPrecio());
    }

    @Test
    void testGenerarTextoFacturaSinIngredientes() {
        String expected = "Hamburguesa Clásica\n" + "            0\n"; 
        assertEquals(expected, productoAjustado.generarTextoFactura());
    }
}
