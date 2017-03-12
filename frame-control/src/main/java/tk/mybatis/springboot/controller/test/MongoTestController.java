package tk.mybatis.springboot.controller.test;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dhcc.ecm.business.exception.BizResponse;
import com.dhcc.ecm.business.exception.ErrorCode;
import com.dhcc.ecm.business.mybatis.file.model.Article;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import tk.mybatis.springboot.util.MongoInfoRepository;

@RestController
public class MongoTestController {
	@Resource
	private MongoInfoRepository mongoRepository;

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = { "application/json" }, consumes = {
			"application/json" })
	@ApiOperation(value = "保存mongo", notes = "保存mongo", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回保存成功") })
	public BizResponse saveFile(@ApiParam(value = "article", required = true) @RequestBody Article article) {
		BizResponse returnResponse = null;
		try {
			mongoRepository.save(article);
			returnResponse = new BizResponse("保存Mogo成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.SAVE_ERROR, "保存文件信息失败");
		}
		return returnResponse;
	}

}
