package de.chris0385.pixel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PixelEraser {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		new PixelEraser().run();

	}

	public PixelEraser() {
	}

	private void run() {
		try {
			Socket socket = new Socket("pixelflut.gpn.entropia.de", 1234);
			OutputStream out = socket.getOutputStream();
			int c = 0;
			while (true) {
				for (int y = 0; y < 1200; y++) {
					StringBuilder bob = new StringBuilder();
					for (int x = 0; x < 1900; x++) {
						int r, g, b;
						r = g = b = c;
						String str = String.format("PX %d %d %02x%02x%02x\n", x, y, r, g, b);
						bob.append(str);
					}
					byte[] da = bob.toString().getBytes();
					out.write(da);
					System.out.println("pos=" + y+" "+c);
				}
				System.err.println("c=" + c);
				c = c == 0 ? 255 : 0;
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
