package br.alisson.nexfartest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.alisson.nexfartest.model.RelatorioRequest;
import br.alisson.nexfartest.service.PedidoService;

@RestController
@RequestMapping("/report")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping("/generate")
	public void gerarRelatorio(@RequestBody RelatorioRequest request) {
		pedidoService.gerarRelatorio(request);
	}
}
