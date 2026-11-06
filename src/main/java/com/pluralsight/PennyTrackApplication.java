package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
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
                    System.out.println("Exit ");
                    break;

                default:
                    System.out.println("You have not selected one of the options above. Please try again.");
                    break;
            }
        }
        while (!option.equals("X \n"));
    }

    public static void ledgerMenu() {
        String ledgerOption;

        System.out.println("Selection: Ledger");

        System.out.println(""" 
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
                for (Transaction transaction : transactionsList)
                    transaction.display();
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
                // subMenu();

                String reportSelection;

                System.out.println("Selection: Reports");
                System.out.println("""
                          1) Month To Date
                          2) Previous Month
                          3) Year To Date
                          4) Previous Year
                          5) Search by Vendor - prompt the user for the vendor name and
                             display all entries for that vendor
                          0) Back - go back to the Ledger page
                        """);

                reportSelection = input.nextLine();

                switch (reportSelection) {

                    case "1" :
                        System.out.println("Selection: Month To Date ");
                        break;

                    case "2" :
                        System.out.println("Selection: Previous Month ");
                        break;

                    case "3" :
                        System.out.println("Selection: Year To Date ");
                        break;

                    case "4" :
                        System.out.println("Selection: Previous Year ");
                        break;

                    case "5" :
                        System.out.println("Selection:  ");
                        break;

                    case "0" :
                        System.out.println("Selection: Back ");
                        break;


                }



























                break;


            case "H":
                System.out.println(" Returning to Main Menu.");
                mainMenu();
                break;

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
