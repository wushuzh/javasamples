package io.github.wushuzh.json.pojo;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class LoanDetails {

  @Getter
  @Setter
  private double amount;
  @Getter
  @Setter
  private LocalDate startDate;
  @Getter
  @Setter
  private LocalDate endDate;

}
