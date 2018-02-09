package com.dianshang.core.pojo;

/**
 * Created by 25131 on 2018/2/10.
 */
public class User {
    private  String name;
    private  String set;

    public User(String name, String set) {
        this.name = name;
        this.set = set;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }
}
