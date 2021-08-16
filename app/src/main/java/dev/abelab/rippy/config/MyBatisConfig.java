package dev.abelab.rippy.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("dev.abelab.rippy.db.mapper")
@Configuration
public class MyBatisConfig {
}
