package main.java.com.doctor_appointments.availability.shared.exceptions;

public class SlotAlreadyReleasedException extends Exception {

  public SlotAlreadyReleasedException(String message) {
    super(message);
  }

}
