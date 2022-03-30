package mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pojo.ProductType;
import pojo.ProductTypeExample;

public interface ProductTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    int countByExample(ProductTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    int deleteByExample(ProductTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    int deleteByPrimaryKey(Integer typeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    int insert(ProductType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    int insertSelective(ProductType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    List<ProductType> selectByExample(ProductTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    ProductType selectByPrimaryKey(Integer typeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    int updateByExampleSelective(@Param("record") ProductType record, @Param("example") ProductTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    int updateByExample(@Param("record") ProductType record, @Param("example") ProductTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    int updateByPrimaryKeySelective(ProductType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_type
     *
     * @mbggenerated Wed Mar 23 09:08:33 CST 2022
     */
    int updateByPrimaryKey(ProductType record);
}