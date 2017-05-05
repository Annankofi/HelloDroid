package com.hello.pattern.facade;

public class Facade {

	private DrawerOne drawerOne;
	private DrawerTwo drawerTwo;

	public Facade(DrawerOne drawerOne, DrawerTwo drawerTwo) {
		super();
		this.drawerOne = drawerOne;
		this.drawerTwo = drawerTwo;
	}

	public void getFile() {
		drawerOne.open();
		drawerTwo.open();
	}
}
