package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class PedidoTest {

    private Pedido pedido;
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @BeforeEach
    void setUp() {
        // Inicialización de los productos
        producto1 = new ProductoMenu("Hamburguesa", 15000);
        producto2 = new ProductoMenu("Papas Fritas", 5000);
        
        // Inicialización del pedido
        pedido = new Pedido("Juan Perez", "Calle Falsa 123");
    }

    @Test
    void testGetIdPedido() {
        assertEquals(5, pedido.getIdPedido()); // El primer pedido debe tener ID 0
    }

    @Test
    void testGetNombreCliente() {
        assertEquals("Juan Perez", pedido.getNombreCliente());
    }

    @Test
    void testAgregarProducto() {
        pedido.agregarProducto(producto1);
        assertEquals(1, pedido.getCantidadProductos());
        assertEquals(producto1, pedido.getProducto(0));
        
        pedido.agregarProducto(producto2);
        assertEquals(2, pedido.getCantidadProductos());
        assertEquals(producto2, pedido.getProducto(1));
    }

    @Test
    void testGetPrecioTotalPedidoSinProductos() {
        assertEquals(0, pedido.getPrecioTotalPedido()); // Sin productos, el total debe ser 0
    }

    @Test
    void testGetPrecioTotalPedidoConProductos() {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        
        // Calcular el precio total: 15000 + 5000 + IVA
        int precioNeto = producto1.getPrecio() + producto2.getPrecio(); // 20000
        int iva = (int) (precioNeto * 0.19); // 3800
        int total = precioNeto + iva; // 23800
        
        assertEquals(total, pedido.getPrecioTotalPedido());
    }

    @Test
    public void testGenerarTextoFactura() {
        String expected = "Cliente: Juan Perez\n" +
                          "Dirección: Calle Falsa 123\n" +
                          "----------------\n" +
                          "----------------\n" +
                          "Precio Neto:  0\n" +
                          "IVA:          0\n" +
                          "Precio Total: 0\n";

        String actual = pedido.generarTextoFactura();

        assertEquals(expected, actual);
    }

    @Test
    void testGuardarFactura() throws FileNotFoundException {
        // Crear un archivo temporal para guardar la factura
        File archivo = new File("factura.txt");
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        
        // Guardar la factura
        pedido.guardarFactura(archivo);

        // Verificar que el archivo se creó y tiene contenido
        assertTrue(archivo.exists());
        
        // Leer el archivo y verificar su contenido
        StringBuilder contenido = new StringBuilder();
        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                contenido.append(scanner.nextLine()).append("\n");
            }
        }
        
        // Compara el contenido leído del archivo con el texto esperado
        String expected = "Cliente: Juan Perez\n" +
                          "Dirección: Calle Falsa 123\n" +
                          "----------------\n" +
                          "Hamburguesa\n" +
                          "            15000\n" + 
                          "Papas Fritas\n" +
                          "            5000\n" +
                          "----------------\n" +
                          "Precio Neto:  20000\n" +
                          "IVA:          3800\n" +
                          "Precio Total: 23800\n";
        
        assertEquals(expected.trim(), contenido.toString().trim());
        
        // Limpiar el archivo después de la prueba
        archivo.delete();
    }
}
