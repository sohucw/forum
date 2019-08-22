package com.cjw.forum.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.BucketAuthorization;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileBucketLocalAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.cjw.forum.exception.CustomErrorCode;
import com.cjw.forum.exception.CustomException;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 15:51 2019-08-22
 */
@Service
public class UcloudProvider {

    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;
    // private int expireDate = 24 * 60 * 60;

    @Value("${ucloud.ufile.region}")
    private String region;

    @Value("${ucloud.ufile.suffix}")
    private String suffix;

    @Value("${ucloud.ufile.expire-date}")
    private Integer expireDate;



    public String upload(InputStream inputStream, String mimeType, String fileName) {
        String generatedFileName = "";
        String[] filePaths = fileName.split("\\.");
        if(filePaths.length>1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length-1];
        } else {
            throw new CustomException(CustomErrorCode.FILE_UPLOAD_ERROR);
        }
        try {
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
            ObjectConfig config = new ObjectConfig(region, suffix);
            PutObjectResultBean resultBean = UfileClient.object(objectAuthorization, config)
                    .putObject(inputStream, mimeType)
                    .nameAs(generatedFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long l, long l1) {

                        }
                    }).execute();
            if(resultBean !=null && resultBean.getRetCode() == 0) {
                String url = UfileClient.object(objectAuthorization, config)
                        .getDownloadUrlFromPrivateBucket(generatedFileName, bucketName , expireDate)
                        .createUrl();
                return url;

            } else {
                throw new CustomException(CustomErrorCode.FILE_UPLOAD_ERROR);
            }

        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomException(CustomErrorCode.FILE_UPLOAD_ERROR);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomException(CustomErrorCode.FILE_UPLOAD_ERROR);
        }
        // return generatedFileName;
    }
}
