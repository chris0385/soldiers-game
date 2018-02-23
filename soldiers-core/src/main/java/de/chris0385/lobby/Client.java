package de.chris0385.lobby;

/**
 * A client represents a connection to the client.
 * It is distinct to a player insofar that the client may also be a simple observer.
 * 
 * A single person may also have multiple open connections open with the server.
 */
public class Client {

	public void dispose() {
		// TODO Auto-generated method stub
		// disconnected
	}
	
	public void addClientUpdateListener(ClientUpdateListener listener) {
		// TODO
	}

}
