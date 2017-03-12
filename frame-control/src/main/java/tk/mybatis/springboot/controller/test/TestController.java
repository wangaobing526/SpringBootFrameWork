package tk.mybatis.springboot.controller.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dhcc.ecm.business.exception.BizResponse;
import com.dhcc.ecm.business.exception.ErrorCode;
import com.dhcc.ecm.business.util.BusinessProperties;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import tk.mybatis.springboot.controller.AbstractRestHandler;
/**
 * redis测试类
 * @author wangaobing
 *
 */
@RestController
@RequestMapping(value = "/test")
@Api(value = "common-test-api", description = "测试接口服务", tags = "测试接口")
public class TestController extends AbstractRestHandler {

	@Autowired
	private BusinessProperties properties;

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource
	private RedisTemplate redisTemplate;

	@RequestMapping(value = "/insertRedis", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = { "application/json" })
	@ApiOperation(value = "测试", notes = "测试", position
	= 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回成功") })
	public BizResponse saveFile(@ApiParam(value = "测试内容", required = true) @RequestBody String test) {
		BizResponse returnResponse = null;
		try {
			// 保存字符串
			stringRedisTemplate.opsForValue().set(test, test);
			List<String> list = new ArrayList();
			list.add(test);
			list.add(test+"+2");
			redisTemplate.opsForValue().set(test+"+list", list);
			
			Map map = new HashMap();
			map.put(test+"+map",list);
			redisTemplate.opsForValue().set(test+"+map", map);
			returnResponse = new BizResponse(stringRedisTemplate.opsForValue().get(test));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.SAVE_ERROR, "测试失败");
		}
		return returnResponse;
	}
	
	@RequestMapping(value = "/queryRedis", method = RequestMethod.POST, produces = {
	"application/json" }, consumes = { "application/json" })
	@ApiOperation(value = "测试", notes = "测试", position
	= 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回成功") })
	public BizResponse queryRedis(@ApiParam(value = "测试内容", required = true) @RequestBody String test) {
	BizResponse returnResponse = null;
	try {
		// 保存字符串
		String strValue = stringRedisTemplate.opsForValue().get(test);
		List list = (List)redisTemplate.opsForValue().get(test+"+list");
		Map map = (HashMap)redisTemplate.opsForValue().get(test+"+map");
		System.out.println("list============="+list);
		System.out.println("map============="+map);
		returnResponse = new BizResponse(strValue);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		returnResponse = new BizResponse(ErrorCode.SAVE_ERROR, "测试失败");
	}
		return returnResponse;
	}
	

}
