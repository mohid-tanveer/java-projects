import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class ScreenScrape {

    private StringBuilder page;
    private String sym;

    /** Creates an individual case for ScreenScrape to run based.
    on the stock symbol provided by the user. */
    public ScreenScrape(String symbol) {
        this.sym = symbol;
        this.page = new StringBuilder();
        try {
            this.scrapePage();
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    /** sets up the screen scrape with proper URL */
    public void scrapePage() throws IOException {

        var url = new URL("https://www.barchart.com/stocks/quotes/" + this.sym + "/overview");
        try (var br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;

            while ((line = br.readLine()) != null) {

                this.page.append(line);
                this.page.append(System.lineSeparator());
            }
        }
    }

    public StringBuilder getPage() {
        return this.page;
    }

    /** Extracts the last known price of the stock */
    public String parseLastPrice() {
        int start = this.page.indexOf("lastPrice");
        start += 12;
        int end = this.page.indexOf("\"", start);
        String last_price = this.page.substring(start,end);
        return last_price;
    }

    /** Extracts the percent change between current price and opening price.*/
    public String parsePercentChange() {
        int start = this.page.indexOf("percentChange");
        start += 16;
        int end = this.page.indexOf("\"", start);
        String percentChange = this.page.substring(start,end);
        return percentChange;
    }

    /** Extracts the average volume, or number of shares on the market.*/
    public String parseAvgVol() {
        int placeholder = this.page.indexOf("averageVolume");
        int start = this.page.indexOf("averageVolume", placeholder + 1);
        start += 26;
        int end = this.page.indexOf("&", start);
        String avgVol = this.page.substring(start,end);
        return avgVol;
    }

    /** Extracts the percent change between current price and opening price 5 days ago.
    This allows the user to gain a better understanding of its trend.*/
    public String parseFiveDayCng() {
        int placeholder = this.page.indexOf("priceChange5d");
        int start = this.page.indexOf("priceChange5d", placeholder + 1);
        start += 26;
        int end = this.page.indexOf(" ", start);
        String fiveDay = this.page.substring(start,end);
        return fiveDay;
    }
}
