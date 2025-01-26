package main.java.com.doctor_appointments.availability.shared.exceptions;

public class SlotAlreadyReservedException extends Exception {
  public SlotAlreadyReservedException(String message) {
    super(message);
  }

}
