public class Stock {

    private ScreenScrape webpage;
    private String name;
    private String lastPrice;
    private String percentChange;
    private String avgVol;
    private String fiveDay;
    private String recommendation;

    /** This class sets up and initializes an object called Stock.
    We'll use this to call everything as we build the report. */
    public Stock(String sym) {
        this.name = sym;
        this.webpage = new ScreenScrape(sym);
        this.lastPrice = webpage.parseLastPrice();
        this.percentChange = webpage.parsePercentChange();
        this.avgVol = webpage.parseAvgVol();
        this.fiveDay = webpage.parseFiveDayCng();
        this.setRecommendation();
    }

    /** returns the symbol of the stock */
    public String getStock() {
        return this.name;
    }
    /** returns the last price of the stock */
    public String getLastPrice() {
        return this.lastPrice;
    }
    /** converts the last price of the stock into a float value */
    public Float getLastPriceFloat() {
        return Float.parseFloat(this.lastPrice);
    }
    /** returns the percent change of the stock */
    public String getPercentChange() {
        return this.percentChange;
    }
    /** converts the percent change of the stock into a float value */
    public Float getPercentChangeFloat() {
        String[] temp_val = this.percentChange.split("%");
        return Float.parseFloat(temp_val[0]);
    }
    /** returns the average volume of the stock */
    public String getAvgVol() {
        return this.avgVol;
    }
    /** converts the average volume of the stock into an integer value */
    public int getAvgVolInt() {
        String[] temp_val = this.avgVol.split(",");
        String val = "";
        for (String str : temp_val) {
            val += str;
        }
        return (Integer.parseInt(val));
    }
    /** returns the five-day change of the stock */
    public String getFiveDay() {
        return this.fiveDay;
    }
    /** converts the five-day change of the stock into a float value */
    public Float getFiveDayFloat() {
        return Float.parseFloat(this.fiveDay);
    }

    public void setRecommendation() {
        if (this.getPercentChangeFloat() >= 1.50) {
            this.recommendation = ("Sell any shares held!");
        }
        if (this.getPercentChangeFloat() >= 1.00) {
            this.recommendation = ("Sell or hold on");
        }
        if (this.getPercentChangeFloat() < 1.00 && this.getPercentChangeFloat() >= -1.00) {
            this.recommendation = ("Hold!");
        }
        if (this.getPercentChangeFloat() < -1.00 && this.getPercentChangeFloat() >= -1.50) {
            this.recommendation = ("Moderately buy more shares!");
        }
        if (this.getPercentChangeFloat() < -1.50) {
            this.recommendation = ("Buy more shares!");
        }
    }

    public String getRecommendation() {
        return this.recommendation;
    }



}
