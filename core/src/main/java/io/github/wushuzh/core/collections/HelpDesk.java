package io.github.wushuzh.core.collections;

public class HelpDesk {
  public void enquire(final Customer customer, final Category category) {

  }

  public void processAllEnquiries() {

  }

  public static void main(String[] args) {
    HelpDesk helpDesk = new HelpDesk();

    helpDesk.enquire(Customer.JACK, Category.PHONE);
    helpDesk.enquire(Customer.JILL, Category.PRINTER);

    helpDesk.processAllEnquiries();
  }
}
