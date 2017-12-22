package com.simone.dao;

import com.simone.bean.CustomerBean;
import com.simone.bean.PageBean;
import com.simone.util.BeanUtil;
import com.simone.util.IDUtil;
import com.simone.util.MyQueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao是用于处理数据库的
 */
public class CustomerDao {
    private MyQueryRunner runner=new MyQueryRunner();
    private int flag;
    //高级搜索不分页
    public List<CustomerBean> queryByCondition(CustomerBean customerBean){
        String cname=customerBean.getCname();
        String gender=customerBean.getGender();
        String cellphone=customerBean.getCellphone();
        String email=customerBean.getEmail();
        //用于存放判断的条件
        List<String> params=new ArrayList<>();
        StringBuffer sql=new StringBuffer("select * from t_customer where 1=1");
        if(!IDUtil.isEmpty(cname)){
            sql.append(" and cname like ?");
            params.add("%"+cname+"%");
        }
        if(!IDUtil.isEmpty(gender)){
            sql.append(" and gender = ?");
            params.add(gender);
        }
        if(!IDUtil.isEmpty(cellphone)){
            sql.append(" and cellphone like ?");
            params.add("%"+cellphone+"%");

        }
        if(!IDUtil.isEmpty(email)){
            sql.append(" and email like ?");
            params.add("%"+email+"%");

        }
        List<CustomerBean> customerBeanList=new ArrayList<>();
        try {
            customerBeanList=runner.query(sql.toString(),new BeanListHandler<>(CustomerBean.class),params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerBeanList;

    }
    //分页查询

    /**
     *
     * @param pageCode   代表要查哪一页
     * @param pageSize   代表每一页显示多少条数据
     * @return
     */
    public PageBean<CustomerBean> findAll(int pageCode,int pageSize){
        PageBean<CustomerBean> pageBean =new PageBean<>();
        pageBean.setPc(pageCode);
        pageBean.setPs(pageSize);
        //首先获取所有的数据数量
        String sql="select count(*) from t_customer";
        try {
            //查询出所有的数据的数量,注意返回值为Long类型
            Long sum=runner.query(sql,new ScalarHandler<>());
            //将总数量添加到PageBean中,sum要转成int
            pageBean.setTr((sum.intValue()));
            sql="select * from t_customer limit ?,?";
            int from=(pageCode-1)*pageSize;
            int num=pageSize;
            //此数组的作用用于存放参数
            Object[] params={from,num};
            List<CustomerBean> customerBeans=
                    runner.query(sql,new BeanListHandler<CustomerBean>(CustomerBean.class),params);
            pageBean.setBeans(customerBeans);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageBean;

    }
    //分页高级查询
    public PageBean<CustomerBean> queryByCondition(CustomerBean customerBean,int pageCode,int pageSize){
        PageBean<CustomerBean> pageBean =new PageBean<>();
        pageBean.setPc(pageCode);
        pageBean.setPs(pageSize);
        String cname=customerBean.getCname();
        String gender=customerBean.getGender();
        String cellphone=customerBean.getCellphone();
        String email=customerBean.getEmail();
        //首先获取所有的数据数量
        StringBuffer sql=new StringBuffer("select count(*) from t_customer where 1=1 ");
        StringBuffer sql2=new StringBuffer("select * from t_customer where 1=1 ");
        List<Object> params2=new ArrayList<>();

        if(!IDUtil.isEmpty(cname)){
            sql.append(" and cname like ?");
            sql2.append(" and cname like ?");
            params2.add("%"+cname+"%");
        }
        if(!IDUtil.isEmpty(gender)){
            sql.append(" and gender = ?");
            sql2.append(" and gender = ?");
            params2.add(gender);
        }
        if(!IDUtil.isEmpty(cellphone)){
            sql.append(" and cellphone like ?");
            sql2.append(" and cellphone like ?");
            params2.add("%"+cellphone+"%");
        }
        if(!IDUtil.isEmpty(email)){
            sql.append(" and email like ?");
            sql2.append(" and email like ?");
            params2.add("%"+email+"%");
        }
        String from= String.valueOf((pageCode-1)*pageSize);
        String num= String.valueOf(pageSize);
        sql2.append(" limit ");
        sql2.append(from);
        sql2.append(" , ");
        sql2.append(num);

        Long sum= null;
        try {
            sum = runner.query(String.valueOf(sql),new ScalarHandler<>(),params2.toArray());
            pageBean.setTr((sum.intValue()));

            List<CustomerBean> customerBeans=
                    runner.query(String.valueOf(sql2),new BeanListHandler<CustomerBean>(CustomerBean.class),params2.toArray());
            pageBean.setBeans(customerBeans);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageBean;

    }
    public boolean deleteCustomer(String cid){
        String sql="delete from t_customer where cid=?";
        try {
            flag=runner.update(sql,cid
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (flag >0){
                return true;
            }
            else {
                return false;
            }
        }

    }


    public boolean addCustomer(CustomerBean bean) {
        String sql="insert into t_customer values(?,?,?,?,?,?,?)";
        try {
            flag = runner.update(sql,bean.getCid(),
                    bean.getCname(),
                    bean.getGender(),
                    bean.getBirthday(),
                    bean.getCellphone(),
                    bean.getEmail(),
                    bean.getDescription());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (flag >0){
                return true;
            }
            else {
                return false;
            }
        }

    }
    public CustomerBean queryById(String cid){
        String sql="select * from t_customer where cid=?";
        CustomerBean customerBean=new CustomerBean();
        try {
            customerBean=runner.query(sql,new BeanHandler<CustomerBean>(CustomerBean.class),cid);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerBean;


    }
    public boolean updateCustomer(CustomerBean bean){
        String sql = "update t_customer set cname = ?," +
                "gender=?,birthday = ?,cellphone = ?,email = ?,description = ? where cid = ?";
        try {
            flag=runner.update(sql,
                    bean.getCname(),
                    bean.getGender(),
                    bean.getBirthday(),
                    bean.getCellphone(),
                    bean.getEmail(),
                    bean.getDescription(),
                    bean.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (flag >0){
                return true;
            }
            else {
                return false;
            }
        }

    }
    public List<CustomerBean> findAll(){
        List<CustomerBean> customerBeans=new ArrayList<>();
        String sql="select * from t_customer";
        try {
            customerBeans=runner.query(sql,new BeanListHandler<CustomerBean>(CustomerBean.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  customerBeans;
    }



    @Test
    public void addTest(){
        CustomerDao dao = new CustomerDao();
        for (int i = 1; i <= 300; i++) {
            CustomerBean c = new CustomerBean();
            c.setCid(IDUtil.randomId());
            c.setCname("cust_"+i);
            c.setBirthday("1975-08-21");
            c.setCellphone("1008611"+i);
            c.setGender(i % 2 == 0? "男":"女");
            c.setEmail("cust_"+i+"@lanou.com");
            c.setDescription("cust_"+i+"的描述信息");
            dao.addCustomer(c);

        }
    }
}
