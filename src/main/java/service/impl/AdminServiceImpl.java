package service.impl;

import mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Admin;
import pojo.AdminExample;
import service.AdminService;
import utils.MD5Util;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

    //在业务逻辑层中，一定会有数据访问层的对象
    @Autowired
    AdminMapper adminMapper;


    @Override
    public Admin login(String name, String pwd) {

        //根据用户名查用户对象回来，取出查回来的对象的密码和传来的密码对比，判断登录是否成功
        //创建条件封装的对象AdminExample
        AdminExample example = new AdminExample();
        //将用户名作为条件封装进AdminExample对象
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            Admin admin = list.get(0);
            String miPwd = MD5Util.getMD5(pwd);
            if (miPwd.equals(admin.getaPass())) {

                return admin;
            }  System.out.println(miPwd);
            System.out.println(admin.getaPass());
        }

        return null;

    }
}