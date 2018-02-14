package de.chris0385.pixel;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class PixelSocket implements Runnable {

	private Socket socket;
	private OutputStream out;
	private PixelDataSource dataSource;

	public PixelSocket(PixelDataSource dataSource) {
		this.dataSource = dataSource;
		try {
			socket = new Socket("pixelflut.gpn.entropia.de", 1234);
			out = socket.getOutputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public OutputStream getOut() {
		return out;
	}

	@Override
	public void run() {
		ThreadLocalRandom rnd = ThreadLocalRandom.current();
		while (true) {
			OutputData n = dataSource.next();
//			if(rnd.nextFloat()<0.8) {
//				continue;
//			}
			n.write(out);
			
//			for (int i = 0; i < dataSource.size(); i++) {
//				 dataSource.get(i).write(out);
//				try {
//					out.write(dataSource.concat());
//				} catch (IOException e) {
//					throw new RuntimeException(e);
//				}
//			}
		}
	}
}
