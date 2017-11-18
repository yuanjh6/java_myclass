package com.yuan.demo;

public class MultiThread implements Runnable{
	private String name;
	private int time;
	public MultiThread(String name,int time) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.time=time;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread a=new Thread(new MultiThread("a",1000));
		Thread b=new Thread(new MultiThread("b",5000));
		Thread c=new Thread(new MultiThread("c",10000));
		try {
			c.start();
			c.join();
			b.start();
			b.join();
			a.start();
			a.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run(){
		System.out.println("this is " +this.name+" and now start sleep");
		try {
			Thread.sleep(this.time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("this is "+this.name+" sleep ok now");
	}

}
