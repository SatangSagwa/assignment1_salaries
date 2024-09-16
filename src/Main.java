/*
WHILE option != Q:
    OUTPUT - print menu
    INPUT - menu option 1-3 or Q to quit
    SWITCH - menu option.
        CASE1: Pay salaries
            - OUTPUT prompt
            - INPUT int - amount of employees to pay salaries to
            - ARR - create of size "amount of employees input"
            - FOR - iterate the employee array
                - OUTPUT - salary amount prompt for each element
                - INPUT - salary for each element
                - CALC - total value of each element -30% tax
                - IF total < account balance
                    - CALC subtract total from account balance
                    - CALC current element after tax
                    - OUTPUT each salary after 30% tax reduction
                - ELSE
                    - OUTPUT message - not enough balance

        CASE2: Create new invoice
            - OUTPUT prompt
            - INPUT invoice amount
            - CALC Sales tax
            - CALC net value
            - OUTPUT gross, tax and net
            - CALC add net value to account balance
            - OUTPUT new account balance

        CASE3: Invoice payment
            - OUTPUT prompt
            - INPUT amount of invoices to pay
            - ARRAY - created of size "amount of invoices"
            - OUTPUT prompt - invoice sum
            - FOR - iterate array of invoices
                - OUTPUT invoice ID
                - INPUT invoice sum for each ID
                - CALC add invoice sum to total variable
            - IF total <= account balance
                - OUTPUT total
                - CALC tax
                - CALC total after tax
                - OUTPUT tax
                - OUTPUT total after tax
                - OUTPUT account balance
                - CALC account balance - total after tax
                - OUTPUT account balance
            - ELSE
                - OUTPUT not enough balance
                - OUTPUT total sum
                - OUTPUT account balance
                - OUTPUT payment cancelled

        CASE QUIT, "q":
            - Terminate.

METHOD IntInput - handles all int input in the scanner.
    - WHILE
        - IF scanner hasNext
            - TRY return next int
            - CATCH inputMismatch
                - IF scanner.next == "q"
                    return "q"
                - ELSE
                    - scanner.nextLine - "consume" input line
                    - OUTPUT invalid input
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    //Constants
    private static final String OPTIONS_STR = "Enter your option or Q to quit: \n1. Payout Salaries \n2. Create new invoice \n3. New invoice payment\n";

    private static final String EMPLOYEE_AMOUNT_STR = "How many employees do you want to payout salaries to?";
    private static final String INVOICES_ANOUNT_STR = "How many invoices do you want to pay?";

    private static final String SALARY_AMOUNT_STR = "Enter employee %d's salary: ";
    private static final String TOTAL_INVOICE_AMOUNT_STR = "Enter the total invoice amount: ";
    private static final String INVOICE_PROMPT_STR = "Enter the invoice sum: ";

    private static final String SALARY_AFTER_TAX_STR = "Employee %d´s salary after tax: ";
    private static final String GROSS_STR = "Gross Value: ";
    private static final String SALES_TAX_STR = "Sales Tax: ";
    private static final String NET_STR = "Net Value: ";
    private static final String ACCOUNT_BALANCE_STR = "Account balance: ";

    private static final String NOT_ENOUGH_BALANCE_STR = "The specified amount exceeds the account balance!";
    private static final String INVALID_INPUT_STR = "Invalid input, please try again.";
    private static final String INVALID_OPTION_STR = "Invalid option. Try again.";
    private static final String QUIT_STR = "q";


    //Menu options
    private static final int OPTION_1 = 1;
    private static final int OPTION_2 = 2;
    private static final int OPTION_3 = 3;
    private static final int QUIT = -1;
    //Array ID
    private static final int SALARY_ID = 1;
    //Magic numbers
    private static final double TAX_REDUCTION = 0.7; // = 30% off
    private static final double INVOICE_REDUCTION = 0.25; // = 25% off
    private static final double START_BALANCE = 5000;


    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        double account_balance = START_BALANCE;
        int option = 0;

        while(option != QUIT) {
            System.out.printf(OPTIONS_STR);
            option = IntInput();
            scanner.nextLine();

            switch (option) {
                //Pay salaries
                case OPTION_1:
                    //Get amount of salaries to pay
                    System.out.println(EMPLOYEE_AMOUNT_STR);
                    int amount_of_employees = IntInput();
                    //Array of the same sixe as specified amount
                    int[] salaries = new int[amount_of_employees];
                    double total = 0;

                    for (int i = 0; i < amount_of_employees; i++) {
                        //Get salary amount for each employee
                        System.out.printf(SALARY_AMOUNT_STR, (i + 1));
                        salaries[i] = IntInput();
                        total = total + (salaries[i] * TAX_REDUCTION);
                    }

                    if (account_balance > total) {
                        //Subtract the total from balance
                        account_balance = account_balance - total;
                        for (int j = 0; j < amount_of_employees; j++) {
                            double after_tax = salaries[j] * TAX_REDUCTION;
                            System.out.printf(SALARY_AFTER_TAX_STR + Math.round(after_tax) + "\n", j + 1);
                        }
                        System.out.println("New account balance: " + account_balance);
                    } else {
                        System.out.println(NOT_ENOUGH_BALANCE_STR);
                        System.out.println("Requested amount: " + total);
                        System.out.println(ACCOUNT_BALANCE_STR + account_balance);
                    }
                    break;

                //Create new invoice
                case OPTION_2:
                    System.out.println(TOTAL_INVOICE_AMOUNT_STR);
                    int invoice_value = IntInput();
                    //Calculate tax and net value
                    double sales_tax = invoice_value * INVOICE_REDUCTION;
                    double net_value = invoice_value - sales_tax;
                    System.out.println(GROSS_STR + invoice_value);
                    System.out.println(SALES_TAX_STR + Math.round(sales_tax));
                    System.out.println(NET_STR + Math.round(net_value));
                    //Add net to balance
                    account_balance += net_value;
                    System.out.println(ACCOUNT_BALANCE_STR + Math.round(account_balance) + "\n");
                    break;

                //New invoice payment
                case OPTION_3:
                    //Gets amount of invoices
                    System.out.println(INVOICES_ANOUNT_STR);
                    int invoice_amount = IntInput();
                    int[] invoices = new int[invoice_amount];
                    System.out.println(INVOICE_PROMPT_STR);
                    int total_sum = 0;

                    //Ask for amount of each invoice
                    for (int x = 0; x < invoice_amount; x++) {
                        System.out.printf("Invoice #" + (x+1) + ": ");
                        invoices[x] = IntInput();
                        total_sum += invoices[x];
                    }
                    if(total_sum <= account_balance) {
                        //Print result
                        System.out.println("Total: " + total_sum);
                        double total_sales_tax = Math.round(total_sum * INVOICE_REDUCTION);
                        double total_with_tax = total_sum - total_sales_tax;
                        System.out.println("Sales tax: " + total_sales_tax);

                        System.out.println("Total with tax: " + total_with_tax);
                        System.out.println("Previous balance: " + account_balance);

                        account_balance -= total_with_tax;
                        System.out.println("New balance: " + account_balance);

                    } else {
                        //Print error message
                        System.out.println(NOT_ENOUGH_BALANCE_STR);
                        System.out.println("Amount to withdraw: " + total_sum);
                        System.out.println(ACCOUNT_BALANCE_STR + account_balance);
                        System.out.println("Payment cancelled.");
                    }
                    break;

                case QUIT:
                    break;

                default:
                    System.out.println(INVALID_OPTION_STR);
                    break;
            }
        }
    }
    //Returns the int entered by user
    // - or q to quit
    // - or prints an error message and loops again until user enters an int
    private static int IntInput() {
        while (true) {
            if (scanner.hasNext()) {
                try {
                    return scanner.nextInt();
                } catch (InputMismatchException e) {
                    if (scanner.next().equalsIgnoreCase(QUIT_STR)) {
                        return QUIT;
                    }
                    else {
                        scanner.nextLine();
                        System.out.println(INVALID_INPUT_STR);
                    }
                }
            }
        }
    }
}