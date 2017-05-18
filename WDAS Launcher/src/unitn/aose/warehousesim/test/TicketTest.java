package unitn.aose.warehousesim.test;

import static org.junit.Assert.*;

import org.junit.Test;

import unitn.aose.warehousesim.api.ITicket;
import unitn.aose.warehousesim.api.ITicketManager;
import unitn.aose.warehousesim.api.data.Ticket;
import unitn.aose.warehousesim.data.Box;
import unitn.aose.warehousesim.data.TicketManager;

public class TicketTest {

	@Test
	public void ticketManagerTest() {
		ITicketManager tm = new TicketManager();
		Box box1 = new Box("box1");
		Box box2 = new Box("box2");
		ITicket code1 = tm.getNewTicket(box1, true);
		ITicket code2 = tm.getNewTicket(box2, true);
		Ticket t1 = tm.getTicketByCode(code1.getCode());
		Ticket t2 = tm.getTicketByCode(code2.getCode());
		assertNotNull(t1);
		assertNotNull(t2);
		assertNotEquals(t1, t2);
		int s1 = t1.getState();
		int s2 = t2.getState();
		assertEquals(s1, Ticket.TICKET_GENERATED);
		assertEquals(s2, Ticket.TICKET_GENERATED);
	}

}
