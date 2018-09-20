package com.lilei.router_api;

/**
 * Created by lilei
 * Date : 2018/9/21
 */

public class PrintHelloWorld {
    public static void print(){
        try {
            Class cla = Class.forName("com.lilei.gen.HelloWorld");
            IComponentRouter instance = (IComponentRouter) cla.newInstance();
            instance.test();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
