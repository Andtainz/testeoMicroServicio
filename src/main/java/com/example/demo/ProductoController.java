package com.example.demo;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * GET /productos - Obtener todos los productos
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarProductos() {
        List<ProductoDTO> productos = productoService.obtenerTodos();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", productos);
        response.put("count", productos.size());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /productos/{id} - Obtener un producto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerProducto(@PathVariable Long id) {
        ProductoDTO producto = productoService.obtenerPorId(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", producto);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /productos - Crear un nuevo producto
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> agregarProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO productoCreado = productoService.crear(productoDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Producto creado exitosamente");
        response.put("data", productoCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * PUT /productos/{id} - Actualizar un producto
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO productoActualizado = productoService.actualizar(id, productoDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Producto actualizado exitosamente");
        response.put("data", productoActualizado);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /productos/{id} - Eliminar un producto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Producto eliminado exitosamente");
        return ResponseEntity.ok(response);
    }
}