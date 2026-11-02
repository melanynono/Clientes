package com.krakedev.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.krakedev.clientes.entidades.Cliente;
import com.krakedev.clientes.servicios.ServicioCliente;

class ServicioClienteTest {

    private ServicioCliente servicio;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        servicio = new ServicioCliente();

        cliente = new Cliente();
        cliente.setCedula("1723456789");
        cliente.setNombre("Melany");
        cliente.setApellido("Nono");
    }

    @Test
    void testCrearCliente() {
        Cliente resultado = servicio.crear(cliente);

        assertNotNull(resultado);
        assertSame(cliente, resultado);
        assertEquals("1723456789", resultado.getCedula());
        assertEquals("Melany", resultado.getNombre());
        assertEquals("Nono", resultado.getApellido());
        assertEquals(1, servicio.listar().size());
    }

    @Test
    void testCrearClienteDuplicado() {
        servicio.crear(cliente);

        Cliente duplicado = new Cliente();
        duplicado.setCedula("1723456789");
        duplicado.setNombre("Carlos");
        duplicado.setApellido("Perez");

        Cliente resultado = servicio.crear(duplicado);

        assertNull(resultado);
        assertEquals(1, servicio.listar().size());
    }

    @Test
    void testBuscarPorCedulaExistente() {
        servicio.crear(cliente);

        Cliente resultado = servicio.buscarPorCedula("1723456789");

        assertNotNull(resultado);
        assertSame(cliente, resultado);
        assertEquals("1723456789", resultado.getCedula());
        assertEquals("Melany", resultado.getNombre());
        assertEquals("Nono", resultado.getApellido());
    }

    @Test
    void testBuscarPorCedulaInexistente() {
        Cliente resultado = servicio.buscarPorCedula("0999999999");

        assertNull(resultado);
    }

    @Test
    void testListarClientes() {
        servicio.crear(cliente);

        Cliente segundoCliente = new Cliente();
        segundoCliente.setCedula("0912345678");
        segundoCliente.setNombre("Ana");
        segundoCliente.setApellido("Lopez");

        servicio.crear(segundoCliente);

        assertEquals(2, servicio.listar().size());
        assertTrue(servicio.listar().contains(cliente));
        assertTrue(servicio.listar().contains(segundoCliente));
    }

    @Test
    void testListarSinClientes() {
        assertNotNull(servicio.listar());
        assertTrue(servicio.listar().isEmpty());
        assertEquals(0, servicio.listar().size());
    }

    @Test
    void testActualizarClienteExistente() {
        servicio.crear(cliente);

        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setCedula("1723456789");
        clienteActualizado.setNombre("Maria");
        clienteActualizado.setApellido("Perez");

        Cliente resultado = servicio.actualizar(
                "1723456789", clienteActualizado);

        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
    }

    @Test
    void testActualizarClienteInexistente() {
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setCedula("0999999999");
        clienteActualizado.setNombre("Carlos");
        clienteActualizado.setApellido("Perez");

        Cliente resultado = servicio.actualizar(
                "0999999999", clienteActualizado);

        assertNull(resultado);
    }

    @Test
    void testEliminarClienteExistente() {
        servicio.crear(cliente);

        boolean resultado = servicio.eliminar("1723456789");

        assertTrue(resultado);
        assertTrue(servicio.listar().isEmpty());
        assertNull(servicio.buscarPorCedula("1723456789"));
    }

    @Test
    void testEliminarClienteInexistente() {
        boolean resultado = servicio.eliminar("0999999999");

        assertFalse(resultado);
        assertTrue(servicio.listar().isEmpty());
    }
}
