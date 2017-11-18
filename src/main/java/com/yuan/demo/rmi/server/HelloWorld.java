package com.yuan.demo.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class HelloWorld extends UnicastRemoteObject implements HelloWorld_Impl{

	protected HelloWorld() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String sayHello() throws RemoteException {
		return "Hello Anonymous";
		
	}

	@Override
	public String  saySomebodyHello(String name) throws RemoteException{
		// TODO Auto-generated method stub
		return "Hello "+name;
	}
	

}
