package com.simone.service;

import com.simone.bean.CustomerBean;
import com.simone.bean.PageBean;
import com.simone.dao.CustomerDao;
import com.simone.util.IDUtil;

import java.util.List;

/**
 *用于处理业务逻辑
 */
public class CustomerService {
    private CustomerDao customerDao=new CustomerDao();
    public boolean addCustomer(CustomerBean bean){
        //先添加ID
        bean.setCid(IDUtil.randomId());
        return  customerDao.addCustomer(bean);
    }
    public List<CustomerBean> findAll(){
        List<CustomerBean> customerBeans=customerDao.findAll();
        return customerBeans;
    }
    public boolean deleteCustomer(String cid){

        return customerDao.deleteCustomer(cid);
    }
    public  List<CustomerBean> queryByCondition(CustomerBean customerBean){
        return customerDao.queryByCondition(customerBean);

    }
    public  PageBean<CustomerBean> queryByCondition(CustomerBean customerBean,int pageCode,int pageSize){
        return customerDao.queryByCondition(customerBean,pageCode,pageSize);

    }

    public CustomerBean queryById(String cid){
        return customerDao.queryById(cid);
    }
    public boolean updateCustomer(CustomerBean bean){
        return customerDao.updateCustomer(bean);
    }
    public PageBean findAll(int pc,int ps){
        return customerDao.findAll(pc,ps);
    }
}