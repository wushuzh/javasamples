package io.github.wushuzh.core.collections;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Predicate;

public class CategoriesedHelpDesk {

  private static final Comparator<Enquiry> BY_CATEGORY = new Comparator<Enquiry>() {
    @Override
    public int compare(Enquiry o1, Enquiry o2) {
      return o1.getCategory().compareTo(o2.getCategory());
    }
  };
  private final Queue<Enquiry> enquiries = new PriorityQueue<>(BY_CATEGORY);

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
      enquiry.getCustomer().reply(
          String.format("Have you tried turning %s off and on again?", enquiry.getCategory()));
    }
  }

  private void processEnquiry(final Predicate<Enquiry> predicate, final String msg) {
    final Enquiry enquiry = enquiries.peek();
    if (enquiry != null && predicate.test(enquiry)) {
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
    helpDesk.enquire(Customer.JACK, Category.PRINTER);
    helpDesk.enquire(Customer.JILL, Category.PRINTER);
    helpDesk.enquire(Customer.MARY, Category.COMPUTER);

    helpDesk.processAllEnquiries();
// Priority order is Printer -> Computer -> PHONE -> TABLET
//    Jack: Have you tried turning PRINTER off and on again?
//    Jill: Have you tried turning PRINTER off and on again?
//    Mary: Have you tried turning COMPUTER off and on again?
//    Jack: Have you tried turning PHONE off and on again?

    helpDesk.processPrinterEnquiry();
    helpDesk.processOtherEnquiry();
    helpDesk.processPrinterEnquiry();
  }
}
