package de.chris0385.pixel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

public class PixelFlut {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		new PixelFlut().run();

	}


	public PixelFlut() {
	}

	private void run() {
		try {
			String name = "v3.png";
//			name = "r2.jpg";
			BufferedImage hugeImage = ImageIO.read(getClass().getClassLoader().getResource(name));
			int[][] imag = convertTo2DUsingGetRGB(hugeImage);
			int x0=0,y0=0;
//			x0= 350;y0 = 0;
			//x0= 1600;y0 = 700;
			//x0= 1900-500;y0 = 750;
//			x0= 300;y0 = 750;
			
			OutputData[] data = createOutputData(imag, x0, y0);
			
			PixelDataSource dataSource= new PixelDataSource(data);
			
			AtomicInteger numThread=new AtomicInteger();
			for(int i=0;i<50;i++) {
				new Thread() {
					@Override
					public void run() {
						for (int i = 0; i < 10; i++) {
							new Thread(new PixelSocket(dataSource)).start();
							int nt = numThread.incrementAndGet();
							if(nt%10==0) {
								System.out.println("Started "+nt);
							}
						}
					}
				}.start();
			}
			System.out.println("Booted");
			Thread.sleep(2000000);
//			while (true) {
//				for (OutputData o : data) {
//					o.write(out);
//				}
//			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private OutputData[] createOutputData(int[][] imag, int x0, int y0) {
		ArrayList<OutputData> arr = new ArrayList<>();
		int d=1; //3->100000
		for (int x = 0; x < imag[0].length; x+=d) {
			StringBuilder bob = new StringBuilder();
			for (int y = 0; y < imag.length; y+=d) {
//				if(x<300||x>1700) continue;
//				if(y<600) continue;
				int r, g, b;
				b = (imag[y][x] >> 0) & 0xFF;
				g = (imag[y][x] >> 8) & 0xFF;
				r = (imag[y][x] >> 16) & 0xFF;
				int t = (imag[y][x] >> 24) & 0xFF;
				if (t < 100) {
					continue;
				}
				bob.append(String.format("PX %d %d %02x%02x%02x\n", x/d + x0, y/d + y0, r, g, b));
			}
			if(bob.length()==0) continue;
			arr.add(new OutputData(bob.toString().getBytes()));
		}
		System.out.println("Pixels:" + arr.size());
		return arr.toArray(new OutputData[0]);
	}

	private static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] result = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = image.getRGB(col, row);
			}
		}

		return result;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
