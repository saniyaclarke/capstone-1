package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PennyTrackApplication {
    public static void main(String[] args) {
        ArrayList<Transaction> transactions = loadFile();

        Scanner input = new Scanner(System.in);

        mainMenu(transactions, input);

    }


    public static void mainMenu(ArrayList<Transaction> transactionList, Scanner input) {
        String option;

        System.out.println(""" 
                D) Add Deposit
                
                P) Make Payment (Debit)
                
                L) Ledger
                
                X)Exit
                
                Please enter option :
                """);
        option = input.nextLine().trim().toUpperCase();

        while (!option.equals("X")) {
            switch (option) {

                case "D":
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

                    //Now we have to store the new object
                    transactionList.add(transaction);

                    saveTransaction(transaction);
                    break;


                case "P":
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
                    transactionList.add(debitTransaction);

                    saveTransaction(debitTransaction);
                    break;

                case "L":
                    System.out.println("Selection: Ledger");

                    System.out.println(""" 
                            A) All
                            
                            D) Deposits
                            
                            P) Payments
                            
                            R) Reports
                            
                            Please enter choice :
                            """);

                    String ledgerOption = input.nextLine().trim().toUpperCase();

                    switch (ledgerOption) {
                        case "A":
                            for (Transaction transactions : transactionList) {
                                System.out.println(transactions);

                            }
                            break;

                        case "D":
                            for (Transaction transactions : transactionList) {
                                if (transactions.getAmount() > 0) {
                                    System.out.println(transactions);
                                }
                            }
                            break;


                        case "P":
                            for (Transaction transactions : transactionList) {
                                if (transactions.getAmount() < 0) {
                                    System.out.println(transactions);
                                }
                            }

                            break;

                        case "R":
                         //   if (){

                          //  }
                            break;

                    }
                    break;


                case "X":
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("You have not selected one of the options above. Please try again.");
                    break;
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
                System.out.println(line);

                String[] parts = line.split(Pattern.quote("|"));
                ///creates parts[0],parts[1],parts[2],parts[3],parts[4]

                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(parts[0], parts[1], parts[2], parts[3], amount);

                transactionArrayList.add(transaction);


                line = bufferedReader.readLine();

            }

        } catch (IOException e) {
            System.out.println("Problem reading file.");
        }

        System.out.println(transactionArrayList.size());

        return transactionArrayList;

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


