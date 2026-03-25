package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo inicializa si la base de datos está vacía
        if (productoRepository.count() == 0) {
            logger.info("Inicializando datos de productos...");
            
            productoRepository.save(new Producto("Laptop Dell XPS", 1200.00));
            productoRepository.save(new Producto("Mouse Logitech", 25.99));
            productoRepository.save(new Producto("Teclado Mecánico", 89.99));
            productoRepository.save(new Producto("Monitor LG 27\"", 299.99));
            productoRepository.save(new Producto("Webcam HD", 59.99));
            
            logger.info("✅ {} productos iniciales cargados correctamente", productoRepository.count());
        } else {
            logger.info("Base de datos ya contiene productos. Se omitió la inicialización.");
        }
    }
}
