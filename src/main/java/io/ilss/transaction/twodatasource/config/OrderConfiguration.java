package io.ilss.transaction.twodatasource.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author feng
 */

@Configuration
@MapperScan(basePackages = {"io.ilss.transaction.twodatasource.dao.order"}, sqlSessionTemplateRef = "orderSqlSessionTemplate")
public class OrderConfiguration {

    @Value("${spring.datasource.order.name}")
    private String name;
    @Value("${spring.datasource.order.url}")
    private String url;
    @Value("${spring.datasource.order.username}")
    private String username;
    @Value("${spring.datasource.order.password}")
    private String password;
    @Value("${spring.datasource.order.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.order.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.order.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.order.maxWait}")
    private long maxWait;
    @Value("${spring.datasource.order.testWhileIdle}")
    private boolean testWhileIdle;


    @Bean(name = "orderDataSource")
    public DataSource orderDataSource() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setName(name);
        druidXADataSource.setUrl(url);
        druidXADataSource.setUsername(username);
        druidXADataSource.setPassword(password);
        druidXADataSource.setInitialSize(initialSize);
        druidXADataSource.setMaxActive(maxActive);
        druidXADataSource.setMinIdle(minIdle);
        druidXADataSource.setMaxWait(maxWait);
        druidXADataSource.setTestWhileIdle(testWhileIdle);

        atomikosDataSourceBean.setXaDataSource(druidXADataSource);
        atomikosDataSourceBean.setUniqueResourceName("orderResource");
        return atomikosDataSourceBean;
    }

    @Bean(name = "orderSqlSessionFactory")
    public SqlSessionFactory accountSqlSessionFactory(DataSource orderDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(orderDataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/order/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "orderSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate orderSqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
