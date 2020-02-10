package ie.gmit.dip;

import java.io.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
	
	private static int port = 81;
	
	public static void main(String args[]) {
		boolean keepRunning = true;
		int userInput, randomNumber, serverWin = 0, clientWin = 0, lives = 9;
		String result;
		try {
			ServerSocket theServer = new ServerSocket(port); // create server on port 81
			Socket theSocket = theServer.accept(); // listen to connection on specified port and accept it once it occurs
			randomNumber = generateNumber(); // assign result of method to variable

			while (keepRunning) { // infinite loop
				InputStream in = theSocket.getInputStream();
				BufferedReader bin = new BufferedReader(new InputStreamReader(in));
				userInput = Integer.parseInt(bin.readLine());

				if (lives == 0 && userInput != randomNumber) {
					result = "You're out of guesses - you lose!";
					PrintWriter pout = new PrintWriter(theSocket.getOutputStream(), true);// generate output stream
					pout.println(result); // send message
					lives = 9; // reset number of attempts
					randomNumber = generateNumber(); // generate random number for a new game
					serverWin++; // record server's victory
				}

				if (userInput == randomNumber) {
					result = "Correct-you win!";
					PrintWriter pout = new PrintWriter(theSocket.getOutputStream(), true); // generate output stream
					pout.println(result); // send message
					lives = 9; // reset number of attempts
					randomNumber = generateNumber(); // generate random number for a new game
					clientWin++; // record clients victory
				}

				else if (userInput > randomNumber) {
					result = "Too high - guess again";
					lives--;
					PrintWriter pout = new PrintWriter(theSocket.getOutputStream(), true); // generate output stream
					pout.println(result); // send message
				}

				else if (userInput < randomNumber) {
					result = "Too low - guess again";
					lives--;
					PrintWriter pout = new PrintWriter(theSocket.getOutputStream(), true); // generate output stream
					pout.println(result); // send message
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	// Method to generate random number between 1 and 1000
	private static int generateNumber() {
		Random rand = new Random();
		int randomNumber = rand.nextInt(1000) + 1;
		return randomNumber;
	}
}