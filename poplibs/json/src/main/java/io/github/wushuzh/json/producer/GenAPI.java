package io.github.wushuzh.json.producer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import io.github.wushuzh.json.pojo.ExampleLoan;
import io.github.wushuzh.json.pojo.Job;
import io.github.wushuzh.json.pojo.LoanApplication;
import io.github.wushuzh.json.pojo.LoanDetails;

public class GenAPI {
  public static void main(String[] args) throws IOException {
    LoanApplication loanApplication = ExampleLoan.LOAN_APPLICATION;
    System.out.println(loanApplication);
    System.out.println();
    System.out.println(toJsonString(loanApplication));

  }

  public static String toJsonString(LoanApplication loanApplication) throws IOException {

    StringWriter stringWriter = new StringWriter();

    JsonFactory jsonFactory = new JsonFactory();
    JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter);
    jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());

    jsonGenerator.writeStartObject();

    jsonGenerator.writeStringField("name", loanApplication.getName());
    jsonGenerator.writeStringField("purposeOfLoan", loanApplication.getPurposeOfLoan());

    toJsonString(jsonGenerator, loanApplication.getLoanDetails());

    toJsonString(jsonGenerator, loanApplication.getJobs());

    jsonGenerator.writeEndObject();
    jsonGenerator.close();

    return stringWriter.toString();
  }

  private static void toJsonString(JsonGenerator jsonGenerator, List<Job> jobs) throws IOException {
    jsonGenerator.writeFieldName("jobs");
    jsonGenerator.writeStartArray();
    for (Job job : jobs) {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeStringField("title", job.getTitle());
      jsonGenerator.writeNumberField("activeYears", job.getActiveYears());
      jsonGenerator.writeNumberField("annualIncome", job.getAnnualIncome());
      jsonGenerator.writeEndObject();
    }
    jsonGenerator.writeEndArray();


  }

  private static void toJsonString(JsonGenerator jsonGenerator, LoanDetails loanDetails)
      throws IOException {
    jsonGenerator.writeFieldName("loanDetails");
    jsonGenerator.writeStartObject();
    jsonGenerator.writeNumberField("amount", loanDetails.getAmount());
    jsonGenerator.writeStringField("startDate", loanDetails.getStartDate().toString());
    jsonGenerator.writeStringField("endDate", loanDetails.getEndDate().toString());
    jsonGenerator.writeEndObject();
  }

}
