package com.dhcc.ecm.business.util.redis;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SdrCacheProvider implements CacheProvider {

	private final Logger logger = Logger.getLogger(SdrCacheProvider.class);
	private static SdrCacheProvider sdrCacheProvider;


	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	

	@Deprecated
	@Override
	public void set(String key, Object value, String cacheName) {
		redisTemplate.opsForHash().put(cacheName, key, value);
	}

	@Deprecated
	@Override
	public Object get(String key, String cacheName) {
		return redisTemplate.opsForHash().get(cacheName, key);
	}
	@SuppressWarnings("unchecked")
	public void hset(String key, Object map) {
		redisTemplate.opsForHash().putAll(key, (Map<? extends Object, ? extends Object>) map);
	}
	public void hset(String key, Object hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	public Object hget(String key, Object hashKey) {
		if (null == key || null == hashKey) {
			return null;
		}
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	public Object hget(String key) {
		if (null == key) {
			return null;
		}
		return redisTemplate.opsForHash().entries(key);
	}
	public void hremove(String key,String hashKeys) {
		if (null == key || null == hashKeys) {
			return;
		}
		 redisTemplate.opsForHash().delete(key, hashKeys);
	}	

	@Override
	public void clearCacheValue(String key, String cacheName) {
		redisTemplate.opsForHash().delete(key, cacheName);
	}

	@Override
	public void set(String cacheName, Object value) {
		redisTemplate.opsForValue().set(cacheName, value);
	}

	@Override
	public Object get(String cacheName) {
		return redisTemplate.opsForValue().get(cacheName);
	}

	@Override
	public void removeCache(String cacheName) {
		redisTemplate.delete(cacheName);

	}

	@Override
	public void clearAllCache() {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection redisConnection)
					throws DataAccessException {
				redisConnection.flushDb();
				return true;
			}
		});
	}


	@Deprecated
	@Override
	public void setCache(String cacheItemName, String key, Object value) {
		redisTemplate.opsForHash().put(cacheItemName, key, value);
	}


	
	public Set<String> getKeys(String key){
		return redisTemplate.keys(key+"*");
	}

	public Set<Object> getHashKeys(String cache){
		return redisTemplate.opsForHash().entries(cache).keySet();
	}

	
//	public static void main(String[] args) {
//		SdrCacheProvider provider = getInstance();
//		provider.set("name", "hhy&dxr");
//		System.out.println(provider.get("name"));
//	}

//	public static SdrCacheProvider getInstance() {
//		if (sdrCacheProvider == null) {
//			if (SpringContextTool.getApplicationContext() == null) {
//				ApplicationContext ctx = new ClassPathXmlApplicationContext(
//						"redis-context.xml");
//				sdrCacheProvider = ctx.getBean(SdrCacheProvider.class);
//			} else {
//				sdrCacheProvider = SpringContextTool.getApplicationContext()
//						.getBean(SdrCacheProvider.class);
//			}
//		}
//		return sdrCacheProvider;
//	}
}
