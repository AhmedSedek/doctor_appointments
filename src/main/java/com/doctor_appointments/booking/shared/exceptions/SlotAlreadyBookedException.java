package main.java.com.doctor_appointments.booking.shared.exceptions;

public class SlotAlreadyBookedException extends Exception {
  public SlotAlreadyBookedException(String message) {
    super(message);
  }

}
