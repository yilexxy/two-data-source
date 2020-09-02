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
import java.util.Properties;

/**
 * @author feng
 */

@Configuration
@MapperScan(basePackages = {"io.ilss.transaction.twodatasource.dao.account"}, sqlSessionTemplateRef = "accountSqlSessionTemplate")
public class AccountConfiguration {

    @Value("${spring.datasource.account.name}")
    private String name;
    @Value("${spring.datasource.account.url}")
    private String url;
    @Value("${spring.datasource.account.username}")
    private String username;
    @Value("${spring.datasource.account.password}")
    private String password;
    @Value("${spring.datasource.account.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.account.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.account.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.account.maxWait}")
    private long maxWait;
    @Value("${spring.datasource.account.testWhileIdle}")
    private boolean testWhileIdle;


    @Bean(name = "accountDataSource")
    public DataSource accountDataSource() {
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
        atomikosDataSourceBean.setUniqueResourceName("accountResource");
        return atomikosDataSourceBean;
    }

    @Bean(name = "accountSqlSessionFactory")
    public SqlSessionFactory accountSqlSessionFactory(DataSource accountDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(accountDataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/account/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "accountSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate accountSqlSessionTemplate(@Qualifier("accountSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
