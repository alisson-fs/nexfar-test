package br.alisson.nexfartest.util;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.opencsv.CSVWriter;

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
			"items.price",
			"items.finalPrice"
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

	public static void exportarParaCSV(String tipoRelatorio, List<Pedido> pedidos) {
		String nomeArquivo = "arquivoCSV.csv";
		List<String[]> linhas = RelatorioUtils.convertPedidosToLines(tipoRelatorio, pedidos);

		switch (tipoRelatorio) {
			case "ORDER_SIMPLE" -> linhas.add(0, colunasPedidosResumido);
			case "ORDER_DETAILED" -> linhas.add(0, colunasPedidosDetalhado);
		}

		try (CSVWriter writer = new CSVWriter(new FileWriter(nomeArquivo))) {
			for (String[] linha : linhas) {
				writer.writeNext(linha);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void exportarParaXLS(String tipoRelatorio, List<Pedido> pedidos) {
		String nomeArquivo = "arquivoXLS.xls";
		List<String[]> linhas = RelatorioUtils.convertPedidosToLines(tipoRelatorio, pedidos);

		switch (tipoRelatorio) {
			case "ORDER_SIMPLE" -> linhas.add(0, colunasPedidosResumido);
			case "ORDER_DETAILED" -> linhas.add(0, colunasPedidosDetalhado);
		}

		try (Workbook workbook = new HSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Pedidos");

			int nRow = 0;
			for (String[] linha : linhas) {
				Row row = sheet.createRow(nRow++);
				int nCol = 0;
				for (String dado : linha) {
					row.createCell(nCol++).setCellValue(dado);
				}
			}

			FileOutputStream fileOut = new FileOutputStream(nomeArquivo);
			workbook.write(fileOut);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
