package com.meetups.todoservice.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import java.util.Map;

@ConfigurationProperties("user")
@Validated
public class UserServiceProperties {

    /**
     * Setting for user service
     */
    //@Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/{}%=~_|]")
    private String url;
    /**
     * formato de url test debe ser de tipo url
     */
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    private String urlTest;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlTest() {
        return urlTest;
    }

    public void setUrlTest(String urlTest) {
        this.urlTest = urlTest;
    }
}
