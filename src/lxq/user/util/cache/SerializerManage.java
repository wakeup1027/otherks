package lxq.user.util.cache;

import java.util.HashMap;
import java.util.Map;

public class SerializerManage {

	private static final Map<String, Serializer> map = new HashMap<String, Serializer>();
	private static String DEFAULT_KEY = null;

	static {
		JavaSerializer javaSerializer = new JavaSerializer();
		add(javaSerializer);
		DEFAULT_KEY = javaSerializer.name();
	}

	public static void setDefaultKey(String defaultKey) {
		DEFAULT_KEY = defaultKey;
	}
	
	/**
	 * @param key
	 * @param serializer
	 */
	public static void add(String key, Serializer serializer) {
		map.put(key, serializer);
	}
	
	public static void add(Serializer serializer) {
		map.put(serializer.name(), serializer);
	}

	public static Serializer get(String key) {
		return map.get(key);
	}
	
	public static Serializer getDefault() {
		return map.get(DEFAULT_KEY);
	}

	public static byte[] serialize(Object obj) throws Exception {
		return getDefault().serialize(obj);
	}

	public static Object deserialize(byte[] bytes) throws Exception {
		return getDefault().deserialize(bytes);
	}
}
