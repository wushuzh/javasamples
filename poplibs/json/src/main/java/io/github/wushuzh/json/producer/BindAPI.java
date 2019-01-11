package io.github.wushuzh.json.producer;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.wushuzh.json.pojo.ExampleLoan;
import io.github.wushuzh.json.pojo.LoanApplication;

public class BindAPI {
  public static void main(String[] args) throws IOException {
    LoanApplication loanApplication = ExampleLoan.LOAN_APPLICATION;
    System.out.println(loanApplication);
    System.out.println("Bind API generated Json String:");
    toJsonString(loanApplication);
  }

  private static void toJsonString(LoanApplication loanApplication) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    String jsonString = objectWriter.writeValueAsString(loanApplication);
    System.out.println(jsonString);
  }
}
