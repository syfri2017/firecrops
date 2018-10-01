package com.syfri.digitalplan.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


//九小场所配置
@Configuration
@ConfigurationProperties(prefix = JxcsfjxzProperties.YAFJXZ_PREFIX)
public class JxcsfjxzProperties {

    public static final String  YAFJXZ_PREFIX = "jxcsfjxz";

    private String savePath = "E://test//upload//";

    private String zipPath="E://test//upload//";

    private String fileServerUrl= "http://10.119.119.145:80";

    private String vueServerUrl= "http://10.119.119.145:80";

    public String getVueServerUrl() {
        return vueServerUrl;
    }

    public void setVueServerUrl(String vueServerUrl) {
        this.vueServerUrl = vueServerUrl;
    }

    public static String getYafjxzPrefix() {
        return YAFJXZ_PREFIX;
    }

    public String getFileServerUrl() {
        return fileServerUrl;
    }

    public void setFileServerUrl(String fileServerUrl) {
        this.fileServerUrl = fileServerUrl;
    }

    public String getZipPath() {
        return zipPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
