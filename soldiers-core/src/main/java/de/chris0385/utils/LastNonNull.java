package de.chris0385.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntFunction;

public class LastNonNull<T> {

	private IntFunction<T>[] takers;
	private ArrayList<T> buf;
	private List<T> unmodifiableRsult;

	public LastNonNull(IntFunction<T>... takers) {
		this.takers = takers;
		this.buf = new ArrayList<T>(takers.length);
		unmodifiableRsult = Collections.unmodifiableList(buf);
	}

	public LastNonNull(List<IntFunction<T>> takers) {
		this(takers.toArray(new IntFunction[takers.size()]));
	}

	public List<T> get(int timeout) {
		long endTime = System.currentTimeMillis() + timeout;

		for (int i = 0; i < takers.length; i++) {
			buf.set(i, null);
		}
		int i = 0;
		for (i = 0; i < takers.length; i++) {
			int waitTime = (int) (endTime - System.currentTimeMillis());
			if (waitTime <= 0) {
				break;
			}
			T newV = takers[i].apply(waitTime);
			if (newV != null) {
				buf.set(i, newV);
			}
		}
		// Updates if newer data arrived
		for (i = 0; i < takers.length; i++) {
			T newV = takers[i].apply(0);
			if (newV != null) {
				buf.set(i, newV);
			}
		}
		return unmodifiableRsult;
	}

}
