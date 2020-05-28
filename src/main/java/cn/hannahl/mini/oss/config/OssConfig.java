package cn.hannahl.mini.oss.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class OssConfig {


    @Value("${endpoint}")
    public String endpoint;

    @Value("${accessKey}")
    public String accessKey;

    @Value("${secretKey}")
    public String secretKey;

    @Value("${bucketName}")
    public String bucketName;

}
