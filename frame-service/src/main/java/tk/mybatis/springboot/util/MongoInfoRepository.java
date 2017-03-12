package tk.mybatis.springboot.util;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dhcc.ecm.business.mybatis.file.model.Article;

public interface  MongoInfoRepository  extends MongoRepository<Article, String> {

}