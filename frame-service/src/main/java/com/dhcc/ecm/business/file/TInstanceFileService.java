package com.dhcc.ecm.business.file;

import java.util.List;

import com.dhcc.ecm.business.base.service.IService;
import com.dhcc.ecm.business.mybatis.file.model.TInstanceFile;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName TInstanceFileService
 * @Description 文件管理服务接口
 * @author wangaobing wangaobing001@dhcc.com.cn
 * @date 2016-07-18
 */
public interface TInstanceFileService extends IService<TInstanceFile> {

	List<TInstanceFile> fileListForClass(TInstanceFile tf);
	
	/**
	 * 根据条件查询获得分页数据
	 * @param entity
	 * @return
	 */
	public PageInfo<TInstanceFile> findTInstanceFilePage(TInstanceFile entity);

}
