import com.stefan.kripke.Kripke;
import org.junit.Test;

public class KripkeTest {

	@Test
	public void main() {
		Kripke kripke = new Kripke("Car");

		kripke.addState("c0");
		kripke.addState("c1");
		kripke.addState("c2");
		kripke.addLabel("c2", "carOnCrossing");

		kripke.addTransition("c0", "car_enter", "c2");
		kripke.addTransition("c2", "car_exit", "c0");
		kripke.addTransition("c0", "close", "c1");
		kripke.addTransition("c1", "open", "c0");

//		kripke.printCurrentState();
//		kripke.input("car_enter");
//		kripke.input("car_exit");
//		kripke.input("car_exit");

		kripke.performDepthFirstSearch();
	}

	@Test
	public void main2() {
		Kripke kripke = new Kripke("Composed");

		kripke.addState("c0");
		kripke.addState("c1");
		kripke.addTransition("c0", "car_enter", "c1");
		kripke.addTransition("c1", "car_exit", "c0");
		kripke.addLabel("c1", "carOnCrossing");

		kripke.addState("c2");
		kripke.addTransition("c1", "approaching", "c2");
		kripke.addLabel("c2", "carOnCrossing");

		kripke.addState("c3");
		kripke.addTransition("c2", "car_exit", "c3");
		kripke.addTransition("c3", "car_enter", "c2");

		kripke.addState("c4");
		kripke.addTransition("c3", "close", "c4");

		kripke.addState("c5");
		kripke.addTransition("c4", "enter", "c5");
		kripke.addLabel("c5", "trainOnCrossing");

		kripke.addState("c6");
		kripke.addTransition("c5", "exit", "c6");
		kripke.addTransition("c6", "open", "c1");

		kripke.performDepthFirstSearch();
	}
}
