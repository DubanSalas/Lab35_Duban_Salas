package pe.edu.vallegrande.ms_pedidos.infrastructure.adapter.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.ms_pedidos.domain.model.Pedido;

public interface PedidoRepository extends ReactiveCrudRepository<Pedido, Long> {
}
