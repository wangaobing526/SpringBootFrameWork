package com.dhcc.ecm.business.mybatis.file.mapper;

import java.util.List;

import com.dhcc.ecm.business.mybatis.file.model.TInstanceFile;

import tk.mybatis.springboot.util.MyMapper;

public interface TInstanceFileMapper extends MyMapper<TInstanceFile,TInstanceFile> {

	List<TInstanceFile> fileListForClass(TInstanceFile tf);
	
	public List<TInstanceFile> findTInstanceFilePage(TInstanceFile tInstanceFile);
}