package service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.AdminMapper;
import mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.ProductInfo;
import pojo.ProductInfoExample;
import pojo.vo.ProductInfoVo;
import service.ProductInfoService;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {


        return productInfoMapper.selectByExample(new ProductInfoExample());
    }


    //分页
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {

        //使用pagehelper.startPage方法完成分页设置
        PageHelper.startPage(pageNum,pageSize);

        //进行PageInfo的数据分装
        //进行有条件的查询操作必须要创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
        //降序使用getOrderByClause方法
        example.getOrderByClause("p_id desc");

        //取集合
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //将查到的集合封装起来
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getByID(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {

        PageHelper.startPage(vo.getPage(),pageSize);

        List<ProductInfo> list = productInfoMapper.selectCondition(vo);

        return new PageInfo<>(list);
    }

}
