package com.ticket.defination;

import java.util.UUID;

import org.apache.commons.validator.routines.EmailValidator;

public class Validator {
	public static boolean isValidEmail(String email) {
		if (email == null || "".equals(email))
			return false;

		email = email.trim();

		EmailValidator ev = EmailValidator.getInstance();
		return ev.isValid(email);
	}
	
	public static boolean isValidNo(String no) {
		if(no == null){
			return false;
		}
		try {
			Integer.parseInt(no);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	public static boolean validateCustomer(String input_customer, String stored_customer){
		if(input_customer == null || stored_customer == null){
			return false;
		}
		return input_customer.equalsIgnoreCase(stored_customer);
	}
	public static String reservationCode(SeatHold hold){
		StringBuilder sb = new StringBuilder();
		sb.append("Your seats have been reserved!\n");
		sb.append("Information:\n");
		sb.append("Confirmation Number: " + UUID.randomUUID().toString() + "\n");
		sb.append("seats: [ ");
		for(Seat st: hold.getSeatsHeld()){
			sb.append(st.getSeatNo()); sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
}
