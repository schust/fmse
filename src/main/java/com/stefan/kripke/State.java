package com.stefan.kripke;

import java.util.*;

class State {
	private final String name;
	private Set<String> labels;
	private Map<String, State> transitions;

	State(String name) {
		this.name = name;
		this.labels = new HashSet<>();
		this.transitions = new HashMap<>();
	}

	void addLabel(String label) {
		labels.add(label);
	}

	void addNextState(String input, State next) {
		transitions.putIfAbsent(input, next);
	}

	State nextState(String input) {
		return transitions.get(input);
	}

	@Override
	public String toString() {
		return String.format("State[%s: %s]", name, Arrays.toString(labels.toArray()));
	}

	Collection<? extends State> getNextStates() {
		return transitions.values();
	}

	void print() {
		System.out.println(this);
	}
}
