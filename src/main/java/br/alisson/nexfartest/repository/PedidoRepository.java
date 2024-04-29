package br.alisson.nexfartest.repository;

import static br.alisson.nexfartest.util.RelatorioUtils.colunasPedidosDetalhado;
import static br.alisson.nexfartest.util.RelatorioUtils.colunasPedidosResumido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import br.alisson.nexfartest.model.FilterRequest;
import br.alisson.nexfartest.model.Pedido;

@Repository
public class PedidoRepository {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public PedidoRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<Pedido> getPedidos(String tipoRelatorio, List<FilterRequest> filters) {
		Query query = new Query();

		if (Objects.equals(tipoRelatorio, "ORDER_SIMPLE")) {
			query.fields().include(colunasPedidosResumido);
		} else if (Objects.equals(tipoRelatorio, "ORDER_DETAILED")) {
			query.fields().include(colunasPedidosDetalhado);
		}

		filters.forEach(filter -> {
			String key = filter.getKey();
			String operation = filter.getOperation();
			String value1 = filter.getValue1();
			String value2 = filter.getValue2();

			switch (key) {
			case "cnpj", "status" -> query.addCriteria(Criteria.where(key).is(value1));
			case "createdAt" -> {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					Date date1 = formatter.parse(value1);
					Date date2 = formatter.parse(value2);
					query.addCriteria(Criteria.where(key).gte(date1).lte(date2));
				} catch (ParseException e) {
					System.out.println(e.getMessage());
				}
			}
			case "netTotal" -> {
				if ("gte".equals(operation)) {
					query.addCriteria(Criteria.where(key).gte(value1));
				} else {
					query.addCriteria(Criteria.where(key).lte(value1));
				}
			}
			}
		});

		return mongoTemplate.find(query, Pedido.class);
	}
}
