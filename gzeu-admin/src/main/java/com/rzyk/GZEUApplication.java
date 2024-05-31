package com.rzyk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 * 
 * @author ruoyi
 */

@MapperScan({"com.rzyk.*.*mapper","com.rzyk.mapper.*Mapper","com.rzyk.system.*.*.*Mapper"})
//新建模块需要添加包扫描
@ComponentScan({"com.rzyk.*" })
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class GZEUApplication
{
    public static void main(String[] args)
    {

        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(GZEUApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  融智云考启动成功   ლ(´ڡ`ლ)ﾞ \n" +
                "\n" +
                " .----------------.  .----------------.  .----------------.  .----------------. \n" +
                "| .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                "| |  _______     | || |   ________   | || |  ____  ____  | || |  ___  ____   | |\n" +
                "| | |_   __ \\    | || |  |  __   _|  | || | |_  _||_  _| | || | |_  ||_  _|  | |\n" +
                "| |   | |__) |   | || |  |_/  / /    | || |   \\ \\  / /   | || |   | |_/ /    | |\n" +
                "| |   |  __ /    | || |     .'.' _   | || |    \\ \\/ /    | || |   |  __'.    | |\n" +
                "| |  _| |  \\ \\_  | || |   _/ /__/ |  | || |    _|  |_    | || |  _| |  \\ \\_  | |\n" +
                "| | |____| |___| | || |  |________|  | || |   |______|   | || | |____||____| | |\n" +
                "| |              | || |              | || |              | || |              | |\n" +
                "| '--------------' || '--------------' || '--------------' || '--------------' |\n"
        );


    }
}
