package tk.mybatis.springboot.controller.file;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.dhcc.ecm.business.exception.BizResponse;
import com.dhcc.ecm.business.exception.ErrorCode;
import com.dhcc.ecm.business.file.TInstanceFileService;
import com.dhcc.ecm.business.mybatis.file.model.TInstanceFile;
import com.dhcc.ecm.business.util.BusinessProperties;
import com.dhcc.ecm.business.util.GlobalConstants;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import tk.mybatis.springboot.controller.AbstractRestHandler;

/**
 * @ClassName TBussGuideController
 * @Description 文件管理Controller
 * @author wangaobing wangaobing001@dhcc.com.cn
 * @date 2016-07-18
 */
@RestController
@RequestMapping(value = "/file")
@Api(value = "common-file-api", description = "文件管理接口服务", tags = "文件管理")
public class TInstanceFileController extends AbstractRestHandler {
	@Autowired
	private TInstanceFileService tInstanceFileService;

	@Autowired
	private BusinessProperties properties;

	/**
	 * 文件上传
	 * @param request 传入的表单内容及附件内容集合
	 * @return 上传成功或者失败信息
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ApiOperation(value = "文件上传", notes = "文件上传", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回分页数据") })
	public BizResponse fileUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		String createUser = request.getParameter("createUser");
		String remark = request.getParameter("remark");
		String instanceId = request.getParameter("instanceId");
		String taskId = request.getParameter("taskId");
		String originalFileName = request.getParameter("originalFileName");
		String fileType = request.getParameter("fileType");

		BizResponse bizResponse = null;
		try {
			// 设置上下方文
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest =

				(MultipartHttpServletRequest) request;
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 由CommonsMultipartFile继承而来,拥有上面的方法.
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String fileName = file.getOriginalFilename();
						// 如果文件夹不存在则创建
						createFolder(properties.getFilePath());
						String path = properties.getFilePath() + "/" + fileName;
						File localFile = new File(path);
						file.transferTo(localFile);
						// 设置相关值
						TInstanceFile instanceFile = setTInstanceFile(createUser, remark, instanceId, taskId,
								originalFileName, fileType, fileName, file.getSize(), path);
						tInstanceFileService.save(instanceFile);
					}
				}
			}
			bizResponse = new BizResponse("文件上传成功！");
		} catch (Exception e) {
			bizResponse = new BizResponse(e);
		}
		return bizResponse;
	}

	/**
	 * 文件下载
	 * @param id
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/fileDownLoad/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "文件下载", notes = "文件下载", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回附件流") })
	public ResponseEntity<InputStreamResource> fileDownLoad(
			@ApiParam(value = "文件ID", required = true) @PathVariable("id") String id, HttpServletRequest request)
					throws IOException {
		TInstanceFile tInstanceFile = tInstanceFileService.getById(id);
		FileSystemResource file = new FileSystemResource(tInstanceFile.getFilePath());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
	}
	
	/**
	 * 保存文件信息
	 * @param tInstanceFile 文件信息对象
	 * @return
	 */
	@RequestMapping(value = "/saveFile", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = { "application/json" })
	@ApiOperation(value = "保存文件信息", notes = "传入文件信息对象进行保存", position
	= 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回保存成功") })
	public BizResponse saveFile(@ApiParam(value = "文件信息内容", required = true) @RequestBody TInstanceFile tInstanceFile) {
		BizResponse returnResponse = null;
		try {
			tInstanceFileService.save(tInstanceFile);
			returnResponse = new BizResponse("保存文件信息成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.SAVE_ERROR, "保存文件信息失败");
		}
		return returnResponse;
	}
	
	/**
	 * 批量删除文件
	 * 
	 * @param ids
	 *            文件ID数组，一至多个
	 * @return 删除成功标识
	 */
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST, produces = { "application/json" }, consumes = {
			"application/json" })
	@ApiOperation(value = "删除文件内容", notes = "根据传入参数：文件ID", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回删除成功") })
	public BizResponse deleteFile(@ApiParam(value = "文件ID，一至多个", required = true) @RequestBody String[] ids) {
		BizResponse returnResponse = null;
		try {
			tInstanceFileService.deleteByIds(ids);
			returnResponse = new BizResponse("删除文件信息成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.DELETE_ERROR, "删除文件信息失败");
		}
		return returnResponse;
	}

	
	/**
	 * 删除文件
	 * 
	 * @param id
	 *            文件ID
	 * @return 删除成功标识
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, produces = { "application/json" }, consumes = {
			"application/json" })
	@ApiOperation(value = "删除文件内容", notes = "根据文件ID删除文件信息", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回删除成功") })
	public BizResponse deleteByIds(@ApiParam(value = "文件ID", required = true) @PathVariable String id) {
		BizResponse returnResponse = null;
		try {
			tInstanceFileService.deleteById(id);
			returnResponse = new BizResponse("删除文件信息成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.DELETE_ERROR, "删除文件信息失败");
		}
		return returnResponse;
	}

	/**
	 * 更新文件信息内容
	 * @param tInstanceFile
	 * @return
	 */
	@RequestMapping(value = "/updateFile", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = { "application/json" })
	@ApiOperation(value = "更新文件信息", notes = "更新文件信息内容", position = 5,
	produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回保存成功") })
	public BizResponse updateFile(
			@ApiParam(value = "文件信息内容", required = true) @RequestBody TInstanceFile tInstanceFile) {
		BizResponse returnResponse = null;
		try {
			tInstanceFileService.updateNotNull(tInstanceFile);
			returnResponse = new BizResponse("更新文件信息成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.SAVE_ERROR, "更新文件信息失败");
		}
		return returnResponse;
	}

	/**
	 * 根据条件查询文件内容
	 * @param tInstanceFile
	 * @return
	 */
	@RequestMapping(value = "/queryFile", method = RequestMethod.POST, produces = { "application/json" }, consumes = {
			"application/json" })
	@ApiOperation(value = "查询文件信息", notes = "根据条件查询文件信息内容，不分页", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回保存成功") })
	public BizResponse queryFile(
			@ApiParam(value = "查询文件信息", required = true) @RequestBody TInstanceFile tInstanceFile) {
		BizResponse returnResponse = null;
		try {
			List<TInstanceFile> list = tInstanceFileService.selectByExample(tInstanceFile);
			returnResponse = new BizResponse(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.QUERY_ERROR, "查询文件信息失败");
		}
		return returnResponse;
	}

	
	@RequestMapping(value = "/queryFilePage", method = RequestMethod.POST, produces = { "application/json" }, consumes = { "application/json" })
	@ApiOperation(value = "查询文件分页信息", notes = "根据条件查询文件管理分页内容", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回分页数据") })
	public BizResponse queryGuidePage(
			@ApiParam(value = "文件对象", required = true) @RequestBody TInstanceFile tInstanceFile) {
		BizResponse returnResponse = null;
		try {
			PageInfo<TInstanceFile> pageInfo = tInstanceFileService
					.findTInstanceFilePage(tInstanceFile);
			returnResponse = new BizResponse(pageInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.QUERY_ERROR, "查询办事指南失败");
		}
		return returnResponse;
	}
	
	
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST, produces = { "application/json" }, consumes = { "application/json" })
	@ApiOperation(value = "查询所有文件信息", notes = "查询所有文件信息，并分页", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "返回分页数据") })
    public BizResponse getAll(TInstanceFile tInstanceFile) {
		BizResponse returnResponse = null;
		try{
			List<TInstanceFile> userInfoList = tInstanceFileService.getAllPage(tInstanceFile.getPage(),tInstanceFile.getRows());
			returnResponse = new BizResponse(new PageInfo<TInstanceFile>(userInfoList));
		}catch(Exception e){
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.QUERY_ERROR, "查询文件信息列表失败");
		}
        return returnResponse;
    }
	/**
	 * 根据业务ID查询文件列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/fileList/{id}", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = { "application/json" })
	@ApiOperation(value = "查询文件信息列表", notes = "根据业务ID查询文件信息列表", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询信息列表成功") })
	public BizResponse fileList(@ApiParam(value = "事例id", required = true) @PathVariable String id) {
		BizResponse returnResponse = null;
		try {
			TInstanceFile tf = new TInstanceFile();
			tf.setInstanceId(id);
			List<TInstanceFile> list = tInstanceFileService.fileListForClass(tf);
			returnResponse = new BizResponse(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnResponse = new BizResponse(ErrorCode.QUERY_ERROR, "查询文件信息列表失败");
		}
		return returnResponse;
	}

    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET, produces = {
	"application/json" }, consumes = { "application/json" })
    @ApiOperation(value = "查询文件信息", notes = "根据业务ID查询文件信息", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询文件信息成功") })
    public BizResponse view(@PathVariable String id) {
    	BizResponse returnResponse = null;
    	try{
    		TInstanceFile file = tInstanceFileService.getById(id);
    		returnResponse = new BizResponse(file);
    	}catch(Exception e){
    		returnResponse = new BizResponse(ErrorCode.QUERY_ERROR, "根据ID查询文件信息失败");
    	}
        return returnResponse;
    }

	public TInstanceFile setTInstanceFile(String createUser, String remark, String instanceId, String taskId,
			String originalFileName, String fileType, String fileName, Long fileSize, String filePath) {
		TInstanceFile file = new TInstanceFile();
		file.setCreateUser(createUser);
		file.setRemark(remark);
		file.setInstanceId(instanceId);
		file.setTaskId(taskId);
		file.setOriginalFileName(originalFileName);
		file.setFileType(fileType);
		// 状态默认可用
		file.setStatus(GlobalConstants.TINSTANCEFILE_STATUS_S0);
		file.setCreateDate(new Date());
		file.setFileName(fileName);
		file.setFileSize(fileSize);
		file.setFilePath(filePath);
		file.setFileExt(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()));
		return file;
	}

	/**
	 * 判断文件夹是否为空，为空则创建
	 */
	private void createFolder(String filePath) {
		try {
			if (!(new File(filePath).isDirectory())) {
				new File(filePath).mkdir();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/toUpload", method = RequestMethod.GET)
	public ModelAndView upload() {
		ModelAndView result = new ModelAndView("upload");
		return result;
	}
}
