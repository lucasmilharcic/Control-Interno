package com.shastino.controlProductos.controller;

import com.shastino.controlProductos.entity.Categoria;
import com.shastino.controlProductos.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // 🔹 GET: listar todas las categorías
    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    // 🔹 GET: obtener una categoría por nombre
    @GetMapping("/{nombre}")
    public ResponseEntity<Categoria> obtenerCategoriaPorNombre(@PathVariable String nombre) {
        return categoriaService.obtenerCategoriaPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 POST: crear nueva categoría
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.guardarCategoria(categoria));
    }

    // 🔹 PUT: actualizar una categoría existente
    @PutMapping("/{nombre}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable String nombre, @RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(nombre, categoria));
    }

    // 🔹 DELETE: eliminar categoría por nombre
    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable String nombre) {
        categoriaService.eliminarCategoriaPorNombre(nombre);
        return ResponseEntity.noContent().build();
    }
}
