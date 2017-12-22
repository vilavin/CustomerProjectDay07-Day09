package com.simone.servlet;

import com.simone.bean.CustomerBean;
import com.simone.bean.PageBean;
import com.simone.service.CustomerService;
import com.simone.util.BeanUtil;
import com.simone.util.IDUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by dllo on 17/12/19.
 */
@WebServlet(name = "CustomerServlet",urlPatterns ="/customer")
public class CustomerServlet extends HttpServlet {
    private CustomerService service=new CustomerService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf8");
        response.setCharacterEncoding("utf8");
        request.setCharacterEncoding("utf8");
        String method=request.getParameter("method");
        switch (method){
            case "add":addCustomer(request,response);
                break;
            case "query":
                findAll(request,response);
                break;
            case "del":
                deleteCustomer(request,response);
                break;
            case "queryByCondition":
                queryByCondition(request,response);
                break;
            case "updateCustomer":
                queryById(request,response);
                break;

            case "edit":
                editCustomer(request,response);
                break;
        }

    }

    private void editCustomer(HttpServletRequest request, HttpServletResponse response) {
        CustomerBean customerBean=new CustomerBean();
        BeanUtil.requestToBean(request,customerBean);
        boolean flag=service.updateCustomer(customerBean);
        if (flag){
            request.setAttribute("msg","更改数据成功");
        }
        else{
            request.setAttribute("msg","更改数据失败");
        }
        try {
            request.getRequestDispatcher("/msg.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cid=request.getParameter("cid");

        boolean flag=service.deleteCustomer(cid);
        if (flag){
            request.setAttribute("msg","删除数据成功");
        }
        else{
            request.setAttribute("msg","删除数据失败");
        }

        request.getRequestDispatcher("/msg.jsp").forward(request,response);



    }


    private void queryById(HttpServletRequest request, HttpServletResponse response) {
        String cid=request.getParameter("cid");
        CustomerBean customerBean=service.queryById(cid);
        request.setAttribute("customer",customerBean);
        try {
            request.getRequestDispatcher("/edit.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/* //未分页之前取出所有数据的方法
    private void findAll(HttpServletRequest request, HttpServletResponse response) {
        List<CustomerBean> beanList=service.findAll();
        request.setAttribute("customers",beanList);
        try {
            request.getRequestDispatcher("/list.jsp").forward(request,response
            );
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    //分页查询
    private void findAll(HttpServletRequest request, HttpServletResponse response) {
        //获取页码数
        int pc=getPc(request);
        //每一页线束的数据条数
        int ps=10;
        //用于存放从数据库取出的数据
        PageBean<CustomerBean> pageBean=service.findAll(pc,ps);
        //设置要访问的地址
        pageBean.setUrl(getUrl(request));
        request.setAttribute("pageBean",pageBean);
        try {
            request.getRequestDispatcher("/list.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //普通高级搜索
//    private void queryByCondition(HttpServletRequest request, HttpServletResponse response) {
//        CustomerBean customerBean=new CustomerBean();
//        BeanUtil.requestToBean(request,customerBean);
//        List<CustomerBean> list=service.queryByCondition(customerBean);
//        request.setAttribute("customers",list);
//        try {
//            request.getRequestDispatcher("/list.jsp").forward(request,response);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    //分页高级搜索
private void queryByCondition(HttpServletRequest request, HttpServletResponse response) {

    try {
        //获取页码数
        int pc=getPc(request);
        //每一页线束的数据条数
        int ps=10;
        CustomerBean customerBean=new CustomerBean();

        HttpSession session = request.getSession();

        String abcgo = request.getParameter("abcgo");

        if("abcgo".equals(abcgo)){
            session.removeAttribute("has8888");
            session.removeAttribute("customerBean");
        }

        String has8888 = String.valueOf(session.getAttribute("has8888"));
        if("has8888".equals(has8888)){
            customerBean = (CustomerBean) session.getAttribute("customerBean");
        }else{
            BeanUtil.requestToBean(request,customerBean);
            session.setAttribute("has8888","has8888");
            session.setAttribute("customerBean",customerBean);
        }

        //用于存放从数据库取出的数据
        PageBean<CustomerBean> pageBean=service.queryByCondition(customerBean,pc,ps);
        pageBean.setUrl(getUrl(request));
        request.setAttribute("pageBean",pageBean);
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    } catch (ServletException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private String getUrl(HttpServletRequest request) {
        //获取项目名
        String contextPath =request.getContextPath();
        //获取servlet路径
        String servletPath=request.getServletPath();
        //获取参数内容  就是?后面的东西
        String queryString=request.getQueryString();
        if(queryString.contains("&pc=")){
            //找到最后一个"&pc="出现的位置
            int index=queryString.lastIndexOf("&pc=");
            //截取参数内容
            //例子
            /*
              /customer?method=findAll&pc=1
              queryString方法获取的是method=findAll&pc=1
              &pc这一段内容是在变化的,所以我们不想要
              先索引&pc=1的位置 ,然后截取前半段
              最终的结果就是 method=findAll
              如果我们想要完整的url地址
              项目名+servlet地址+?+method=findAll
              /

             */
            queryString=queryString.substring(0,index);
        }
        return contextPath+servletPath+"?"+queryString;
    }
    public int getPc(HttpServletRequest request){
        String pc=request.getParameter("pc");
        int p;
        if (!IDUtil.isEmpty(pc)){
            p=Integer.valueOf(pc);
        }else {
            p=1;
        }
        return p;
    }

    //添加用户
    private void addCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //用于存放前端所提供的信息
        CustomerBean customerBean=new CustomerBean();
        BeanUtil.requestToBean(request,customerBean);
        boolean flag=service.addCustomer(customerBean);
        if (flag){
            request.setAttribute("msg","添加数据成功");
        }
        else{
            request.setAttribute("msg","添加数据失败");
          }
        request.getRequestDispatcher("/msg.jsp").forward(request,response);

    }

}
