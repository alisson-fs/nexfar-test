# Nexfar test

Este projeto implementa uma API para receber requisições com instruções para geração de relatórios utilizando o framework Spring Boot. Os relatórios são disponibilizados no formato XLS e CSV.

### Estrutura do projeto:

- Projeto Maven
- Java 17
- Spring Boot 3.2.5
- Banco de dados MongoDB

### Relatórios desenvolvidos:

#### Pedido resumido:

- Key: ORDER_SIMPLE
- Descrição: Para cada pedido, deverá existir uma linha no arquivo exportado.
- Nome do arquivo de retorno: PedidoResumido.(xls/csv)
- Collection Mongo: order
- Colunas:
  - _id
  - client.cnpj
  - client.name
  - createdAt (formato: dd/MM/yyyy HH:mm)
  - status
  - netTotal
  - totalWithTaxes 
- Filtros possíveis:
  - cnpj (tipo equals)
  - createdAt (tipo interval)
  - status (tipo equals)
  - netTotal (tipo gte ou lte)

#### Pedido detalhado:

- Key: ORDER_DETAILED
- Descrição: Para cada item do pedido (items), deverá existir uma linha no arquivo exportado.
- Nome do arquivo de retorno: PedidoDetalhado.(xls/csv)
- Collection Mongo: order
- Colunas:
    - _id
    - client.cnpj
    - client.name
    - createdAt (formato: dd/MM/yyyy HH:mm)
    - status
    - items.product.sku 
    - items.product.name 
    - items.quantity 
    - items.finalPrice.price 
    - items.finalPrice.finalPrice
- Filtros possíveis:
    - cnpj (tipo equals)
    - createdAt (tipo interval)
    - status (tipo equals)
    - netTotal (tipo gte ou lte)

### Endpoints:

POST - /report/generate

Payload:

```json
{
    "key" : "ORDER_SIMPLE",
    "format" : "XLS",
    "filters": [
        {
            "key" : "cnpj",
            "operation" : "EQ",
            "value1" : "2021-09-30 20:00",
        },
        {
            "key" : "createdAt",
            "operation" : "INTERVAL",
            "value1" : "2022-09-30 10:30",
            "value2" : "2022-12-16 11:00"
        },
    ]
}
```
