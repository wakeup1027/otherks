package lxq.user.util.cache;

import java.io.IOException;

public interface Serializer {
	
	public String name();
	
	public byte[] serialize(Object obj) throws IOException ;
	
	public <T> T deserialize(byte[] bytes) throws IOException ;
	
}
