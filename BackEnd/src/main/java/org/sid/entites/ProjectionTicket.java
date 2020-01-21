package org.sid.entites;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketProj", types = Ticket.class)
public interface ProjectionTicket {
	public Long getId();
	public String getNomClient();
	public double getPrix();
	public Integer getCodePayement();
	public boolean getReserve();
	public Place getPlace();
}
