package com.hello.pattern.command;

public class TurnOffCommand implements Command {
	private Tv mTv;
	public TurnOffCommand(Tv tv) {
		super();
		this.mTv = tv;
	}
	@Override
	public void execute() {
		mTv.turnOff();
	}
}
