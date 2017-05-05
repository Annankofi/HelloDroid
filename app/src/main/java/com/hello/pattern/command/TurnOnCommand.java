package com.hello.pattern.command;

public class TurnOnCommand implements Command {
	private Tv mTv;
	public TurnOnCommand(Tv tv) {
		this.mTv = tv;
	}
	@Override
	public void execute() {
		mTv.turnOn();
	}
}
