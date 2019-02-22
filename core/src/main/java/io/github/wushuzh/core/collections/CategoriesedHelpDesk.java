package io.github.wushuzh.core.collections;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Predicate;

public class CategoriesedHelpDesk {
  private final Queue<Enquiry> enquiries = new ArrayDeque<>();

  public void enquire(final Customer customer, final Category category) {
    enquiries.offer(new Enquiry(customer, category));
  }

  @Deprecated
  public void processAllEnquiries() {
//    while (!enquiries.isEmpty()) {
//      final Enquiry enquiry = enquiries.remove();
//      enquiry.getCustomer().reply("Have U tried turning it off and on again?");
//    }

    Enquiry enquiry;
    while ((enquiry = enquiries.poll()) != null) {
      enquiry.getCustomer().reply("Have you tried turning it off and on again?");
    }
  }

  private void processEnquiry(final Predicate<Enquiry> predicate, final String msg) {
    final Enquiry enquiry = enquiries.peek();
    if (enquiry != null & predicate.test(enquiry)) {
      enquiry.getCustomer().reply(msg);
      enquiries.remove();
    } else {
      System.out.println("No work to do, let's have some coffee!");
    }
  }

  public void processPrinterEnquiry() {
    processEnquiry(
        enq -> enq.getCategory() == Category.PRINTER,
        "Is it out of paper");
  }

  public void processOtherEnquiry() {
    processEnquiry(
        enq -> enq.getCategory() != Category.PRINTER,
        "Have you tried turning it off and on again?");
  }

  public static void main(String[] args) {
    CategoriesedHelpDesk helpDesk = new CategoriesedHelpDesk();

    helpDesk.enquire(Customer.JACK, Category.PHONE);
    helpDesk.enquire(Customer.JILL, Category.PRINTER);

//    helpDesk.processAllEnquiries();
    helpDesk.processPrinterEnquiry();
    helpDesk.processOtherEnquiry();
    helpDesk.processPrinterEnquiry();
  }
}
