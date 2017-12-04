package org.sekill.quratz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SpringQtz {
    private static int counter = 0;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void executeMethod()  {
        System.out.println("simple counter: (" + counter++ + ")");
    }
}
