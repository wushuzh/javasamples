package io.github.wushuzh.json.pojo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class LoanApplication {
  @Getter
  @Setter
  private String name;
  @Getter
  @Setter
  private String purposeOfLoan;
  @Getter
  @Setter
  private List<Job> jobs;
  @Getter
  @Setter
  private LoanDetails loanDetails;
}
