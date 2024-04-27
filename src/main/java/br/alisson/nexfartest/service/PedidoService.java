package br.alisson.nexfartest.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.alisson.nexfartest.model.Pedido;
import br.alisson.nexfartest.model.RelatorioRequest;
import br.alisson.nexfartest.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public void gerarRelatorio(RelatorioRequest request) {
		List<Pedido> pedidos = pedidoRepository.getPedidos(request.getFilters());

	}
}
