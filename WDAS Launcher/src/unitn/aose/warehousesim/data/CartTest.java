package unitn.aose.warehousesim.data;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import unitn.aose.warehousesim.api.MovementState;
import unitn.aose.warehousesim.api.data.MovementWithRespectToMe;
import unitn.aose.warehousesim.api.data.PositionWithRespectToMe;
import unitn.aose.warehousesim.observable.ObservableCart;

public class CartTest {

	@Test
	public void cartAroundTest() {
		final Map<PositionWithRespectToMe, ObservableCart> cartAround;
		cartAround = new HashMap<PositionWithRespectToMe, ObservableCart>();
		for (PositionWithRespectToMe p : PositionWithRespectToMe.values()) {
			cartAround.put(p, new ObservableCart());
		}

		int hash = PositionWithRespectToMe.haed.hashCode();
		System.out.println(PositionWithRespectToMe.haed + "=" + Integer.toBinaryString(hash));

		ObservableCart oc = cartAround.get(PositionWithRespectToMe.haed);
		assertNotNull(oc);
		oc = cartAround.get(new PositionWithRespectToMe(1, 0));
		assertNotNull(oc);
	}

	@Test
	public void cartMovementWithRespectToMeTest() {
		Rail rail = new Rail("rail", 16);
		Cart myself = new Cart("I", rail, null, null);
		myself.getPosition().set(0);
		Cart theother = new Cart("O", rail, null, null);
		rail.addCart(myself);
		rail.addCart(theother);
		theother.getPosition().set(5);
		MovementWithRespectToMe m = myself.computeMovementWithRespectToMe(theother);
		assertEquals(MovementWithRespectToMe.steady, m);
		theother.getMovement().set(MovementState.runningForward);
		m = myself.computeMovementWithRespectToMe(theother);
		assertEquals(MovementWithRespectToMe.goingAway, m);
		theother.getMovement().set(MovementState.runningBackward);
		m = myself.computeMovementWithRespectToMe(theother);
		assertEquals(MovementWithRespectToMe.gettingCloser, m);
		theother.getMovement().set(MovementState.stop);
		m = myself.computeMovementWithRespectToMe(theother);
		assertEquals(MovementWithRespectToMe.steady, m);
		
		myself.getPosition().set(5);
		theother.getPosition().set(0);
		m = myself.computeMovementWithRespectToMe(theother);
		assertEquals(MovementWithRespectToMe.steady, m);
		theother.getMovement().set(MovementState.runningForward);
		m = myself.computeMovementWithRespectToMe(theother);
		assertEquals(MovementWithRespectToMe.gettingCloser, m);
		theother.getMovement().set(MovementState.runningBackward);
		m = myself.computeMovementWithRespectToMe(theother);
		assertEquals(MovementWithRespectToMe.goingAway, m);
		theother.getMovement().set(MovementState.stop);
		m = myself.computeMovementWithRespectToMe(theother);
		assertEquals(MovementWithRespectToMe.steady, m);
		
		Rail rail2 = new Rail("anotherrail", 16);
		rail.addCross(2, rail2, 11, false);
		rail2.addCross(11, rail, 2, true);
		Cart theother2 = new Cart("O2", rail2, null, null);
		rail2.addCart(theother2);
		theother2.getPosition().set(8);
		myself.getPosition().set(2);
		m = myself.computeMovementWithRespectToMe(theother2);
		assertEquals(MovementWithRespectToMe.steady, m);
		theother2.getMovement().set(MovementState.runningForward);
		m = myself.computeMovementWithRespectToMe(theother2);
		assertEquals(MovementWithRespectToMe.gettingCloser, m);
		theother2.getMovement().set(MovementState.runningBackward);
		m = myself.computeMovementWithRespectToMe(theother2);
		assertEquals(MovementWithRespectToMe.goingAway, m);
		theother2.getMovement().set(MovementState.stop);
		m = myself.computeMovementWithRespectToMe(theother2);
		assertEquals(MovementWithRespectToMe.steady, m);
		
		myself.getPosition().set(2);
		theother2.getPosition().set(13);
		m = myself.computeMovementWithRespectToMe(theother2);
		assertEquals(MovementWithRespectToMe.steady, m);
		theother2.getMovement().set(MovementState.runningForward);
		m = myself.computeMovementWithRespectToMe(theother2);
		assertEquals(MovementWithRespectToMe.goingAway, m);
		theother2.getMovement().set(MovementState.runningBackward);
		m = myself.computeMovementWithRespectToMe(theother2);
		assertEquals(MovementWithRespectToMe.gettingCloser, m);
		theother2.getMovement().set(MovementState.stop);
		m = myself.computeMovementWithRespectToMe(theother2);
		assertEquals(MovementWithRespectToMe.steady, m);
	}
}
