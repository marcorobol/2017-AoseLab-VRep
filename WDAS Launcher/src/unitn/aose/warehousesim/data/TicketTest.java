package unitn.aose.warehousesim.data;

import static org.junit.Assert.*;

import org.junit.Test;

import unitn.aose.warehousesim.api.TicketManager;
import unitn.aose.warehousesim.api.data.Ticket;

public class TicketTest {

	@Test
	public void ticketManagerTest() {
		TicketManager tm = TicketManager.getInstance();
		String code1 = tm.getNewTrackingCode("box1");
		String code2 = tm.getNewTrackingCode("box2");
		Ticket t1 = tm.getTicketByCode(code1);
		Ticket t2 = tm.getTicketByCode(code2);
		assertNotNull(t1);
		assertNotNull(t2);
		assertNotEquals(t1, t2);
		Ticket t1b = tm.getTicketByBox("box1");
		Ticket t2b = tm.getTicketByBox("box2");
		assertNotNull(t1b);
		assertNotNull(t2b);
		assertNotEquals(t1b, t2b);
		assertEquals(t1b, t1);
		assertEquals(t2b, t2);
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
