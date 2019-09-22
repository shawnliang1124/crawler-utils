package cn.shawn.crawler.dao;

import cn.shawn.crawler.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDao extends CrudRepository<Test,Integer> {
    @Query(nativeQuery = true,
            value = "select * from test " +
                    "where id  BETWEEN ?1 and ?2")
    List<Test> findAllIdBetween(int start, int end);
}
