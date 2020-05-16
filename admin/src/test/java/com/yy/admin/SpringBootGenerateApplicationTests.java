package com.yy.admin;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {AdminApplication.class})
@RunWith(SpringRunner.class)
public class SpringBootGenerateApplicationTests {

    @Autowired
    private DataSourceProperties dsp;

    @Test
    public void testGenerateCode() {
        //驱动类全名
        String driverName = dsp.getDriverClassName();
        //数据库URL
        String url = dsp.getUrl();
        //数据库账号
        String userName = dsp.getUsername();
        //数据库密码
        String password = dsp.getPassword();
        //作者
        String authorName = "liguanfeng";
        //项目路径
        String projectPath = System.getProperty("user.dir");
        //要生成的表名
        String[] tables = {"role"};
        //table前缀
        String tablePrefix = "";
        String basePackage = "com.yy.admin";
        AutoGenerator generator = new AutoGenerator();
        generator.setDataSource(new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setDriverName(driverName)
                .setUrl(url)
                .setUsername(userName)
                .setPassword(password));
        generator.setGlobalConfig(new GlobalConfig()
                .setOutputDir(projectPath + "/src/main/java")
                .setFileOverride(false)
                .setSwagger2(true)
                .setKotlin(false)
                .setActiveRecord(true)
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)

                .setOpen(false)
                .setAuthor(authorName)
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setIdType(IdType.ID_WORKER)

        );
        generator.setStrategy(new StrategyConfig()
                .setTablePrefix(tablePrefix)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.no_change)
                .setInclude(tables)
                .setRestControllerStyle(true)
                .setEntityLombokModel(true)
        );
        generator.setPackageInfo(new PackageConfig()
                .setParent(basePackage)
                .setController("controller")
                .setEntity("entity")
                .setService("service")
                .setServiceImpl("service")
                .setMapper("dao")
                .setXml("mapper")
        );
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.setTemplate(
                new TemplateConfig()
                        .setService(null)
                        .setServiceImpl(null)
                        .setController(null)
//                        .setMapper(null)
//                        .setXml(null)
        );
        generator.execute();

    }

}
