package ie.gmit.dip;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.*;

public class Client {

	private static boolean keepRunning = true;
	private static int port =81;
	private static String IP="127.0.0.1";

	public static void main(String args[]) {
		int number;
		String result;

		Scanner sc = new Scanner(System.in);
		Socket theSocket;
		try {
			theSocket = new Socket(IP, port);// generate socket on localhost port 81
			DataInputStream dis;
			String result2 = "";
			displayLogo();

			while (keepRunning) {

				System.out.println("Enter a number between 1-1000");
				number = sc.nextInt(); // Read input from user

				PrintWriter pout = new PrintWriter(theSocket.getOutputStream(), true); // generate output stream for a
																						// socket and enable autoflush
																						// option
				pout.println(number); // Send data to server

				dis = new DataInputStream(theSocket.getInputStream()); // generate stream for receiving data
				result = dis.readLine();
				result2 = result;
				System.out.println(result); // print received message

				if (result.equals("Correct-you win!")) { // if user won the game
					System.out.println("Congratulation!");
					menu();
					initMenu();
				} else if (result2.equals("You're out of guesses - you lose!")) { // if user lost the game
					System.out.println("Try again");
					menu();
					initMenu();
				}
			}
			theSocket.close();
		} 
		catch (UnknownHostException e) {
			System.err.println(e);
		} 
		catch (IOException e) {
			System.out.println(e);
		}
	}

	// Display options
	public static void menu() {
		System.out.println("\nOptions:");
		System.out.println("(1) Try again");
		System.out.println("(2) Exit");
	}

	// Read input and process following option
	public static void initMenu() {
		Scanner sc1 = new Scanner(System.in);
		int option = sc1.nextInt();

		switch (option) {
		case 1:
			System.out.println("\n");
			displayLogo();
			break;
		case 2:
			keepRunning = false;
			System.out.println("Goodbye");
			break;
		default:
			System.out.println(" ^      ^      ^ ");
			System.out.println(" |      |      |");
			System.out.println("Invalid option. Check option above.");
		}
	}

	public static void displayLogo() {
		System.out.println("------------------------------");
		System.out.println("|   Number  Guessing  Game   |");
		System.out.println("------------------------------");
	}
}