package io.mhe.assignmentcomponent.config;

import com.mhe.common.configuration.IConfigurationProvider;
import com.mhe.common.configuration.SQLBackedConfigurationProvider;
import com.mhe.common.text.ITemplateService;
import com.mhe.common.text.impl.FreemarkerTemplateService;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import com.mhe.connect.business.common.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
//@ComponentScan({"io.mhe.assignmentcomponent","com.mhe.common.text.ITemplateService"})
public class Config {

    @Bean
    public DataSource getDataSource() throws SQLException {
        String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = connect-sprkeqst-db.oci.mh.com)(PORT = 1521))(LOAD_BALANCE = yes)(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = sprkeqst.exa.dsiad.oraclevcn.com))(UR = A)))";
        String user = "cmsrep";
        String password = "P$sw0rd4cM3sp";
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setLoginTimeout(5);
        return dataSource;
    }

    @Bean
    @Qualifier("sql")
    ITemplateService getTemplateService() {return new FreemarkerTemplateService("/sql");}


    @Bean
    @Qualifier("configurationProperties")
    SQLBackedConfigurationProvider SQLBackedConfigurationProvider() throws SQLException { return new SQLBackedConfigurationProvider(getDataSource());}
    //Configuration Configuration(){ return new Configuration();}

    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() throws SQLException {
        NamedParameterJdbcTemplate temp =  new NamedParameterJdbcTemplate(getDataSource());
        return temp;
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate() throws SQLException
    {
        JdbcTemplate temp = new JdbcTemplate(getDataSource());
        temp.setFetchSize(100);
        return temp;
    }

}



