import java.awt.*;
import java.util.ArrayList;

public class StockReportVisual {

    public static ArrayList<Stock> portfolio;
    public static ArrayList<String> portfolio_sym;
    public boolean user_exit;
    public int curr_stock;

    /** Creates an visual representation for the stock reports depending
     on the stock symbols provided by the user. It displays one stock's
     information at a time */
    public StockReportVisual(ArrayList<Stock> portfolio, ArrayList<String> portfolio_sym) {
        this.portfolio = portfolio;
        this.portfolio_sym = portfolio_sym;
        this.curr_stock = 0;
    }

    /** Draws the visuals onto the popup screen */
    public void draw() {
        StdDraw.clear();
        /** Creates the outlines for the buttons and prints the text of what
         each button does. */
        StdDraw.setPenColor(Color.black);
        StdDraw.rectangle(0.75, 0.1, 0.075, 0.05);
        StdDraw.rectangle(0.25, 0.1, 0.075, 0.05);
        StdDraw.rectangle(0.9, 0.9, 0.05, 0.04);
        StdDraw.text(0.75, 0.1, "Next");
        StdDraw.text(0.25, 0.1,  "Previous");
        StdDraw.text(0.9, 0.9, "Exit");
        /** Colors the information green or red depending on if the percent
         * change is positive or negative.
         */
        if (portfolio.get(this.curr_stock).getPercentChangeFloat() < 0) {
            StdDraw.setPenColor(Color.red);
        }
        else {
            StdDraw.setPenColor(Color.green);
        }
        /** Prints the symbol of the current stock on the top of the screen. */
        StdDraw.text(0.5, 0.9, portfolio_sym.get(this.curr_stock));
        /** Displays key information from the stock report for the stock. */
        StdDraw.text(0.25, 0.7, ("Last Price: " + portfolio.get((this.curr_stock)).getLastPrice()));
        StdDraw.text(0.25, 0.6, ("Percent Chg: " + portfolio.get((this.curr_stock)).getPercentChange()));
        StdDraw.text(0.25, 0.5, ("Avg Volume: " + portfolio.get((this.curr_stock)).getAvgVol()));
        StdDraw.text(0.25, 0.4, ("5-day Chg: " + portfolio.get((this.curr_stock)).getFiveDay()));
        StdDraw.text(0.75, 0.6, ("Our Recommendation:"));
        StdDraw.text(0.75, 0.5, portfolio.get((this.curr_stock)).getRecommendation());
        StdDraw.show();
    }

    /** Takes a mouse click and decides if it clicks one of the three buttons
     * on the screen. If it clicks a button it will do what the button describes.
     * For example if the user clicks next, the curr_stock will increment by one.
     */
    public void handleMouseClick() {
        while (!StdDraw.isMousePressed()) {
            // Wait for mouse press
        }
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        while (StdDraw.isMousePressed()) {
            // Wait for mouse release
        }
        if ((x > 0.175 && x < 0.325) && (y > 0.05 && y < 0.15)) {
            if (this.curr_stock == 0) {
                this.curr_stock = 0;
            }
            else {
                this.curr_stock -= 1;
            }
        }
        else if ((x > 0.675 && x < 0.825) && (y > 0.05 && y < 0.15)) {
            if (this.curr_stock == (portfolio.size() - 1)) {
                this.curr_stock = (portfolio.size() - 1);
            }
            else {
                this.curr_stock += 1;
            }
        }
        else if ((x > 0.85 && x < 0.95) && (y > 0.86 && y < 0.94)) {
            this.user_exit = true;
        }
    }

    /** Returns if the user has exited the visual */
    public boolean getUserExit() {
        return this.user_exit;
    }
}
