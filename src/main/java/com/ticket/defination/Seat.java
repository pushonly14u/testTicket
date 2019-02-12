package com.ticket.defination;

public class Seat {
	Position seatNo;
	Client reservedBy;
	EnumState enumState;
	
	public Seat(Position seatNo) {
		super();
		this.seatNo = seatNo;
	}
	
	public Seat(Position seatNo, EnumState enumState) {
		this(seatNo);
		this.enumState = enumState;
	}

	public Position getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(Position seatNo) {
		this.seatNo = seatNo;
	}
	public Client getReservedBy() {
		return reservedBy;
	}
	public void setReservedBy(Client reservedBy) {
		this.reservedBy = reservedBy;
	}
	public EnumState getStatus() {
		return enumState;
	}
	public void setStatus(EnumState enumState) {
		this.enumState = enumState;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seat<");
		if (seatNo != null)
			builder.append(seatNo).append(", ");
		if (reservedBy != null)
			builder.append("reservedBy=").append(reservedBy).append(", ");
		if (enumState != null)
			builder.append("enumState=").append(enumState);
		builder.append(">");
		return builder.toString();
	}
	
	
}
