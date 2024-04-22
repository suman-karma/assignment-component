package io.mhe.assignmentcomponent.mheevent.config;

import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants;
import io.mhe.assignmentcomponent.mheevent.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class MheEventConfiguration {
private Logger logger = LoggerFactory.getLogger(MheEventConfiguration.class);

  @Bean(name = "mheEventExecutor") 
  public TaskExecutor getThreadPoolTaskExecutor() { 
		  ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor(); 
		  taskExecutor.setThreadNamePrefix("MheEvent - "); 
		  //Set for testing, will decide the actual values after analysis
		  taskExecutor.setAwaitTerminationSeconds(10); 
		  String poolSize = Util.getConfigValue(MheEventConstants.MHE_EVENT_MAX_POOL_SIZE);
			if (logger.isDebugEnabled()) {
				logger.debug("MAX POOL SIZE " + poolSize);			
			}
			taskExecutor.setMaxPoolSize(Integer.parseInt(poolSize));
		  return taskExecutor; 
  }

}