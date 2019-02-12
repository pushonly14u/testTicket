package com.ticket.defination;

import java.util.Scanner;

import com.ticket.imple.TicketService;
import com.ticket.imple.TicketServiceImpl;


public class Start {
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("TicketService System");
		
		boolean loop = true;
		String options = "\nSelect One of them: \n1. Create/Reset \n2. Seats Left \n3. Hold seats \n4. Booking confirm \n5. Exit.";
		int rows = 5;
		int seatsProw = 5;
		Venue v = new Venue(rows, seatsProw);
		TicketService service = new TicketServiceImpl(v);
		System.out.println("System started with "+ rows + " rows & " + seatsProw + " seats per row venue! (Expiration seconds is set to 100 secs.))");
		while(loop) {
			System.out.println(options);
			String str = sc.next();
			boolean isvalidInput = Validator.isValidNo(str);
			if(!isvalidInput){
				System.out.println("Select only numbers.");
				continue;
			}
			int input = Integer.parseInt(str);
			switch(input){
			case 1:
				System.out.println("How many rows?");
				String xr = sc.next();
				boolean isvalidRow = Validator.isValidNo(xr);
				if(!isvalidRow){
					while(!isvalidRow){
						System.out.println("Invalid row no.");
						System.out.println("Enter valid no:");
						xr = sc.next();
						isvalidRow = Validator.isValidNo(xr);
					}
				}
				rows = Integer.parseInt(xr);
				System.out.println("How many seats per rows?");
				String xst = sc.next();
				boolean isvalidStPRw = Validator.isValidNo(xst);
				if(!isvalidStPRw){
					while(!isvalidStPRw){
						System.out.println("Invalid seat no.");
						System.out.println("Enter valid no:");
						xst = sc.next();
						isvalidStPRw = Validator.isValidNo(xst);
					}
				}
				seatsProw = Integer.parseInt(xst);
				System.out.println("Expiration seconds");
				int exp;
				try {
					exp = Integer.parseInt(sc.next());
				} catch (NumberFormatException e) {
					exp = -1;
				}
				v = new Venue(rows, seatsProw);
				service = (exp==-1)?new TicketServiceImpl(v):new TicketServiceImpl(v, exp);
				System.gc();
				break;
			case 2:
				System.out.println("\nNo of seats available now: " + service.numSeatsAvailable());
				break;
			case 3:
				System.out.println("How many seats for hold?");
				String xs = sc.next();
				boolean isvalidSeat = Validator.isValidNo(xs);
				if(!isvalidSeat){
					while(!isvalidSeat){
						System.out.println("Invalid seat no.");
						System.out.println("Enter valid no:");
						xs = sc.next();
						isvalidSeat = Validator.isValidNo(xs);
					}
				}
				int seats = Integer.parseInt(xs);
				System.out.println("Client email?");
				String email = sc.next();
				boolean isvalid = Validator.isValidEmail(email);
				if(!isvalid){
					while(!isvalid){
						System.out.println("Invalid email pattern.");
						System.out.println("Enter valid email:");
						email = sc.next();
						isvalid = Validator.isValidEmail(email);
					}
				}
				SeatHold hold = service.findAndHoldSeats(seats, email);
				if(hold!=null){
					System.out.println("\n" + seats + " held!\n" + hold);
				}else{
					System.out.println("\nYour request has been failed! Please try again!");
				}
				break;
			case 4:
				System.out.println("SeatHold Id?");
				String x = sc.next();
				boolean isvalidDigit = Validator.isValidNo(x);
				if(!isvalidDigit){
					while(!isvalidDigit){
						System.out.println("Invalid no pattern.");
						System.out.println("Enter valid no:");
						x = sc.next();
						isvalidDigit = Validator.isValidNo(x);
					}
				}
				int id = Integer.parseInt(x);
				System.out.println("Associated with which customer email?");
				String cust = sc.next();
				boolean isvalidEmail = Validator.isValidEmail(cust);
				if(!isvalidEmail){
					while(!isvalidEmail){
						System.out.println("Invalid email pattern.");
						System.out.println("Enter valid email:");
						cust = sc.next();
						isvalidEmail = Validator.isValidEmail(cust);
					}
				}
				System.out.println("\n" + service.reserveSeats(id, cust));
				break;
			case 5:
				loop = false;
				System.out.println("\nThank you!");
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
		sc.close();
		
	}

}
