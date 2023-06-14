package ui;

import java.util.Scanner;

public class ThreadTest {
	public static void main(String[] args) {
		new Thread(() -> {
			while (true) {
				System.out.println("Thread");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		try (Scanner sc = new Scanner(System.in)) {
			String s;
			while (true) {
				System.out.println("SC: ");
				s = sc.nextLine();
				System.out.println(s);
			}
		}
	}
}
 