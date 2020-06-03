package inatel.br.stockquotemanager;

import inatel.br.stockquotemanager.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
class StockQuoteManagerApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private StockService stockService;

    Logger logger = LoggerFactory.getLogger(StockQuoteManagerApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            logger.debug("Application is up. Register it in Stock Manager");

            stockService.registerInStockManager();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

