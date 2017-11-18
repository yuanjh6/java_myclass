package com.yuan.demo.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class scoket_client {

	public scoket_client() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket s=new Socket("10.13.64.138",8888);
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			DataInputStream dis=new DataInputStream(s.getInputStream());
			System.out.println("i am john i will say hello to server");
			dos.writeUTF("john");
			System.out.println(dis.readUTF());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
