package com.dhcc.ecm.business.file.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dhcc.ecm.business.base.service.impl.BaseService;
import com.dhcc.ecm.business.file.TInstanceFileService;
import com.dhcc.ecm.business.mybatis.file.mapper.TInstanceFileMapper;
import com.dhcc.ecm.business.mybatis.file.model.TInstanceFile;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.springboot.conf.TransactionService;

/**
 * @ClassName TInstanceFileServiceImpl
 * @Description 文件管理服务实现
 * @author wangaobing wangaobing001@dhcc.com.cn
 * @date 2016-07-18
 */
@TransactionService
public class TInstanceFileServiceImpl extends BaseService<TInstanceFile> implements TInstanceFileService {

	@Autowired
	TInstanceFileMapper tfMapper;
	
	@Override
	public List<TInstanceFile> fileListForClass(TInstanceFile tf) {
		List<TInstanceFile> tfList = tfMapper.fileListForClass(tf);
		return tfList;
	}

	/**
	 * 根据条件查询获得分页数据
	 * @param entity
	 * @return
	 */
	@Override
	public PageInfo<TInstanceFile> findTInstanceFilePage(TInstanceFile entity){
		 Page<TInstanceFile> page = PageHelper.startPage(entity.getPage(), entity.getRows(), "id");
		 List<TInstanceFile> list = tfMapper.findTInstanceFilePage(entity);
		 return  page.toPageInfo();
	}

}
