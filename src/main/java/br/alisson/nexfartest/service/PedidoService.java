package br.alisson.nexfartest.service;

import static br.alisson.nexfartest.util.RelatorioUtils.exportarParaCSV;
import static br.alisson.nexfartest.util.RelatorioUtils.exportarParaXLS;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.alisson.nexfartest.model.FilterRequest;
import br.alisson.nexfartest.model.Pedido;
import br.alisson.nexfartest.model.RelatorioRequest;
import br.alisson.nexfartest.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public void gerarRelatorio(RelatorioRequest request) {
		String tipoRelatorio = request.getKey();
		String format = request.getFormat();
		List<FilterRequest> filters = request.getFilters();
		List<Pedido> pedidos = pedidoRepository.getPedidos(tipoRelatorio, filters);
		switch (format) {
			case "CSV" -> exportarParaCSV(tipoRelatorio, pedidos);
			case "XLS" -> exportarParaXLS(tipoRelatorio, pedidos);
		}
	}
}
