package com.yuan.demo.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class socket_server {

	public socket_server() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket s=new ServerSocket(8888).accept();
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			DataInputStream dis=new DataInputStream(s.getInputStream());
			String clientName=dis.readUTF();
			System.out.println("link success to"+clientName);
			System.out.println("hello i am server,i will say hello to Client");
			dos.writeUTF("hello client"+clientName);
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
