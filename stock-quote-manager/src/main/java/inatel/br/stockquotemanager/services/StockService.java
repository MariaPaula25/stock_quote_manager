package inatel.br.stockquotemanager.services;

import inatel.br.stockquotemanager.entities.Stock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class StockService {

    private static final String SERVER_URL = "http://api:8080";
    private static List<Stock> stockList = new ArrayList<>();

    public void registerInStockManager() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String url = SERVER_URL + "/notification";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("host", "localhost");
        bodyMap.put("port", "8081");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(bodyMap, headers);

        restTemplate.postForLocation(url, entity);
    }

    public void getStocksFromStockManager() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = SERVER_URL + "/stock";

        ResponseEntity<Stock[]> response = restTemplate.getForEntity(url, Stock[].class);
        stockList = Arrays.asList(response.getBody());
    }

    public List<Stock> getStockList() throws Exception {
        if (stockList.isEmpty())
            getStocksFromStockManager();

        return stockList;
    }

    public void cleanStockList() {
        stockList = new ArrayList<>();
    }
}
