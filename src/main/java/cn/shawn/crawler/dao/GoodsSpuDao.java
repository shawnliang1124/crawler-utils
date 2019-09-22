package cn.shawn.crawler.dao;

import cn.shawn.crawler.entity.FhGoodsSpuEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 描述:
 *
 * @author Shawn Liang
 * @create 2019-09-15 15:09
 * @package cn.shawn.crawler.dao
 * @contact https://github.com/shawnliang1124
 */
@Repository
public interface GoodsSpuDao extends PagingAndSortingRepository<FhGoodsSpuEntity,Integer> {
}
