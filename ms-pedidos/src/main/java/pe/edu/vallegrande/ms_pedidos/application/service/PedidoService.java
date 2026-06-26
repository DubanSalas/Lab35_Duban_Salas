package pe.edu.vallegrande.ms_pedidos.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.vallegrande.ms_pedidos.application.port.in.IPedidoServicePort;
import pe.edu.vallegrande.ms_pedidos.application.port.out.IPedidoRepositoryPort;
import pe.edu.vallegrande.ms_pedidos.application.port.out.IProductoClientPort;
import pe.edu.vallegrande.ms_pedidos.domain.model.Pedido;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PedidoService implements IPedidoServicePort {

    private final IPedidoRepositoryPort repositoryPort;
    private final IProductoClientPort productoClientPort;

    public PedidoService(IPedidoRepositoryPort repositoryPort, IProductoClientPort productoClientPort) {
        this.repositoryPort = repositoryPort;
        this.productoClientPort = productoClientPort;
    }

    @Override
    public Flux<Pedido> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Mono<Pedido> findById(Long id) {
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")));
    }

    @Override
    public Mono<Pedido> create(Pedido order) {
        return productoClientPort.findById(order.getProductId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado")))
                .flatMap(product -> {
                    if (product.getStock() < order.getQuantity()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente"));
                    }
                    return productoClientPort.decreaseStock(product.getId(), order.getQuantity())
                            .flatMap(updated -> {
                                order.setTotal(product.getPrice() * order.getQuantity());
                                order.setStatus("CONFIRMADO");
                                order.setDate(java.time.LocalDateTime.now());
                                return repositoryPort.save(order);
                            });
                });
    }

    @Override
    public Mono<Pedido> cancel(Long id) {
        return findById(id)
                .flatMap(order -> {
                    order.setStatus("CANCELADO");
                    return repositoryPort.save(order);
                });
    }

}
