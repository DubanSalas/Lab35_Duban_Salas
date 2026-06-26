package pe.edu.vallegrande.ms_pedidos.infrastructure.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.ms_pedidos.application.port.out.IPedidoRepositoryPort;
import pe.edu.vallegrande.ms_pedidos.domain.model.Pedido;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PedidoRepositoryAdapter implements IPedidoRepositoryPort {

    private final PedidoRepository repository;

    @Override
    public Flux<Pedido> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Pedido> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Pedido> save(Pedido order) {
        return repository.save(order);
    }

}
