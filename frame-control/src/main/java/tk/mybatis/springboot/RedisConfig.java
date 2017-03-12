package tk.mybatis.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.dhcc.ecm.business.util.BusinessProperties;

@Configuration
public class RedisConfig {
	@Autowired
	private BusinessProperties properties;
	
   @Bean
    public RedisConnectionFactory redisConnectionFactory() {
    	JedisConnectionFactory factory = new JedisConnectionFactory();
    	factory.setHostName(properties.getHost());
    	factory.setPassword(properties.getPassword());
    	factory.setPort(Integer.parseInt(properties.getPort()));
        return factory;
    }
}
