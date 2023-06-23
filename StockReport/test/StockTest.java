import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StockTest {

    private Stock stock;

    @BeforeEach
    public void setUp() throws Exception {
        stock = new Stock("AG");
    }

    @Test
    public void storesSymbol() {
        assertEquals("AG", stock.getStock());
    }

    @Test
    public void storesLastPrice() {
        assertTrue(stock.getLastPriceFloat() > 11.50 && stock.getLastPriceFloat() < 15.00);
    }

    @Test
    public void storesPercentCng() {
        assertTrue(stock.getPercentChange().contains("%"));
    }

    @Test
    public void convertsLastPrice() {
        String lastPrice = stock.getLastPriceFloat().toString();
        assertTrue(stock.getLastPrice().equals(lastPrice));
    }

    @Test
    public void convertsPercentCng() {
        String percentChange = (stock.getPercentChangeFloat().toString() + "%");
        assertTrue(stock.getPercentChange().equals(percentChange));
    }

    @Test
    public void convertsFiveDay() {
        String fiveDay = (stock.getFiveDayFloat().toString());
        assertTrue(stock.getFiveDay().equals(fiveDay));
    }
}