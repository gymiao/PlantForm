package com.example;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class Generator {
    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
    
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/reggie?serverTimezone=UTC");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123");
        autoGenerator.setDataSource(dataSourceConfig);
    
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/plantform/src/main/java");
        globalConfig.setOpen(false);
        globalConfig.setAuthor("miaogy");
        globalConfig.setFileOverride(true);
        globalConfig.setMapperName("%sDao");
        globalConfig.setIdType(IdType.ASSIGN_ID);
        autoGenerator.setGlobalConfig(globalConfig);
        // test
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.example");
        packageConfig.setEntity("domain");
        packageConfig.setMapper("dao");
        autoGenerator.setPackageInfo(packageConfig);
    
        StrategyConfig strategyConfig = new StrategyConfig();
        //strategyConfig.setInclude("tbl_user","tbl_admin");
        //strategyConfig.setTablePrefix("tbl_");
        strategyConfig.setRestControllerStyle(true);
        //strategyConfig.setVersionFieldName("version");
        //strategyConfig.setLogicDeleteFieldName("deleted");
        strategyConfig.setEntityLombokModel(true);
        autoGenerator.setStrategy(strategyConfig);
        
        autoGenerator.execute();
        
    }
}
