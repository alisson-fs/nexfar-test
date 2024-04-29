package br.alisson.nexfartest.service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import br.alisson.nexfartest.model.Pedido;
import br.alisson.nexfartest.model.RelatorioRequest;
import br.alisson.nexfartest.repository.PedidoRepository;
import br.alisson.nexfartest.util.RelatorioUtils;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public void gerarRelatorio(RelatorioRequest request) {
		String tipoRelatorio = request.getKey();
		List<Pedido> pedidos = pedidoRepository.getPedidos(tipoRelatorio, request.getFilters());
		exportarParaCSV(tipoRelatorio, pedidos);
	}


	public void exportarParaCSV(String tipoRelatorio, List<Pedido> pedidos) {
		String nomeArquivo = "arquivoCSV";
		List<String[]> linhas = RelatorioUtils.convertPedidosToLines(tipoRelatorio, pedidos);
		try (CSVWriter writer = new CSVWriter(new FileWriter(nomeArquivo))) {
			for (String[] linha : linhas) {
				writer.writeNext(linha);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
