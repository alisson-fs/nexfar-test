package br.alisson.nexfartest.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.alisson.nexfartest.model.Pedido;

public class RelatorioUtils {
	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final String[] colunasPedidosResumido = {
			"_id",
			"client.cnpj",
			"client.name",
			"createdAt",
			"status",
			"netTotal",
			"totalWithTaxes"
	};
	public static final String[] colunasPedidosDetalhado = {
			"_id",
			"client.cnpj",
			"client.name",
			"createdAt",
			"status",
			"items.product.sku",
			"items.product.name",
			"items.quantity",
			"items.finalPrice.price",
			"items.finalPrice.finalPrice"
	};

	public static List<String[]> convertPedidosToLines(String tipoRelatorio, List<Pedido> pedidos) {
		List<String[]> lines = new ArrayList<>();
		pedidos.forEach(pedido -> {
			String _id = pedido.get_id().toString();
			String clientCNPJ = pedido.getClient().getCnpj();
			String clientName = pedido.getClient().getName();
			String createdAt = dateFormatter.format(pedido.getCreatedAt());
			String status = pedido.getStatus();

			if (Objects.equals(tipoRelatorio, "ORDER_SIMPLE")) {
				String[] pedidoResumido = {
						_id,
						clientCNPJ,
						clientName,
						createdAt,
						status,
						pedido.getNetTotal().toString(),
						pedido.getTotalWithTaxes().toString()
				};
				lines.add(pedidoResumido);
			} else if (Objects.equals(tipoRelatorio, "ORDER_DETAILED")) {
				pedido.getItems().forEach(item -> {
					String[] pedidoDetalhado = {
							_id,
							clientCNPJ,
							clientName,
							createdAt,
							status,
							item.getProduct().getSku(),
							item.getProduct().getName(),
							item.getQuantity().toString(),
							item.getPrice().toString(),
							item.getFinalPrice().toString()
					};
					lines.add(pedidoDetalhado);
				});
			}
		});
		return lines;
	}
}
