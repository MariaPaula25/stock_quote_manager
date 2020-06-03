package inatel.br.stockquotemanager;

import inatel.br.stockquotemanager.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockQuoteManagerApplication {

    @Autowired
    private StockService stockService;

    public static void main(String[] args) {
        SpringApplication.run(StockQuoteManagerApplication.class, args);
    }

}
