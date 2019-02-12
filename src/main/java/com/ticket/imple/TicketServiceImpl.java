package com.ticket.imple;

import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ticket.defination.Client;
import com.ticket.defination.Validator;
import com.ticket.defination.EnumState;
import com.ticket.defination.Seat;
import com.ticket.defination.SeatHold;
import com.ticket.defination.Venue;


public class TicketServiceImpl implements TicketService {
	private int available;
	private Venue v;
	private Map<Integer, SeatHold> seatHoldMapper;
	private long seconds = 100L;

	public TicketServiceImpl(Venue v) {
		super();
		this.v = v;
		this.available = v.getCapacity();
		seatHoldMapper = new TreeMap<Integer, SeatHold>();
	}
	
	public TicketServiceImpl(Venue v, long secs) {
		this(v);
		this.seconds = secs;
	}

	public int numSeatsAvailable() {
		expiryCheck();
		System.out.println(v.prettyPrint());
		return available;
	}

	private void expiryCheck() {
		for (Iterator<Map.Entry<Integer, SeatHold>> it = seatHoldMapper.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Integer, SeatHold> entry = it.next();
			SeatHold tempSH = entry.getValue();
			long now = Instant.now().getEpochSecond();
			if ((now - tempSH.getCreatedAt().getEpochSecond()) > this.seconds) {
//				System.out.println("\t()now = " + now + " sec.");
//				System.out.println("\t()Created at = " + tempSH.getCreatedAt().getEpochSecond() + " sec.");
				updateStatus(tempSH.getSeatsHeld(), EnumState.AVAILABLE);
				this.available += tempSH.getSeatsHeld().size();
				it.remove();
			}
		}
	}

	private void expiryCheck(int seatHoldId) {
		SeatHold tempSH = seatHoldMapper.get(seatHoldId);
		if(tempSH!=null){
			long now = Instant.now().getEpochSecond();
			if((now - tempSH.getCreatedAt().getEpochSecond())> this.seconds){
//				System.out.println("\tnow = " + now + " sec.");
//				System.out.println("\tCreated at = " + tempSH.getCreatedAt().getEpochSecond() + " sec.");
				updateStatus(tempSH.getSeatsHeld(), EnumState.AVAILABLE);
				this.available += tempSH.getSeatsHeld().size();
				seatHoldMapper.remove(seatHoldId);
			}
		}
	}

	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		expiryCheck();
		List<Seat> holdingSeats = findGoodSeats(numSeats);
		updateStatus(holdingSeats, EnumState.HOLD);
		this.available -= holdingSeats.size();
		SeatHold hold = generateSeatHold(holdingSeats, customerEmail);
		if(hold!=null)seatHoldMapper.put(hold.getId(), hold);
		return hold;
	}

	private void updateStatus(List<Seat> seats, EnumState enumState){
		for(Seat st: seats){
			st.setStatus(enumState);
		}
	}
	
	private SeatHold generateSeatHold(List<Seat> holdingSeats, String customerEmail){
		if(holdingSeats.size()<1){
			return null;
		}
		SeatHold hold = new SeatHold();
		hold.setCustomer(new Client(customerEmail));
		hold.setSeatsHeld(holdingSeats);
		hold.setCreatedAt(Instant.now());
		
		return hold;
	}
	
	private List<Seat> findGoodSeats(int numSeats){
		if(this.available < numSeats){
			System.out.println("There are only " + this.available + " seats available now!");
			return new LinkedList<Seat>(); 
		}
		Seat[][] seats = v.getSeats();
		List<Seat> storeSeats = new LinkedList<Seat>();
		boolean breakFlag = false;
		for(int i=0; i<v.getRows(); i++){
			if(breakFlag){
				break;
			}
			for(int j=0; j<v.getSeatsPerRow(); j++){
				Seat st = seats[i][j];
				if(EnumState.AVAILABLE == st.getStatus()){
					storeSeats.add(st);
					if(--numSeats == 0){
						breakFlag = true;
						break;
					}
				}
			}
		}
		return storeSeats;
	}
	public String reserveSeats(int seatHoldId, String customerEmail) {
		expiryCheck(seatHoldId);
		SeatHold seatHold = finder(seatHoldId);
		if(seatHold == null){
			System.out.println("Either seatHoldId is invalid OR is expired! ");
			return null;
		}
		boolean isValidCustomer = Validator.validateCustomer(customerEmail, seatHold.getCustomer().getEmail());
		if(!isValidCustomer){
			return "cannot verify customer. Please request reservation with correct customer email.";
		}
		updateStatus(seatHold.getSeatsHeld(), EnumState.RESERVED);
//		this.available -= seatHold.getSeatsHeld().size();
		String result =  Validator.reservationCode(seatHold);
		seatHoldMapper.remove(seatHoldId);
		return result;
	}
	
	private SeatHold finder(int seatHoldId){
		return seatHoldMapper.get(seatHoldId);
	}
}
