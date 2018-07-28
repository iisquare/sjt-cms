package com.iisquare.sjt.cms.web.test;

import org.junit.Test;

import java.util.UUID;

public class DataTester {

    @Test
    public void uuidTest() {
        System.out.println(UUID.randomUUID().toString().replaceAll("\\-", "").toLowerCase());
    }

}
