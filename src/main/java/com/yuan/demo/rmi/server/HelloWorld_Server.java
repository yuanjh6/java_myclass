package com.yuan.demo.rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class HelloWorld_Server {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			LocateRegistry.createRegistry(8888);
			Naming.bind("rmi://localhost:8888/Rhello", new HelloWorld());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
