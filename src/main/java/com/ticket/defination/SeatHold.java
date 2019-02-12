package com.ticket.defination;

import java.time.Instant;
import java.util.List;

public class SeatHold {
	private static int counter;
	private int id;
	private Client client;
	private Instant createdAt;
	private List<Seat> seatsHeld;
	static {
		counter = 0;
	}
	public SeatHold() {
		this.id = ++counter;
	}
	
	public static int getCounter() {
		return counter;
	}
	
	public static void setCounter(int counter) {
		SeatHold.counter = counter;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Client getCustomer() {
		return client;
	}
	
	public void setCustomer(Client client) {
		this.client = client;
	}
	
	public Instant getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	
	public List<Seat> getSeatsHeld() {
		return seatsHeld;
	}
	
	public void setSeatsHeld(List<Seat> seatsHeld) {
		this.seatsHeld = seatsHeld;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeatHold [").append(id).append(", ");
		if (client != null)
			builder.append(client).append(", ");
		if (createdAt != null)
			builder.append(createdAt).append(", ");
		if (seatsHeld != null){
			builder.append(seatsHeld.size() + " seats held: [");
			for(Seat st: seatsHeld){
				builder.append(st.getSeatNo()); builder.append(" ");
			}
			builder.append("]");
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}
