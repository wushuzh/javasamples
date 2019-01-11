package io.github.wushuzh;

import io.github.wushuzh.json.pojo.ExampleLoan;
import io.github.wushuzh.json.pojo.LoanApplication;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");
    LoanApplication loanApplication = ExampleLoan.LOAN_APPLICATION;
    System.out.println(loanApplication);
  }
}
