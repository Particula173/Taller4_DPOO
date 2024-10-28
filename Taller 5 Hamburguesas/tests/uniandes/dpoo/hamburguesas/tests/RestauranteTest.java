package uniandes.dpoo.hamburguesas.tests;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import uniandes.dpoo.hamburguesas.excepciones.*;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest {

    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        restaurante = new Restaurante();
    }

    @Test
    public void testIniciarPedidoConPedidoEnCurso() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Cliente1", "Direccion1");
        
        // Se debe lanzar una excepción al intentar iniciar otro pedido sin cerrar el anterior
        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Cliente2", "Direccion2");
        });
    }

    @Test
    public void testCerrarYGuardarPedidoSinPedidoEnCurso() {
        // Se debe lanzar una excepción si no hay un pedido en curso
        assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        });
    }

    @Test
    public void testCargarIngredientesRepetidos() throws HamburguesaException {
        // Crear un archivo de ingredientes para la prueba
        File archivoIngredientes = new File("ingredientes_test.txt");
        try {
            // Escribir datos de prueba
            writeTestFile(archivoIngredientes, "Tomate;100\nLechuga;50\nTomate;100\n");
            assertThrows(IngredienteRepetidoException.class, () -> {
                restaurante.cargarInformacionRestaurante(archivoIngredientes, new File("menu_test.txt"), new File("combos_test.txt"));
            });
        } catch (IOException e) {
            fail("No se esperaba una excepción de IO: " + e.getMessage());
        } finally {
            archivoIngredientes.delete(); // Limpiar el archivo de prueba
        }
    }

    @Test
    public void testCargarMenuRepetido() throws HamburguesaException {
        // Crear un archivo de menú para la prueba
        File archivoMenu = new File("menu_test.txt");
        try {
            // Escribir datos de prueba
            writeTestFile(archivoMenu, "Hamburguesa1;5000\nHamburguesa2;6000\nHamburguesa1;5000\n");
            assertThrows(ProductoRepetidoException.class, () -> {
                restaurante.cargarInformacionRestaurante(new File("ingredientes_test.txt"), archivoMenu, new File("combos_test.txt"));
            });
        } catch (IOException e) {
            fail("No se esperaba una excepción de IO: " + e.getMessage());
        } finally {
            archivoMenu.delete(); // Limpiar el archivo de prueba
        }
    }

    @Test
    public void testCargarProductoFaltante() throws HamburguesaException {
        // Crear un archivo de combos para la prueba
        File archivoCombos = new File("combos_test.txt");
        try {
            // Escribir datos de prueba
            writeTestFile(archivoCombos, "Combo1;10%;;Hamburguesa1\n");
            assertThrows(ProductoFaltanteException.class, () -> {
                restaurante.cargarInformacionRestaurante(new File("ingredientes_test.txt"), new File("menu_test.txt"), archivoCombos);
            });
        } catch (IOException e) {
            fail("No se esperaba una excepción de IO: " + e.getMessage());
        } finally {
            archivoCombos.delete(); // Limpiar el archivo de prueba
        }
    }

    private void writeTestFile(File file, String content) throws IOException {
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            writer.write(content);
        }
    }
}
