package com.ybk.intent.inject;

import java.io.Serializable;

/**
 * Created by ybk on 2017/9/5.
 */

public class SerialzableTest implements Serializable {
    private String name;

    public SerialzableTest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
