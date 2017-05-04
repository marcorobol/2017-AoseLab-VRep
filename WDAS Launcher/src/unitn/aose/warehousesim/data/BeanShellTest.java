package unitn.aose.warehousesim.data;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import bsh.EvalError;
import bsh.Interpreter;

public class BeanShellTest {

	@Test
	public void scriptTest() {
		Interpreter i = new Interpreter();
		long duration = 1000;
		String statements = "Thread.sleep("+duration+");";
		long st = System.currentTimeMillis();
		long et = st+duration;
		try {
			i.eval(statements);
		} catch (EvalError e) {
			e.printStackTrace();
		}
		assertTrue(et<System.currentTimeMillis());
	}

}
