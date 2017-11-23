package lxq.user.util.cache.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lxq.user.util.cache.Cache;
import lxq.user.util.cache.Serializer;
import lxq.user.util.cache.SerializerManage;

/**
 * 内存序列�?
 * 
 */
public class MemorySerializeCache implements Cache {

	protected Serializer serializer;
	protected String name;
	protected Map<String, byte[]> map = new ConcurrentHashMap<String, byte[]>();

	public MemorySerializeCache() {
		this.serializer = SerializerManage.getDefault();
	}

	public MemorySerializeCache(Serializer serializer) {
		this.serializer = serializer;
	}

	public String name() {
		return name;
	}

	public MemorySerializeCache name(String name) {
		this.name = name;
		return this;
	}

	public MemorySerializeCache add(String key, Object value) {
		try {
			map.put(key, serializer.serialize(value));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		try {
			return (T) serializer.deserialize(map.get(key));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object remove(String key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public int size() {
		return map.size();
	}

	public Set<String> keys() {
		if (map.size() == 0) {
			return null;
		}
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public <T> Collection<T> values() {
		if (map.size() == 0) {
			return null;
		}

		Collection<T> list = new ArrayList<T>();
		for (byte[] obj : map.values()) {
			try {
				list.add((T) serializer.deserialize(obj));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

}
