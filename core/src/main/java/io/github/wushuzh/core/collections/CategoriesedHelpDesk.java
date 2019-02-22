package io.github.wushuzh.core.collections;

import java.util.ArrayDeque;
import java.util.Queue;

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

  public void processPrinterEnquiry() {
    Enquiry enquiry = enquiries.peek();
    if (enquiry != null && enquiry.getCategory() == Category.PRINTER) {
      enquiries.remove();
      enquiry.getCustomer().reply("Is it out of paper?");
    } else {
      System.out.println("No work to do, let's have some coffee!");
    }
  }

  public void processOtherEnquiry() {
    Enquiry enquiry = enquiries.peek();
    if (enquiry != null && enquiry.getCategory() != Category.PRINTER) {
      enquiries.remove();
      enquiry.getCustomer().reply("Have you tried turning it off and on again?");
    } else {
      System.out.println("No work to do, let's have some coffee!");
    }
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
