import java.util.*;
abstract class BankAccount{//abstract super class
	
	protected Double balance;//instance variable .Holds the amount in the account.
	public BankAccount() {//Default constructor.Creates an account with 0 balance.
		balance=0.0;
	}
	 public BankAccount(double initialAmount) {
		    balance = initialAmount;
		  }

		  /**
		   * Deposits into the bank account.
		   * @param amount the amount to be deposited
		   */
		  public abstract void deposit(double amount) ;//abstract method
		   
		  /**
		   * Withdraws from the bank account.
		   * @param amount the amount to withdraw
		   */
		  
		  public void withdraw(double amount) {
			  if(amount>0) {
					if(amount<=balance) {
						System.out.printf("Amount of %.2f withdrawn from Account%n", amount);
						balance-=amount;//balance=balance-amount
						System.out.printf("Current balance is:%.2f%n", balance);
						
					}
					else {
						System.out.println("Balance isn't enough!");
					}
				}
		  }
		  /**
		   * @return the current balance in the account.
		   */
		  public double getBalance() {
		    return balance;
		  }
		}

class savings_Account extends BankAccount{
	private double interestRate;//Holds the interest rate for the period.
	 /**
	   *  Constructor that sets interest rate.
	   *  @param interestRate the interest rate paid by the account
	   */
	  public savings_Account(double interestRate) {
	    this.interestRate = interestRate;
	  }
	 
	  /**
	   *  Constructor that sets rate and initial balance.
	   * @param interestRate the interest rate paid by the account
	   * @param initialAmount the amount in the account when it is created
	   */
	  public savings_Account(double interestRate, double initialAmount) {
	    super(initialAmount);  // Calls constructor of BankAccount.
	    this.interestRate = interestRate;
	  }
	
	public void deposit(double amount) {
		if(amount>0) {
			balance+=amount;//balance=balance+amount
			System.out.printf("Amount %.2f deposited%n", amount);
			System.out.printf("Your Current balance is:%.2f%n", balance);
			
		}
		else {
			System.out.println("A negative amount can't be deposited.");
		}
		
	}
	
	/**
	   * Add interest for the current period to the account balance.
	   */
	  public void addPeriodicInterest() {
	    double interest = getBalance() * interestRate / 100.0;
	    deposit(interest);
	  }
	  /**
	   * @return the estimated periodic interest..
	   */
	  public double getInterest() {
		  return getBalance() * interestRate / 100.0;
	  }
	
}
class Checking_Account extends BankAccount {
	  private static final double TRANSACTION_FEE = 0.50;// static ,final instance variable.
	  private static final int FREE_TRANSACTIONS = 3;//static,final instance variable.
	  private int transactionCount; // Number of transactions this period.

	  /**
	   *  Default constructor
	   */
	  public Checking_Account() {
	    transactionCount = 0;
	  }
	  /**
	   * Constructor to create an account with an initial balance.
	   * @param initialAmount the initial balance in the account
	   */
	  public Checking_Account(double initialAmount) {
	    super(initialAmount);//calls the constructor of the super class.
	    transactionCount = 0;
	  
	  }

	 
	  /**
	   * Withdraw from account, but count as a transaction.
	   * @param amount the amount to withdraw
	   */
	  public void withdraw(double amount) {//overrides the method in BankAccount class
	    super.withdraw(amount);
	    transactionCount++;
	  }
	  /**
	   * Deposit to account, but count as a transaction.
	   * @param amount the amount to withdraw
	   */
	  public void deposit(double amount) {
		  if(amount>0) {
				balance+=amount;
				System.out.printf("Amount %.2f deposited%n", amount);
				System.out.printf("Your Current balance is:%.2f%n", balance);
				
			}
			else {
				System.out.println("A negative amount can't be deposited.");
			}
		  transactionCount++;
	}
	  /**
	   *  Charge transaction fees (if any) to the account
	   */
	  public void deductFees() {
	    if (transactionCount > FREE_TRANSACTIONS) {
	      double fee = TRANSACTION_FEE * (transactionCount - FREE_TRANSACTIONS);
	      super.withdraw(fee);
	    }
	    transactionCount = 0; // Start over because new time period.
	  }
}
public class test {
	  public static void main(String[] args) {
	   
		  savings_Account[] customer=new savings_Account[2];//instantiate an array(customer) of size 2
		  for(int i=0;i<customer.length;i++) {
			  Scanner input=new Scanner(System.in);
			  System.out.println("Enter initial amount: ");//prompts the user to enter an initial amount.
			  Double initAmount=input.nextDouble();
			  System.out.println("Enter interest rate: ");//prompts the user to enter an interest rate.
			  Double intRate=input.nextDouble();
			  customer[i]=new savings_Account(intRate,initAmount);
			  
		  }
		  for(int i=0;i<customer.length;i++) {
			  
				System.out.println("customer["+i+"]" +" "+ "has an initial balance of �"+ customer[i].getBalance() +" "+ "and an estimated interest of �" + " " + customer[i].getInterest());
			}
		  System.out.println("--------------------------------------------------------------------------------------------------");
		 
		 
		  /**
		   * Demonstrate variable polymorphism.
		   */
		  BankAccount bettysSavings = new savings_Account(0.5);//A savings_Account object
		  
		  BankAccount bettysChecking = new Checking_Account();//A Checking_Account object 
		  
	         System.out.println("SAVINGS ACCOUNT DEMO");
		     bettysSavings.deposit(1000.00);
	         bettysSavings.withdraw(100.00);
	         endOfMonth((savings_Account)bettysSavings);//type-casted
	         
	         System.out.println("-----------------------------------------------------------------------------------------------");
	    
	         System.out.println("CHECKING ACCOUNT DEMO");
	         bettysChecking.deposit(5000);
	         bettysChecking.deposit(8000);
	         bettysChecking.withdraw(5000);
	         bettysChecking.withdraw(300);
             endOfMonth((Checking_Account)bettysChecking);//type-casted
	  }
	  /** 
	   * Handles end of month interest for a savings account
	   * @param savings the savings account to handle
	   */
	  public static void endOfMonth(savings_Account savings) {//overloaded method
	    savings.addPeriodicInterest();
	  }

	  /** 
	   * Handles end of month fee deduction for a checking account
	   * @param checking the checking account to handle
	   */
	  public static void endOfMonth(Checking_Account checking) {//overloading method
	    checking.deductFees();
	  }
	}
