package io.github.wushuzh.json.pojo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static java.time.Month.JANUARY;

public class ExampleLoan {

  public static final LoanApplication LOAN_APPLICATION;

  static {
    LOAN_APPLICATION = new LoanApplication();
    LOAN_APPLICATION.setName("Mr wushuzh");
    LOAN_APPLICATION.setPurposeOfLoan("To build an extension of my house");

    final List<Job> jobs = new ArrayList<>();
    Job job = new Job();
    job.setTitle("Freelance Developer");
    job.setActiveYears(3);
    job.setAnnualIncome(18000);
    jobs.add(job);

    job = new Job();
    job.setTitle("Part time Developer");
    job.setActiveYears(8);
    job.setAnnualIncome(80000);
    jobs.add(job);

    job = new Job();
    job.setTitle("Blog author");
    job.setActiveYears(1);
    job.setAnnualIncome(15000);
    jobs.add(job);

    LOAN_APPLICATION.setJobs(jobs);

    final LoanDetails loanDetails = new LoanDetails();
    loanDetails.setAmount(10000);
    loanDetails.setStartDate(LocalDate.of(2019, JANUARY, 11));
    loanDetails.setEndDate(LocalDate.of(2024, JANUARY, 11));

    LOAN_APPLICATION.setLoanDetails(loanDetails);

  }

}
