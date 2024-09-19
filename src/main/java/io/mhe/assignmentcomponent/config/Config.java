package io.mhe.assignmentcomponent.config;

import io.mhe.assignmentcomponent.dao.AssignmentCopyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import com.mhe.common.configuration.SQLBackedConfigurationProvider;
import com.mhe.common.text.ITemplateService;
import com.mhe.common.text.impl.FreemarkerTemplateService;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import software.amazon.awssdk.http.SdkHttpClient;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;


import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

@Configuration
public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    @Value("${DB_PASSWORD}")
    String databasePasswordKey;
    @Value("${DB_URL}")
    String url;
    @Value("${DB_USER}")
    String user;


    /*private String getValues(String key){
        SdkHttpClient httpClient =
                ApacheHttpClient.builder()
                        .socketTimeout(Duration.ofSeconds(10))
                        .build();
        SsmClient client = SsmClient.builder().region(Region.US_EAST_1).httpClient(httpClient).build();
        SSMProvider ssmProvider = ParamManager.getSsmProvider(client);
        String value = ssmProvider.withDecryption().get(key);
        return value;
    }*/

    private String getSSMValues(String paraName){
        SsmClient ssmClient = SsmClient.builder()
                .region(Region.US_EAST_1)
                .build();

        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(paraName)
                    .withDecryption(true)
                    .build();

            GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
            logger.info("The parameter value is 1234{}",parameterResponse.parameter().value());
            return parameterResponse.parameter().value();

        } catch (SsmException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }



    @Bean
    public DataSource getDataSource() throws SQLException {
        logger.info("url:{}:{}",url,getSSMValues(databasePasswordKey));
        String password = getSSMValues(databasePasswordKey);
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



