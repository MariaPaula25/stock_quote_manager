package inatel.br.stockquotemanager.services;

import inatel.br.stockquotemanager.entities.Quotation;
import inatel.br.stockquotemanager.entities.Stock;
import inatel.br.stockquotemanager.repositories.StockQuoteRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StockQuoteService {
    @Autowired
    private StockQuoteRepository stockQuoteRepository;

    @Autowired
    private StockService stockService;

    public Stock save(String body) throws Exception {
        JSONObject jsonObject = new JSONObject(body);

        String stockId = jsonObject.getString("id");

        List<Stock> stockList = stockService.getStockList();

        Stock stockFromStockManager = stockList.stream()
                .filter(stock -> stockId.equals(stock.getId())).findFirst().orElse(null);

        if (stockFromStockManager == null)
            return null;

        Stock stock = new Stock();
        stock.setId(stockId);
        stock.setDescription(stockFromStockManager.getDescription());

        List<Quotation> quotationList = new ArrayList<>();

        JSONObject quotes = jsonObject.getJSONObject("quotes");
        Iterator<String> keys = quotes.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            if (quotes.get(key) instanceof String) {
                Quotation quotation = new Quotation();
                quotation.setDate(key);
                quotation.setValue(quotes.getString(key));
                quotation.setStock(stock);

                quotationList.add(quotation);
            }
        }

        validateQuotationList(stockId, quotationList);
        stock.setQuotationList(quotationList);

        stockQuoteRepository.save(stock);

        return stock;
    }

    public HashMap<String, Object> getById(String id) throws Exception {
        Optional<Stock> result = stockQuoteRepository.findById(id);

        if (!result.isPresent())
            return new HashMap<>();

        Stock stock = result.get();

        return parseStock(stock);
    }

    public List<HashMap<String, Object>> getAll() throws Exception {
        Iterable<Stock> stocks = stockQuoteRepository.findAll();

        List<HashMap<String, Object>> objects = new ArrayList<>();
        for (Stock stock : stocks) {
            objects.add(parseStock(stock));
        }

        return objects;
    }

    public HashMap<String, Object> parseStock(Stock stock) throws Exception {
        if (stock == null)
            return new HashMap<>();

        HashMap<String, String> quotesMap = new HashMap<>();
        for (Quotation quotation : stock.getQuotationList()) {
            quotesMap.put(quotation.getDate(), quotation.getValue());
        }

        HashMap<String, Object> objectMap = new HashMap<>();
        objectMap.put("id", stock.getId());
        objectMap.put("quotes", quotesMap);

        return objectMap;
    }

    public void validateQuotationList(String id, List<Quotation> quotationList) {
        if (id == null || quotationList == null)
            return;

        Optional<Stock> result = stockQuoteRepository.findById(id);

        if (result.isPresent()) {
            Stock stock = result.get();
            quotationList.addAll(stock.getQuotationList());
        }
    }
}
