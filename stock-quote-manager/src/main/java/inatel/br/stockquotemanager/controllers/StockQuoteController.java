package inatel.br.stockquotemanager.controllers;

import inatel.br.stockquotemanager.entities.Stock;
import inatel.br.stockquotemanager.services.StockQuoteService;
import inatel.br.stockquotemanager.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class StockQuoteController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockQuoteService stockQuoteService;

    Logger logger = LoggerFactory.getLogger(StockQuoteController.class);

    @PostMapping(value = "/stock_quote/")
    public ResponseEntity<?> saveStockQuote(@RequestBody String body) {
        try {
            Stock stock = stockQuoteService.save(body);

            if (stock == null)
                return new ResponseEntity<String>("The stock isn't registered in Stock Manager", HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<String>("Stock registered", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/stock_quote/{id}/")
    public ResponseEntity<?> getStockQuoteById(@PathVariable String id) {
        try {
            HashMap<String, Object> response = stockQuoteService.getById(id);

            if (response.isEmpty())
                return new ResponseEntity<String>("Stock quote not found", HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/stock_quote/")
    public ResponseEntity<?> getAllStockQuotes() {
        try {
            List<HashMap<String, Object>> response = stockQuoteService.getAll();

            return new ResponseEntity<List<HashMap<String, Object>>>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/stockcache")
    public HttpStatus cleanStockList() {
        stockService.cleanStockList();

        return HttpStatus.OK;
    }
}
