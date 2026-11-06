package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PennyTrackApplication {
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<Transaction> transactionsList = loadFile();

    public static void main(String[] args) {

        mainMenu();

    }

    public static void mainMenu() {
        String option;


        do {
            System.out.println(""" 
                    \nMain Menu
                    
                    D) Add Deposit
                    
                    P) Make Payment (Debit)
                    
                    L) Ledger
                    
                    X)Exit
                    
                    Please enter option :
                    """);
            option = input.nextLine().trim().toUpperCase();

            switch (option) {

                case "D":
                    addDeposit();
                    break;

                case "P":
                    addPayment();

                    break;

                case "L":
                    ledgerMenu();
                    break;


                case "X":
                    System.out.println("\nExit ");
                    break;

                default:
                    System.out.println("\nYou have not selected one of the options above. Please try again.");
                    break;
            }
        }
        while (!option.equals("X"));
    }

    public static void ledgerMenu() {
        String ledgerOption;

        do {
            System.out.println(""" 
                    \nLedger Menu
                    
                    A) All
                    
                    D) Deposits
                    
                    P) Payments
                    
                    R) Reports
                    
                    H) Main Menu
                    
                    Please enter choice :
                    """);

            ledgerOption = input.nextLine().trim().toUpperCase();

            switch (ledgerOption) {
                //displays all transactions by calling display method
                case "A":
                    for (Transaction transaction : transactionsList) {
                        transaction.display();
                    }
                    break;

                //displays debit transaction by calling display method
                case "D":
                    for (Transaction transaction : transactionsList) {
                        if (transaction.getAmount() > 0) {
                            transaction.display();
                        }
                    }
                    break;

                //displays payment transactions by calling display method
                case "P":
                    for (Transaction transaction : transactionsList) {
                        if (transaction.getAmount() < 0) {
                            transaction.display();
                        }
                    }
                    break;

                case "R":
                    reportSubMenu();
                    // subMenu();
                    break;

                case "H":
                    System.out.println(" Returning to Main Menu.");
                    break;

            }
        } while (!ledgerOption.equals("H"));


    }

    public static void RunMonthToDate() {
        System.out.println("\nSelection: Month To Date ");
        LocalDate runDate = LocalDate.now();

        LocalDate startDate = runDate.withDayOfMonth(1);

        for (Transaction transaction : transactionsList) {

            //converts transaction date from a string type variable into a localdate type variable to access the isbeforre/isafter/isequal methods
            String reportDate = transaction.getDate();
            LocalDate reportDateLocal = LocalDate.parse(reportDate);

            if (!reportDateLocal.isBefore(startDate) && !reportDateLocal.isAfter(runDate)) {
                transaction.display();

            }
        }

    }

    public static void reportSubMenu() {
        String reportSelection;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
        do {
            System.out.println("\nSelection: Reports");
            System.out.println("""
                      \n1) Month To Date
                      2) Previous Month
                      3) Year To Date
                      4) Previous Year
                      5) Search by Vendor - prompt the user for the vendor name and
                         display all entries for that vendor
                      0) Back - go back to the Ledger page
                    """);

            reportSelection = input.nextLine();

            switch (reportSelection) {

                case "1":
                    RunMonthToDate();
                    //RunMonthToDate()

                    break;

                case "2":
                    runPreviousMonth();
                    //RunPreviousMonth()
                    break;

                case "3":
                    RunYearToDate();
                    //RunYearToDate()
                    break;

                case "4":
                    RunPreviousYear();
                    //RunPreviousYear()

                    break;

                case "5":
                    SearchbyVendor();
                    //SearchbyVendor()
                    break;

                case "0":
                    System.out.println("Selection: Back ");
                    break;


            }

        } while (!reportSelection.equals("0"));

    }

    public static void runPreviousMonth() {
        System.out.println("\nSelection: Previous Month ");

        //Need current date to compare to transaction date
        LocalDate runDate = LocalDate.now();
        //stores value of previous month in a LocalDate variable previousMonth
        LocalDate previousMonth = runDate.minusMonths(1);
        //run date, then set value -1 month, change day of month to 1 stores in startDate
        LocalDate startDate = previousMonth.withDayOfMonth(1);
        //run date, then set value -1 month, change day of month to length of said month stores in startDate
        LocalDate endDate = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth());

        // int monthValue = reportDateLocal.getMonthValue(); - can do it with ints as well (figure out)
        //int previousMonthValue = monthValue.

        for (Transaction transaction : transactionsList) {

            //conversion (transaction date)
            String reportDate = transaction.getDate();
            LocalDate reportDateLocal = LocalDate.parse(reportDate);

            if (!reportDateLocal.isBefore(startDate) && !reportDateLocal.isAfter(endDate)) {
                transaction.display();

            }
        }


    }

    public static void RunYearToDate() {
        LocalDate runDate = LocalDate.now();

        //Sets day to Jan 1(1st day of year)
        LocalDate startDate = runDate.withDayOfYear(1);

        for (Transaction transaction : transactionsList) {

            String reportDate = transaction.getDate();
            LocalDate reportDateLocal = LocalDate.parse(reportDate);

            if (!reportDateLocal.isBefore(startDate) && !reportDateLocal.isAfter(runDate)) {
                transaction.display();

            }
        }


    }

    public static void RunPreviousYear() {
        LocalDate runDate = LocalDate.now();

        LocalDate previousYear = runDate.minusYears(1);

        LocalDate startDate = previousYear.withDayOfYear(1);

        LocalDate endDate = previousYear.withDayOfYear(previousYear.lengthOfYear());

        for (Transaction transaction : transactionsList) {
            String reportYear = transaction.getDate();
            LocalDate reportYearLocal = LocalDate.parse(reportYear);

            if (!reportYearLocal.isBefore(startDate) && (!reportYearLocal.isAfter(endDate))) {
                transaction.display();

            }

        }
    }

    public static void SearchbyVendor() {

        System.out.println("Please enter Vendor Name: ");

        String vendorInput = input.nextLine();

        for (Transaction transaction : transactionsList) {

            String vendorName = transaction.getVendorName();

            if (vendorInput.equalsIgnoreCase(vendorName)) {
                transaction.display();
            }
        }
    }

    public static ArrayList<Transaction> loadFile() {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("src/main/resources/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            // System.out.println(line); (checkpoint)

            line = bufferedReader.readLine();
            //This allows me to skip th header/first line so everything compiles correctly

            while (line != null) {
                //System.out.println(line);

                String[] parts = line.split(Pattern.quote("|"));
                ///creates parts[0],parts[1],parts[2],parts[3],parts[4]

                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(parts[0], parts[1], parts[2], parts[3], amount);

                //System.out.println(parts[0]+ parts[1]+ parts[2]+ parts[3]+ amount);
                transactionArrayList.add(transaction);


                line = bufferedReader.readLine();

            }

        } catch (IOException e) {
            System.out.println("Problem reading file.");
        }

        //System.out.println(transactionArrayList.size());

        return transactionArrayList;

    }

    public static void addDeposit() {

        System.out.println("Selection: Add deposit");

        System.out.println("Please enter the following: ");

        System.out.println("Description: ");
        String description = input.nextLine();

        System.out.println("Vendor: ");
        String vendor = input.nextLine();

        System.out.println("Amount: ");
        String amount = input.nextLine();

        double cost = Double.parseDouble(amount);
        cost = Math.abs(cost);

        LocalDateTime now = LocalDateTime.now();

        //converts local date and time into a string type
        String date = now.toLocalDate().toString();
        String time = now.toLocalTime().toString();

        //Now we have to create the Transaction (created new object)
        Transaction transaction = new Transaction(date, time, description, vendor, cost);

        //Now we have to store the new object this stores in memory
        transactionsList.add(transaction);

        //this saves and writes it to the csv file
        saveTransaction(transaction);
    }

    public static void addPayment() {
        System.out.println("Selection: Make Payment (Debit) ");

        System.out.println("Please enter the following: ");

        System.out.println("Description: ");
        String debitDescription = input.nextLine();

        System.out.println("Vendor: ");
        String debitVendor = input.nextLine();

        System.out.println("Amount: ");
        String debitAmount = input.nextLine();
        double debitCost = Double.parseDouble(debitAmount);
        debitCost = -Math.abs(debitCost);

        LocalDateTime debitNow = LocalDateTime.now();

        //converts local date and time into a string type
        String debitDate = debitNow.toLocalDate().toString();
        String debitTime = debitNow.toLocalTime().toString();

        //Now we have to create the Transaction (created new object)
        Transaction debitTransaction = new Transaction(debitDate, debitTime, debitDescription, debitVendor, debitCost);

        //Now we have to store the new object
        transactionsList.add(debitTransaction);

        saveTransaction(debitTransaction);
    }

    public static void saveTransaction(Transaction addedTransaction) {

        try {
            String line = addedTransaction.getDate() + "|" + addedTransaction.getTime() + "|" + addedTransaction.getDescription() + "|" + addedTransaction.getVendorName() + "|" + addedTransaction.getAmount();
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true);
            fileWriter.write(line + "\n");

            fileWriter.close();
            System.out.println("Transaction Saved");

        } catch (IOException e) {
            System.out.println("Issue with file,");
        }
    }

    public static void sortTransactions() {

        //Fairly new concept how to sort by  date and time
        transactionsList.sort((Transaction t1, Transaction t2) -> {

            int result = t2.getDate().compareTo(t1.getDate());

            if (result == 0)
                return t2.getTime().compareTo(t1.getTime());
            return result;

        });
    }
}




/// public static void displayTransaction() { unused display method that can display transactions as well.
///
///         try {
///             FileReader fileReader = new FileReader("src/main/resources/transactions.csv");
///             BufferedReader bufferedReader = new BufferedReader(fileReader);
///
///             String line = bufferedReader.readLine();
///             // System.out.println(line); (checkpoint)
///
///             line = bufferedReader.readLine();
///             //This allows me to skip th header/first line so everything compiles correctly
///
///             while (line != null) {
///                 System.out.println(line);
///
///                 String[] parts = line.split(Pattern.quote("|"));
///                 ///creates parts[0],parts[1],parts[2],parts[3],parts[4]
///
///                 double amount = Double.parseDouble(parts[4]);
///
///                 //Transaction transaction = new Transaction(parts[0], parts[1], parts[2], parts[3], amount);
///
///                 System.out.println(parts[0] + parts[1] + parts[2] + parts[3] + amount);
///                 //transactionArrayList.add(transaction);
///
///
///                 line = bufferedReader.readLine();
///
///             }
///
///         } catch (IOException e) {
///             System.out.println("Problem reading file.");
///         }
///
///     }
