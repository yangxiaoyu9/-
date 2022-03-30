package service;

import com.github.pagehelper.PageInfo;
import pojo.ProductInfo;
import pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {

    //显示全部商品
    List<ProductInfo> getAll();


    //分页共能的实现
    PageInfo splitPage(int pageNum,int pageSize);

    int save(ProductInfo info);

      ProductInfo getByID(int pid);


    int update(ProductInfo info);

    int delete(int pid);

    int deleteBatch(String []ids);

    List<ProductInfo> selectCondition(ProductInfoVo vo);

    public PageInfo splitPageVo(ProductInfoVo vo,int pageSize);

}
