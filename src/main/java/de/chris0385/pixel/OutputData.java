package de.chris0385.pixel;

import java.io.IOException;
import java.io.OutputStream;

public class OutputData {
	int x, y;
	byte[] data;

	public OutputData(byte[] data) {
		this.data = data;
	}

	public void write(OutputStream out) {
		try {
			out.write(this.data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
