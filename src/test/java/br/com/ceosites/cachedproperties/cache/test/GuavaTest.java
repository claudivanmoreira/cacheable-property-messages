package br.com.ceosites.cachedproperties.cache.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class GuavaTest {

	@Test
	@Ignore
	public void testGuavaGetCache() {
		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
        .maximumSize(100) // maximum 100 records can be cached
        .expireAfterAccess(30, TimeUnit.SECONDS) // cache will expire after 30 minutes of access
        .build(new CacheLoader<String, String>(){ // build the cacheloader
           @Override
           public String load(String key) throws Exception {
              return getFromMap(key);
           } 
        });
		
		
		try {
			System.out.println(cache.get("100"));
			System.out.println(cache.get("100"));
			System.out.println(cache.get("110"));
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testGuavaGetAllCache() {
		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
        .maximumSize(100) // maximum 100 records can be cached
        .expireAfterAccess(30, TimeUnit.SECONDS) // cache will expire after 30 minutes of access
        .build(new CacheLoader<String, String>(){ // build the cacheloader
           @Override
           public String load(String key) throws Exception {
        	   System.out.println("load...");
              return getFromMap(key);
           } 
           
           @Override
        	public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
        	   System.out.println("loadAll...");
        	   Iterator<? extends String> it = keys.iterator();
        	   Map<String, String> mapCache = new HashMap<String, String>();
        	   while(it.hasNext()) {
        		   String key = it.next();
        		   mapCache.put(key, getFromMap(key));
        	   }
        	   return mapCache;
        	}
        });
		
		
		try {
			cache.getAll(new KeysIterable("100", "103", "110"));
			System.out.println(cache.get("100"));
			System.out.println(cache.get("100"));
			System.out.println(cache.get("103"));
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGuavaConfigurationCache() throws InterruptedException {
		
		//expireAfterAccess - idle time
		//expireAfterWrite - time to live
		//refreshAfterWrite - time to refresh cache automatically
		
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("MyCacheRefresher-pool-%d").setDaemon(true).build();
	    ExecutorService parentExecutor = Executors.newSingleThreadExecutor(threadFactory);
	    final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(parentExecutor);
	    
		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
		.recordStats()
		.refreshAfterWrite(500, TimeUnit.MILLISECONDS)
		.removalListener(new CacheRemovalListener())
        .maximumSize(100)
        .expireAfterAccess(6, TimeUnit.SECONDS)
        .build(new CacheLoader<String, String>(){
        	@Override
        	public String load(String key) throws Exception {
        		System.out.println("load...");
        		return getFromMap(key);
        	} 

        	@Override
        	public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
        		System.out.println("loadAll...");
        		Iterator<? extends String> it = keys.iterator();
        		Map<String, String> mapCache = new HashMap<String, String>();
        		while(it.hasNext()) {
        			String key = it.next();
        			mapCache.put(key, getFromMap(key));
        		}
        		return mapCache;
        	}

        	@Override
        	public ListenableFuture<String> reload(final String key, String oldValue) throws Exception {
        		System.out.println("Asyncronous reload key: " + key);
        		ListenableFuture<String> listenableFuture = executorService.submit(new Callable<String>() {
        			@Override
        			public String call() throws Exception {
        				System.out.println("Async reload event");
        				return load(key);
        			}
        		});
        		return listenableFuture;
        	}
        });
		
		
		try {
			cache.getAll(new KeysIterable("100", "103", "110"));
			System.out.println(cache.getUnchecked("100"));
			Thread.sleep(5000);
			System.out.println(cache.getUnchecked("100"));
			System.out.println(cache.getUnchecked("103"));
			System.out.println(cache.getUnchecked("100"));
			System.out.println(cache.getUnchecked("103"));
			System.out.println(cache.stats().hitCount());
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public String getFromMap(String key) {
		System.out.println("loading map.");
		Map<String, String> database = new HashMap<String, String>();
		database.put("100", "Banana");
		database.put("103", "Uva");
		database.put("110", "laranja");
		return database.get(key);
	}
	
	class CacheRemovalListener implements RemovalListener<String, String> {

		@Override
		public void onRemoval(RemovalNotification<String, String> notification) {
			System.out.println("Removal notification received to key: " + notification.getKey());
		}
		
	}
	class KeysIterable implements Iterable<String> {
		
		private List<String> keyList;
		
		public KeysIterable(List<String> keyList) {
			this.keyList = keyList;
		}
		
		public KeysIterable(String... keys) {
			this.keyList = Arrays.asList(keys);
			System.out.println(keyList);
		}

		@Override
		public Iterator<String> iterator() {
			return this.keyList.iterator();
		}
		
	}
}
