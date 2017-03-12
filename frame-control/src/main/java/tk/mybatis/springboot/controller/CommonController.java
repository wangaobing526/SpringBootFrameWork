/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.controller;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import tk.mybatis.springboot.model.BaseEntity;

/**
 * @author liuzh
 * @since 2015-12-19 11:10
 */
@RestController
@RequestMapping("/common")
public  class CommonController {
	 
//	 @RequestMapping(value="getTasks",method=RequestMethod.GET)
//	  public List<TTemplateTask> getTasks(String uuid) throws Exception {			
//		 	return bussTemplateService.getTasks(uuid);
//	  }
//	 
//	 @RequestMapping(value="getmap",method=RequestMethod.GET)
//	  public Map<String,String> getProjection(String uuid) throws Exception {			
//		 	return bussTemplateService.getProjectionBytd(uuid);
//	  }
//	 @RequestMapping(value="oneToMany",method=RequestMethod.GET)
//	  public TBussTemplate getAllByOneToMany(String uuid) throws Exception {			
//		 	return bussTemplateService.getTemplateAndTaskById(uuid);
//	  }
	 
	 
//	@RequestMapping(value = "add")
//    public String add() {	
//		TBussMenu menu = new TBussMenu();
//		menu.setId("123");
//		menu.setCode("code");
//		menu.setName("nameadd");
//		menu.setMenuUrl("menuUrl");
//		menu.setParentId("parent");
//		menu.setDiscription("d");
//		//menu.setIcon("1");
//		menu.setLastUpdateDate(new Date());
//		menu.setLastUpdateUser("u");
//		menu.setSeqNo(11111111l);
//		menu.setCreateUser("wangxb");
//		menu.setCreateDate(new Date());
//		menu.setStatus("s0");
//		menu.setVersion(1l);
//		bussMenuService.save(menu);
//        ModelAndView result = new ModelAndView("view");
//        result.addObject("country", new Country());
//        return "success";
//    }
	
//	@RequestMapping(value="condition",method=RequestMethod.GET)
//    public PageInfo<TBussMenu> getAllByCondition(PageInfo pageInfo) throws Exception {
//		
//		TBussMenuQuery menuQuery = new TBussMenuQuery();		
//		menuQuery.setStatus("s0");		
//         Page<TBussMenu> page = bussMenuService.getAllByCondition(menuQuery, pageInfo.getPageNum(), pageInfo.getPageSize());
//        
//         return page.toPageInfo();
//    }
	
    @RequestMapping(value="list",method=RequestMethod.GET)
    public List getAlllist(BaseEntity baseEntity,String tablename,HttpServletRequest request) throws Exception {
    	
    	String serviceImplName = getServiceFullName(tablename);
    	
    	WebApplicationContext  context = RequestContextUtils.getWebApplicationContext(request);
    	Object serviceObj = context.getBean(serviceImplName);
    	
    	Class [] params = {Integer.class,Integer.class};
    	Method method = serviceObj.getClass().getMethod("getAllPage", params);
    	Integer [] arguments = {baseEntity.getPage(),baseEntity.getRows()};
    	
    	List objList = (List)method.invoke(serviceObj, arguments);
    	
        return objList;
    }
    @RequestMapping(value="query",method=RequestMethod.GET)
    public Object getByid(String tablename,String id,HttpServletRequest request) throws Exception {
    	
    	String serviceImplName = getServiceFullName(tablename);
    	
    	WebApplicationContext  context = RequestContextUtils.getWebApplicationContext(request);
    	Object serviceObj = context.getBean(serviceImplName);    	
    	Class [] params = {String.class};
    	Method method = serviceObj.getClass().getMethod("getById", params);
    	String [] arguments = {id};
    	
    	Object obj = method.invoke(serviceObj, arguments);
    	
       
        return obj;
    }
    private String getServiceFullName(String tablename){
    	String servicePreName = generateServiceByTbName(tablename);    	
    	return servicePreName+"ServiceImpl";
    }
    private String generateServiceByTbName(String tbname){
    	String [] partNames = tbname.split("_");
    	String returnServiceName = "";
    	if(partNames!=null && partNames.length>1){
    		for(int i =1;i<partNames.length;i++){
    			returnServiceName+=StringUtils.upperCase(partNames[i].substring(0, 1)).concat(partNames[i].substring(1));
    		}
    		return partNames[0]+returnServiceName;
    	}else if(partNames!=null && partNames.length==1){
    		return partNames[0];
    	}
    	return null;
    }
}
