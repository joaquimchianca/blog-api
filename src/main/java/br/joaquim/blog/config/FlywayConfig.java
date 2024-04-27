package br.joaquim.blog.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Autowired
    public FlywayConfig(DataSource dataSource) {
        Flyway.configure()
                .baselineVersion("0.0")
                .dataSource(dataSource)
                .load().migrate();
    }
}
