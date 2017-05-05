package com.hello.pattern.command;

public class TvCommandInvoker {
	private TurnOffCommand mTurnOffCommand;
	private TurnOnCommand mTurnOnCommand;

	public TvCommandInvoker(TurnOnCommand mTurnOnCommand, TurnOffCommand mTurnOffCommand) {
		this.mTurnOffCommand = mTurnOffCommand;
		this.mTurnOnCommand = mTurnOnCommand;
	}

	public void TurnOn() {
		mTurnOnCommand.execute();
	}

	public void TurnOff() {
		mTurnOffCommand.execute();
	}
}
