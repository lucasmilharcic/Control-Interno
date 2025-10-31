package com.shastino.controlProductos.service;

import com.shastino.controlProductos.entity.Producto;
import com.shastino.controlProductos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto guardarProducto(Producto producto) {
        producto.calcularCostos();
        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public void eliminarProductoPorNombre(String nombre) {
        productoRepository.deleteByNombre(nombre);
    }

    public Map<String, Double> obtenerEstadisticasMensuales(int mes, int anio) {
        List<Producto> productos = productoRepository.findByMesYAnio(mes, anio);

        double totalGastado = productos.stream().mapToDouble(Producto::getCostoTotal).sum();
        double totalGanado = productos.stream().mapToDouble(Producto::getGananciaGenerada).sum();

        Map<String, Double> resultado = new HashMap<>();
        resultado.put("totalGastado", totalGastado);
        resultado.put("totalGanado", totalGanado);
        return resultado;
    }
}
