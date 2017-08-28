package com.stefan.kripke;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Kripke {
	private final String name;
	private final Map<String, State> states;
	private State currentState;

	/**
	 * Creates a new Kripke Structure with the given name.
	 * Throws {@link IllegalArgumentException}, if name is null/empty.
	 * @param name The name of the Kripke Structure
	 */
	public Kripke(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Kripke names should not be blank.");
		}
		this.name = name;
		this.states = new HashMap<>();
	}

	/**
	 * Add a new State with the given name.
	 * Throws {@link IllegalArgumentException}, if name is null/empty.
	 * @param name The name of the new State
	 */
	public void addState(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("State names should not be blank.");
		}
		State state = new State(name);
		if (states.size() == 0) {
			this.currentState = state;
		}
		states.put(name, state);
	}

	/**
	 * Adds a new label to the given state if it does not exist yet.
	 * Throw {@link IllegalArgumentException}, if any of the String parameters is empty or the state does not exist.
	 * @param stateName
	 * @param label
	 */
	public void addLabel(String stateName, String label) {
		if (StringUtils.isBlank(stateName)) {
			throw new IllegalArgumentException("State names should not be blank.");
		}
		if (StringUtils.isBlank(label)) {
			throw new IllegalArgumentException("Labels should not be blank.");
		}
		State state = states.get(stateName);
		if (state != null) {
			state.addLabel(label);
		}
	}

	/**
	 * Steps to and prints the next state, if the current state has a corresponding transition.
	 * @param input The input leading to the next State
	 */
	public void input(String input) {
		State nextState = this.currentState.nextState(input);

		if (nextState != null) {
			this.currentState = nextState;
		}

		printCurrentState();
	}

	/**
	 * Adds a transition in the startState firing on the given input leading to nextState.
	 * Throws {@link IllegalArgumentException}, if any of the String parameters in null/empty or one of the two States does not exist.
	 * @param startState The name of the starting state of the transition
	 * @param input The input upon which the transition will be activated
	 * @param nextState The name of the ending state of the transition
	 */
	public void addTransition(String startState, String input, String nextState) {
		if (StringUtils.isBlank(startState)) {
			throw new IllegalArgumentException("State names should not be blank.");
		}
		if (StringUtils.isBlank(input)) {
			throw new IllegalArgumentException("Inputs should not be blank.");
		}
		if (StringUtils.isBlank(nextState)) {
			throw new IllegalArgumentException("State names should not be blank.");
		}

		State start = states.get(startState);
		if (start == null) {
			throw new IllegalArgumentException(String.format("State with name %s does not exist.", startState));
		}

		State next = states.get(nextState);
		if (next == null) {
			throw new IllegalArgumentException(String.format("State with name %s does not exist.", nextState));
		}

		start.addNextState(input, next);
	}

	/**
	 * Prints the current state of the Kripke Structure
	 */
	public void printCurrentState() {
		currentState.print();
	}

	/**
	 * Runs through all states and prints them, using a Depth-First-Search.
	 */
	public void performDepthFirstSearch() {
		Queue<State> closed = new LinkedList<>();
		Queue<State> opened = new LinkedList<>();

		opened.add(currentState);

		dfsHelper(opened, closed);
	}

	private void dfsHelper(Queue<State> opened, Queue<State> closed) {
		State current = opened.poll();
		current.print();

		closed.add(current);
		opened.addAll(current.getNextStates());
		opened.removeAll(closed);

		if (!opened.isEmpty()) {
			dfsHelper(opened, closed);
		}
	}
}
