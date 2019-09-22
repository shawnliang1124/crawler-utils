package cn.shawn.crawler.dao;

import cn.shawn.crawler.entity.GcGoodsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * *  @author jiajing.liang
 * *  @date 2019/9/11 19:33
 * *  @description
 */
@Repository
public interface GcGoodsDao extends CrudRepository<GcGoodsEntity,Integer>{
}
