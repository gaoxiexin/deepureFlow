package com.tasly.deepureflow.cache;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.tasly.deepureflow.util.ProtoStuffSerializerUtil;

/**
 * 
 * @ClassName:  RedisCache   
 * @Description:Redis缓存配置 
 * @author: 高燮訢  
 * @date:   Nov 4, 2016 10:53:39 AM   
 *
 */
@Component
public class RedisCache {
	
	public final static String CAHCENAME="cache";//缓存名称
	public final static int CAHCETIME=600;//Ĭ缓存超时

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 
	 * @Title: putCache   
	 * @Description: 增加缓存key和value
	 * @param: @param key
	 * @param: @param obj
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public <T> boolean putCache(String key, T obj) {
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = ProtoStuffSerializerUtil.serialize(obj);
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.setNX(bkey, bvalue);
			}
		});
		return result;
	}

	/**
	 * 
	 * @Title: putCacheWithExpireTime   
	 * @Description: 带失效时间的缓存增加
	 * @param: @param key
	 * @param: @param obj
	 * @param: @param expireTime      
	 * @return: void      
	 * @throws
	 */
	public <T> void putCacheWithExpireTime(String key, T obj, final long expireTime) {
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = ProtoStuffSerializerUtil.serialize(obj);
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setEx(bkey, expireTime, bvalue);
				return true;
			}
		});
	}

	/**
	 * 
	 * @Title: putListCache   
	 * @Description: 增加value类型为list的缓存
	 * @param: @param key
	 * @param: @param objList
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public <T> boolean putListCache(String key, List<T> objList) {
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = ProtoStuffSerializerUtil.serializeList(objList);
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.setNX(bkey, bvalue);
			}
		});
		return result;
	}

	/**
	 * 
	 * @Title: putListCacheWithExpireTime   
	 * @Description: 增加value类型为list并且带失效时间的缓存
	 * @param: @param key
	 * @param: @param objList
	 * @param: @param expireTime
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public <T> boolean putListCacheWithExpireTime(String key, List<T> objList, final long expireTime) {
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = ProtoStuffSerializerUtil.serializeList(objList);
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setEx(bkey, expireTime, bvalue);
				return true;
			}
		});
		return result;
	}

	/**
	 * 
	 * @Title: getCache   
	 * @Description: 获取缓存
	 * @param: @param key
	 * @param: @param targetClass
	 * @param: @return      
	 * @return: T      
	 * @throws
	 */
	public <T> T getCache(final String key, Class<T> targetClass) {
		byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
		if (result == null) {
			return null;
		}
		return ProtoStuffSerializerUtil.deserialize(result, targetClass);
	}

	/**
	 * 
	 * @Title: getListCache   
	 * @Description:获取list的缓存
	 * @param: @param key
	 * @param: @param targetClass
	 * @param: @return      
	 * @return: List<T>      
	 * @throws
	 */
	public <T> List<T> getListCache(final String key, Class<T> targetClass) {
		byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
		if (result == null) {
			return null;
		}
		return ProtoStuffSerializerUtil.deserializeList(result, targetClass);
	}

	/**
	 * 
	 * @Title: deleteCache   
	 * @Description: 根据key删除缓存
	 * @param: @param key      
	 * @return: void      
	 * @throws
	 */
	public void deleteCache(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 
	 * @Title: deleteCacheWithPattern   
	 * @Description: 根据正则删除缓存 
	 * @param: @param pattern      
	 * @return: void      
	 * @throws
	 */
	public void deleteCacheWithPattern(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		redisTemplate.delete(keys);
	}

	/**
	 * 
	 * @Title: clearCache   
	 * @Description:清除所有缓存
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	public void clearCache() {
		deleteCacheWithPattern(RedisCache.CAHCENAME+"|*");
	}
}