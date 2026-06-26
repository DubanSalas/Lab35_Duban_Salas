package pe.edu.vallegrande.ms_pedidos.infrastructure.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.ms_pedidos.application.port.in.IPedidoServicePort;
import pe.edu.vallegrande.ms_pedidos.domain.model.Pedido;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoRest {

    private final IPedidoServicePort servicePort;

    @GetMapping
    public Flux<Pedido> findAll() {
        return servicePort.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Pedido> findById(@PathVariable Long id) {
        return servicePort.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pedido> save(@RequestBody Pedido order) {
        return servicePort.create(order);
    }

    // @PutMapping @PatchMapping
    @PatchMapping("/{id}/cancel")
    public Mono<Pedido> cancel(@PathVariable Long id) {
        return servicePort.cancel(id);
    }

}
