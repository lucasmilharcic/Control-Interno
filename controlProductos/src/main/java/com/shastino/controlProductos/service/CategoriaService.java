package com.shastino.controlProductos.service;

import com.shastino.controlProductos.entity.Categoria;
import com.shastino.controlProductos.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // 🔹 Listar todas las categorías
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    // 🔹 Buscar una categoría por nombre
    public Optional<Categoria> obtenerCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    // 🔹 Crear o guardar una categoría
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // 🔹 Actualizar una categoría existente por su nombre
    @Transactional
    public Categoria actualizarCategoria(String nombre, Categoria categoriaActualizada) {
        Categoria categoria = categoriaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + nombre));
        categoria.setNombre(categoriaActualizada.getNombre());
        return categoriaRepository.save(categoria);
    }

    // 🔹 Eliminar una categoría por nombre
    @Transactional
    public void eliminarCategoriaPorNombre(String nombre) {
        categoriaRepository.deleteByNombre(nombre);
    }
}
