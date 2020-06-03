package inatel.br.stockquotemanager;

import inatel.br.stockquotemanager.entities.Quotation;
import inatel.br.stockquotemanager.entities.Stock;
import inatel.br.stockquotemanager.services.StockQuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class StockQuoteServiceTests {

    @Autowired
    private StockQuoteService stockQuoteService;

    @Test
    void parseStockWithValidData() {
        List<Quotation> quotationList = new ArrayList<>();

        Quotation quotation1 = new Quotation();
        quotation1.setDate("2019-01-01");
        quotation1.setValue("10");
        quotationList.add(quotation1);

        Quotation quotation2 = new Quotation();
        quotation2.setDate("2019-01-02");
        quotation2.setValue("11");
        quotationList.add(quotation2);

        Quotation quotation3 = new Quotation();
        quotation3.setDate("2019-01-03");
        quotation3.setValue("14");
        quotationList.add(quotation3);

        Stock stock = new Stock();
        stock.setId("petr3");
        stock.setQuotationList(quotationList);

        try {
            HashMap<String, Object> result = stockQuoteService.parseStock(stock);

            if (!result.isEmpty() && result.containsKey("id") && result.containsKey("quotes"))
                assert true;
            else
                assert false;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void parseStockWithInValidData() {
        try {
            HashMap<String, Object> result = stockQuoteService.parseStock(null);

            if (result.isEmpty())
                assert true;
            else
                assert false;
        } catch (Exception e) {
            assert false;
        }
    }
}
