package inatel.br.stockquotemanager.repositories;

import inatel.br.stockquotemanager.entities.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockQuoteRepository extends CrudRepository<Stock, String> {

}
