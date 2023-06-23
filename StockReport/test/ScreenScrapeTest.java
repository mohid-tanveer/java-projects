import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScreenScrapeTest {

    private ScreenScrape ss;
    private Stock stock;

    @BeforeEach
    public void setUp() throws Exception {
        ss = new ScreenScrape("AG");
        stock = new Stock("AG");
    }

    @Test
    public void parsesLastPrice() {
        assertTrue(ss.parseLastPrice().equals(stock.getLastPrice()));
    }

    @Test
    public void parsesAvgVol() {
        assertTrue(ss.parseAvgVol().equals(stock.getAvgVol()));
    }

    @Test
    public void parsesFiveDayCng() {
        assertTrue(ss.parseFiveDayCng().equals(stock.getFiveDay()));
    }

    @Test
    public void parsesPercentChange() {
        assertTrue(ss.parsePercentChange().equals(stock.getPercentChange()));
    }
}