import java.util.ArrayList;
import java.util.Scanner;

public class StockReport {

    /** Portfolio to hold members of the Stock class. */
    public static ArrayList<Stock> portfolio = new ArrayList<Stock>();
    /** Portfolio that holds the symbols of the stocks. */
    public static ArrayList<String> portfolio_sym = new ArrayList<>();
    /** Holds and builds the final report. */
    public static StringBuilder report = new StringBuilder();
    /** Allows for the possibility for the user to email themselves the report. */
    public static EmailReport ereport;

    /** Adds stocks to portfolio and constructs the report. Also gives user option to email the report. */
    public static void main(String[] args){
        addStocks();
        System.out.println();
        System.out.println();
        constructReport();
        System.out.println(portfolio_sym + "\n");
        System.out.print(report);
        StockReportVisual visual = new StockReportVisual(portfolio, portfolio_sym);
        while (!visual.getUserExit()) {
            visual.draw();
            visual.handleMouseClick();
        }
        System.out.println("Would you like to email this report? y/n: ");
        try (Scanner scan = new Scanner(System.in)) {
            String email_choice = scan.nextLine();
            if (email_choice.equals("y")) {
                constructEmail();
                ereport.Send();
            }
        }
    }

    /** Allows the user to add a set of stocks to their portfolio. */
    public static void addStocks() {
        String new_stock = "";
        System.out.print("Input stock symbol or enter Done: ");
        try (Scanner scan = new Scanner(System.in)) {
            new_stock = scan.nextLine();
            while (!new_stock.equals("Done")) {
                portfolio_sym.add(new_stock);
                portfolio.add(new Stock(new_stock));
                System.out.print("Input stock symbol or enter Done: ");
                new_stock = scan.nextLine();
            }
        }
    }

    /** Constructs the report depending on the values of the stocks. */
    public static void constructReport() {
        for (int i = 0; i < portfolio.size(); i++) {
            report.append(portfolio.get(i).getStock() + "\n");
            report.append("For the stock " + portfolio.get(i).getStock() + " the latest price was: " + portfolio.get(i).getLastPrice() + ".\n");
            if (portfolio.get(i).getAvgVolInt() < 500000) {
                report.append("This stock is being traded relatively low with an average volume of " + portfolio.get(i).getAvgVol() + " compared to other mainstream stocks with average volumes that are above 500,000.\n");
            }
            else {
                report.append("This stock is being traded relatively often with an average volume of " + portfolio.get(i).getAvgVol() + ".\n");
            }
            if (portfolio.get(i).getPercentChangeFloat() < 0) {
                report.append(portfolio.get(i).getStock() + " has been trading at a lower price than yesterday's close with a percent change of: " + portfolio.get(i).getPercentChange() + ".\n");
            }
            else {
                report.append(portfolio.get(i).getStock() + " has been trading at a higher price than yesterday's close with a percent change of: " + portfolio.get(i).getPercentChange() + ".\n");
            }
            if (portfolio.get(i).getFiveDayFloat() < 0) {
                report.append("The five-day change analysis for " + portfolio.get(i).getStock() + " reveal that the stock has gotten lower over the course of a business week with a change of: " + portfolio.get(i).getFiveDay() + ".\n");
            }
            else {
                report.append("The five-day change analysis for " + portfolio.get(i).getStock() + " reveal that the stock has gotten higher over the course of a business week with a change of: " + portfolio.get(i).getFiveDay() + ".\n");
            }
            if (portfolio.get(i).getPercentChangeFloat() >= 1.50) {
                report.append("After analyzing the relevant information we would advise you to sell any shares of " + portfolio.get(i).getStock() + " that you may be holding as you will return a profit.\n");
            }
            if (portfolio.get(i).getPercentChangeFloat() >= 1.00) {
                report.append("After analyzing the relevant information we would advise you to sell any shares of " + portfolio.get(i).getStock() + " that you may be holding as you will return a profit. However, you could alternatively hold and see if the stock will go up anymore.\n");
            }
            if (portfolio.get(i).getPercentChangeFloat() < 1.00 && portfolio.get(i).getPercentChangeFloat() >= -1.00) {
                report.append("After analyzing the relevant information we would advise you to hold any shares of " + portfolio.get(i).getStock() + " that you may be holding as you will not return a good profit if you sell at this point.\n");
            }
            if (portfolio.get(i).getPercentChangeFloat() < -1.00 && portfolio.get(i).getPercentChangeFloat() >= -1.50) {
                report.append("After analyzing the relevant information we would advise you to moderately buy any shares of " + portfolio.get(i).getStock() + " that you are willing to as they may return a decent profit in a short while.\n");
            }
            if (portfolio.get(i).getPercentChangeFloat() < -1.50) {
                report.append("After analyzing the relevant information we would advise you to buy any shares of " + portfolio.get(i).getStock() + " that you can as there will be a high probability that you will return a profit from this trade.\n");
            }
            report.append("\n");
        }
    }

    /** Constructs the email report and asks for the user's desired email. */
    public static void constructEmail() {
        System.out.print("What is your desired email?: ");
        try (Scanner scan = new Scanner(System.in)) {
            String user_email = scan.nextLine();
            ereport = new EmailReport(user_email, report);
        }
    }
}
