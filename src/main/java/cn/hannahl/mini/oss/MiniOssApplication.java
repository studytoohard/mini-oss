package cn.hannahl.mini.oss;

import cn.hannahl.mini.oss.config.OssConfig;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Configuration
@ComponentScan
public class MiniOssApplication {

    public static void main(String[] args) throws XmlPullParserException, NoSuchAlgorithmException,
            InvalidKeyException, IOException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MiniOssApplication.class);
        OssConfig ossConfig = applicationContext.getBean(OssConfig.class);
        File file = new File(MiniOssApplication.class.getClassLoader().getResource("ctw1.png").getFile());

        MiniOssApplication.upload(ossConfig, file);
    }

    public static void upload(OssConfig ossConfig, File file) throws XmlPullParserException,
            NoSuchAlgorithmException,
            InvalidKeyException, IOException {
        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient(ossConfig.endpoint, ossConfig.accessKey, ossConfig.secretKey);

            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(ossConfig.bucketName);
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket(ossConfig.bucketName);
            }

            // 使用putObject上传一个文件到存储桶中。
            String fileName = file.getName();
            String randomFileName =
                    UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
            minioClient.putObject(ossConfig.bucketName, randomFileName, file.getAbsolutePath());
            System.out.println(file.getAbsolutePath() + " is successfully uploaded as " + randomFileName + "to"
                    + ossConfig.bucketName + " bucket.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }

}
