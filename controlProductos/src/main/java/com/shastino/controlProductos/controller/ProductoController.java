package com.shastino.controlProductos.controller;

import com.shastino.controlProductos.entity.Producto;
import com.shastino.controlProductos.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @DeleteMapping("/{nombre}")
    public void eliminarProducto(@PathVariable String nombre) {
        productoService.eliminarProductoPorNombre(nombre);
    }

    @GetMapping("/estadisticas")
    public Map<String, Double> obtenerEstadisticas(
            @RequestParam int mes,
            @RequestParam int anio
    ) {
        return productoService.obtenerEstadisticasMensuales(mes, anio);
    }
}
