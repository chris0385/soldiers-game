package de.chris0385.pixel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class PixelDataSource {

	private OutputData[] data;
	private int idx;
	private WriteLock lock;
	private byte[] cat;

	public PixelDataSource(OutputData[] data) {
		lock = new ReentrantReadWriteLock().writeLock();
		this.data = data;
		this.idx = 0;
	}

	public synchronized OutputData next() {
		try {
//			lock.lock();
			return data[(idx++) % data.length];
		} finally {
//			lock.unlock();
		}
	}
	
	public int size() {
		return data.length;
	}
	
	public OutputData get(int i) {
		return data[i];
	}
	
	public synchronized byte[] concat() {
		if(cat!=null) {
			return cat;
		}
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		Arrays.stream(data).forEach(b-> {
			try {
				o.write(b.data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return cat=o.toByteArray();
	}

}
