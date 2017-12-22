package com.simone.util;

import com.simone.bean.CustomerBean;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * Created by dllo on 17/12/19.
 */
public class BeanUtil {
    public static void requestToBean(HttpServletRequest request, CustomerBean bean){
        //利用反射获取bean类
        Class clazz=bean.getClass();
        //获取类中所有属性
        Field[] fields=clazz.getDeclaredFields();
        //对属性进行遍历
        for (Field field : fields) {
            String name=field.getName();
            String value=request.getParameter(name);

            //打破封装
            field.setAccessible(true);
            try {
                field.set(bean,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

}
