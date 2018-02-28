package de.chris0385.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * Different from PooledObjectFactory in that it doesn't need the object to be subtype of PooledObject.
 * 
 * Useful for Object from the API.
 * 
 * Threadsafe: each thread has it's own little pool (a partition), and communicates with a global pool if he has not enough or too much.
 */
public class PartitionedObjectPool<T> {
	
	private class ThreadLocalPool implements ObjectPool<T> {
		private final Supplier<T> creator;
		private final Object[] pool;
		private int cnt;
		
		private ThreadLocalPool(Supplier<T> creator, int maxSize) {
			this.creator = creator;
			pool = new Object[maxSize];
		}

		public T take() {
			if (cnt > 0) {
				return (T) pool[--cnt];
			}
			if (gcnt > 0) {
				cnt += takeSet(pool, cnt);
			}
			return create();
		}

		private T create() {
			return creator.get();
		}
		
		public void returnDisposed(T obj) {
			if (obj == null) {
				throw new IllegalArgumentException();
			}
			if (cnt == pool.length) {
				cnt -= returnSet(pool, pool.length / 2, pool.length);
			}
			if (cnt == pool.length) {
				// Garbage collect
				return;
			}
			pool[cnt++] = obj;
		}
		
		// protected void finalize() throws Throwable {
		// finalize slows down allocation slightly
		// finalize leads to the garbage collector needing two passes to clear
		// the object.
		// -> It's probably not worth it. We just did a garbage collection, live
		// with it.

	}

	private final Lock lock = new ReentrantLock();
	private Supplier<T> creator;
	private Object[] gpool;
	private volatile int gcnt;

	private ThreadLocal<ThreadLocalPool> perThreadPartition = ThreadLocal.withInitial(()->{
		return new ThreadLocalPool(creator, Math.min(10, Math.max(gpool.length / 8, 2)));
	});

	private PartitionedObjectPool(Supplier<T> creator, int maxSize) {
		this.creator = creator;
		gpool = new Object[maxSize];
	}
	
	private int takeSet(Object[] pool, int start) {
		lock.lock();
		try {
			int cnt = pool.length / 2;
			int i = 0;
			for (; i < cnt && gcnt > 0; i++) {
				pool[start + i] = gpool[--gcnt];
			}
			return i;
		} finally {
			lock.unlock();
		}
	}

	private int returnSet(Object[] pool, int start, int end) {
		lock.lock();
		try {
			int i;
			for (i = end - 1; i >= start; i--) {
				if (gcnt == gpool.length) {
					// Garbage collect
					break;
				}
				gpool[gcnt++] = pool[i];
			}
			return end - 1 - i;
		} finally {
			lock.unlock();
		}
	}

	private static final ConcurrentHashMap<Class<?>, PartitionedObjectPool<?>> POOLS = new ConcurrentHashMap<>();

	/**
	 * creator has to be threadsafe
	 */
	public static <T> Supplier<ObjectPool<T>> createOrGet(Class<T> cls, Supplier<T> creator, int maxSize) {
		PartitionedObjectPool<T> partitionedPool = (PartitionedObjectPool<T>) POOLS.computeIfAbsent(cls, (c) -> {
			return new PartitionedObjectPool<T>(creator, maxSize);
		});
		return partitionedPool::get;
	}
	
	/**
	 * @return null if no pool was initialized before using createOrGet
	 */
	public static <T> Supplier<ObjectPool<T>> get(Class<T> cls) {
		PartitionedObjectPool<T> partitionedPool = (PartitionedObjectPool<T>) POOLS.get(cls);
		if (partitionedPool == null) {
			return null;
		}
		return partitionedPool::get;
	}
	
	public ObjectPool<T> get() {
		return perThreadPartition.get();
	}
	
}
