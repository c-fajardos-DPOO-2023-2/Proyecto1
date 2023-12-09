package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Inventario.Inventario;
import SistemaAlquiler.VehiculoRentalSystem;

public class InventarioTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testCargarSedes() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarSedes(rentalSystem);

        assertNotNull(rentalSystem.getSedes());
        assertTrue(rentalSystem.getSedes().size() > 0);
    }

    @Test
    public void testCargarCategorias() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarCategorias(rentalSystem);

        assertNotNull(rentalSystem.getCategorias());
        assertTrue(rentalSystem.getCategorias().size() > 0);
    }
    
    @Test
    public void testCargarReservas() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarReservas(rentalSystem);
        
        assertNotNull(rentalSystem.getReservas());
        assertTrue(rentalSystem.getReservas().size() > 0);
    }

    @Test
    public void testCargarEmpleados() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarEmpleados(rentalSystem);
        
        assertNotNull(rentalSystem.getEmpleados());
        assertTrue(rentalSystem.getEmpleados().size() > 0);
    }

    @Test
    public void testCargarClientes() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarClientes(rentalSystem);
        
        assertNotNull(rentalSystem.getClientes());
        assertTrue(rentalSystem.getClientes().size() > 0);
    } 
    
    @Test
    public void testCargarCarros() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarCarros(rentalSystem);
        
        assertNotNull(rentalSystem.getVehiculos());
        assertTrue(rentalSystem.getVehiculos().size() > 0);
    }
    
    @Test
    public void testCargarUsuarioContrasenas() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarUsuarioContrasenas(rentalSystem);
        
        assertNotNull(rentalSystem.getContraseñasUsuarios());
        assertTrue(rentalSystem.getContraseñasUsuarios().size() > 0);
    }
    
    @Test
    public void testCargarAgendasCarros() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarAgendasCarros(rentalSystem);
        
        assertNotNull(rentalSystem.getAgendasCarros());
        assertTrue(rentalSystem.getAgendasCarros().size() > 0);
    }
    
    @Test
    public void testCargarSeguros() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarSeguros(rentalSystem);
        
        assertNotNull(rentalSystem.getSeguros());
        assertTrue(rentalSystem.getSeguros().size() > 0);
    }
    
    @Test
    public void testCargarSedesInvalida() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        inventario.CargarSedes(rentalSystem);

        assertEquals("Advertencia: línea incorrecta en el archivo de carros: ...", outContent.toString().trim());
    }
    
    @Test
    public void testCargarSedesConExcepcion() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        try {
            inventario.CargarSedes(rentalSystem);
        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    @Test
    public void testCargarClientesConFechaInvalida() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        try {
            inventario.CargarClientes(rentalSystem);
        } catch (Exception e) {
            assertTrue(e instanceof ParseException);
        }
    }

    @Test
    public void testCargarSegurosConExcepcion() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        try {
            inventario.CargarSeguros(rentalSystem);
        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }
    
    @Test
    public void testCargarUsuarioContrasenasConExcepcion() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        try {
            inventario.CargarUsuarioContrasenas(rentalSystem);
        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    @Test
    public void testCargarAgendasCarrosConExcepcion() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        try {
            inventario.CargarAgendasCarros(rentalSystem);
        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    @Test
    public void testCargarSegurosReservasConExcepcion() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        try {
            inventario.CargarSegurosReservas(rentalSystem);
        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    @Test
    public void testCargarCarrosConExcepcion() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        try {
            inventario.CargarCarros(rentalSystem);
        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    @Test
    public void testCargarReservasConFechaInvalida() {
        Inventario inventario = new Inventario();
        VehiculoRentalSystem rentalSystem = inventario.getAplicacion();

        try {
            inventario.CargarReservas(rentalSystem);
        } catch (Exception e) {
            assertTrue(e instanceof ParseException);
        }
    }
}
