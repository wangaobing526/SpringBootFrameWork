package com.dhcc.ecm.business.util.redis;

public interface CacheProvider {
	
	/**
	 * 有二级关联关系的缓存 map<cacheName Map<key,value>> 如数据字典跟数据字典项的缓存
	 * @param key 缓存项
	 * @param value 缓存项的值
	 * @param cacheName 缓存名字
	 */
	public void set(String key, Object value, String cacheName);
	/**
	 * 通过缓存名查询缓存项的值
	 * @param key 缓存项的值
	 * @param cacheName 缓存名称
	 * @return 返回缓存项的值
	 */
	public Object get(String key, String cacheName);
	/**
	 * 清除缓存项的值
	 * @param key 选项项
	 * @param cacheName	缓存名称
	 */
	public void clearCacheValue(String key, String cacheName);
	/**
	 * 直接缓存
	 * @param cacheName	缓存名称
	 * @param value 缓存值
	 */
	public void set(String cacheName, Object value);
	/**
	 * 通过缓存名称获取缓存值
	 * @param cacheName
	 * @return 返回缓存值
	 */
	public Object get(String cacheName);
	/**
	 * 清除缓存值
	 * @param cacheName
	 */
	public void removeCache(String cacheName);
	/**
	 * 清除所有缓存
	 */
	public void clearAllCache();
	/**
	 * 保存cache数据
	 * @param cacheKeyName
	 * @param key
	 * @param value
	 */
	public void setCache(String cacheItemName,String key,Object value);

}
