package com.yuan.demo.rmi.client;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HelloWorld_Client {

	public HelloWorld_Client() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			HelloWorld_Impl hw=(HelloWorld_Impl)Naming.lookup("rmi://localhost:8888/Rhello");
			System.out.println(hw.sayHello());
			System.out.println(hw.saySomebodyHello("john"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
