package unitn.aose.warehousesim.data;

import static org.junit.Assert.*;

import org.junit.Test;

import unitn.aose.warehousesim.api.TicketManager;
import unitn.aose.warehousesim.api.data.Ticket;

public class TicketTest {

	@Test
	public void ticketManagerTest() {
		TicketManager tm = TicketManager.getInstance();
		String code1 = tm.getNewTrackingCode();
		String code2 = tm.getNewTrackingCode();
		Ticket t1 = tm.getTicket(code1);
		Ticket t2 = tm.getTicket(code2);
		assertNotNull(t1);
		assertNotNull(t2);
		assertNotEquals(t1, t2);
		int s1 = tm.getTrackingNumberState(code1);
		int s2 = tm.getTrackingNumberState(code2);
		assertEquals(s1, Ticket.TICKET_NONE);
		assertEquals(s2, Ticket.TICKET_NONE);
		tm.setTrackingState(code1, Ticket.TICKET_RETRIEVE);
		s1 = tm.getTrackingNumberState(code1);
		s2 = tm.getTrackingNumberState(code2);
		assertEquals(s1, Ticket.TICKET_RETRIEVE);
		assertEquals(s2, Ticket.TICKET_NONE);
		tm.setTrackingState(code2, Ticket.TICKET_STORE);
		s1 = tm.getTrackingNumberState(code1);
		s2 = tm.getTrackingNumberState(code2);
		assertEquals(s1, Ticket.TICKET_RETRIEVE);
		assertEquals(s2, Ticket.TICKET_STORE);
	}

}
