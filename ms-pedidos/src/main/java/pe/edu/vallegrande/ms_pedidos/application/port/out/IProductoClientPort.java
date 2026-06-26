package pe.edu.vallegrande.ms_pedidos.application.port.out;

import pe.edu.vallegrande.ms_pedidos.domain.model.Producto;
import reactor.core.publisher.Mono;

public interface IProductoClientPort {

    Mono<Producto> findById(Long id);

    Mono<Producto> decreaseStock(Long id, Integer quantity);

}
