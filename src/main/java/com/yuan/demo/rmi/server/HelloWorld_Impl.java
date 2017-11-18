package com.yuan.demo.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface HelloWorld_Impl extends Remote {
	public String sayHello() throws RemoteException;
	public String saySomebodyHello(String name)  throws RemoteException;
}
