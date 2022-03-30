package controller;

import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pojo.ProductInfo;
import pojo.vo.ProductInfoVo;
import service.ProductInfoService;
import utils.FileNameUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    //每页显示的记录数
    public static final int PAGE_SIZE = 5;


    String saveFileName = "";
    @Autowired
    ProductInfoService productInfoService;
    //不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    //分页
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodvo");
        if(vo!=null){
            info = productInfoService.splitPageVo((ProductInfoVo)vo,PAGE_SIZE);
            request.getSession().removeAttribute("prodvo");
        }else{
            info = productInfoService.splitPage(1,PAGE_SIZE);
        }
        //得到第一页的数据

        request.setAttribute("info",info);

        return "product";
    }

    //ajax分页翻页
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){

        PageInfo info = productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);

    }


    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //要进行文件上传操作
        //取文件名
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //取路径
        try {
            String path = request.getServletContext().getRealPath("/image_big");
            //转存
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //为了在客户端显示图片，要将存储的文件名回传下去，由于是自定义的上传插件，所以此处要手工处理JSON
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);
        //切记切记：JSON对象一定要toString()回到客户端
        return object.toString();
    }

    //执行增加操作
    @RequestMapping("/save")
    public String save(ProductInfo info, HttpServletRequest request) {
        //图片处理好后，设置到商品对象中
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        //到此止，商品对象构建完毕，有自动从表单元素注入的，有上传图片的，有上架日期
        //完成数据库增加操作
        int num = -1;
        try {
            num = productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0)
            request.getSession().setAttribute("msg", "增加成功！");
        else
            request.getSession().setAttribute("msg", "增加失败!");
        saveFileName="";
        //增删改后用重定向跳转
        return "redirect:/prod/split.action";
    }


    @RequestMapping("/one")
    public String one(Integer pid,ProductInfoVo vo,Model model,HttpSession session) {
        ProductInfo info = productInfoService.getByID(pid);
        model.addAttribute("prod", info);
        session.setAttribute("prodvo",vo);
        return "update";
    }


    //更新操作
    @RequestMapping("/update")
    public String update(ProductInfo info,Integer page,HttpServletRequest request) {

        //因为是修改，所以要判断有没有点文件上传的铵钮
        if(!saveFileName.equals("")){
            info.setpImage(saveFileName);
        }
        boolean flag = false;
        //有没有时间改变
        //此处要进行info对象的上架时间的更新
        //首先要判断当前新更新上来的日期不能大于当前日期
        if (info.getpDate() != null) {
            if (info.getpDate().getTime() > System.currentTimeMillis()) {
                //日期不正常了，则置为空，底层做更新处理时不做更改
                info.setpDate(null);
                flag=true;
            }
        }
        //完成对象更新
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (num > 0) {
            if (flag) {
                request.getSession().setAttribute("msg", "日期不能大于当前日期");
            } else {
                request.getSession().setAttribute("msg", "更新成功！");
            }
        } else {
            request.getSession().setAttribute("msg", "更新失败");
        }
        saveFileName="";
        return "redirect:/prod/split.action?page="+page;
    }




    @RequestMapping("/delete")
    public String delete(int pid,ProductInfoVo vo,HttpServletRequest request){
        int num = -1;
        try {
            num = productInfoService.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg", "删除成功");
            request.getSession().setAttribute("deleteProductvo",vo);

        } else {
            request.setAttribute("msg", "删除失败");
        }
        //删除成功之后跳转到 ajax分页显示
        //因为删除完以后,只是异步刷新表格而不是整个页面,request添加的属性就显示不了
        return "forward:/prod/deleteAjaxSplit.action";
    }

    //重新写一个 ajax分页有返回值
    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit.action", produces = "text/html;charset=utf-8")//因为有中文显示所以设置编码
    public Object deleteAjaxSplit(HttpServletRequest request) {
        //重新,取得第一页的数据
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteProductvo");
        if(vo!=null){
            info = productInfoService.splitPageVo((ProductInfoVo)vo,PAGE_SIZE);

        }else{
            info = productInfoService.splitPage(1, PAGE_SIZE);;
        }

        request.getSession().setAttribute("info", info);
        return request.getAttribute("msg");
    }

    //批量删除
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){
        String []ps = pids.split(",");
        try {   int num = productInfoService.deleteBatch(ps);
            if(num>0)
            {
                request.setAttribute("msg","批量删除成功");
            }else{
                request.setAttribute("msg","批量删除失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品不可删除");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }




    }



