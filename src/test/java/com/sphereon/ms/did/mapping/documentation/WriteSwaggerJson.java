package com.sphereon.ms.did.mapping.documentation;

import com.sphereon.ms.did.mapping.config.TestConfigWriteSwagger;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfigWriteSwagger.class, properties = {"management.port=0", "spring.data.mongodb.port=0"})
//@ActiveProfiles("write-swagger")
public class WriteSwaggerJson {
    @Value("${local.server.port}")
    private String port;

    @Test
    public void createSwaggerJson() throws Exception {
        String jsonFile = "swagger.sdk.json";
        File swaggerDir = new File("src/main/resources/swagger");
        swaggerDir.mkdirs();

        File swaggerFile = new File(swaggerDir.getAbsolutePath() + File.separator + jsonFile);
        if (swaggerFile.exists()) {
            Assert.assertTrue(swaggerFile.delete());
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(getServiceUrl());
        CloseableHttpResponse response = client.execute(get);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        String rawJson = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(rawJson);

        swaggerFile.setWritable(true);
        Files.write(swaggerFile.toPath(), json.toString(2).getBytes(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        if (swaggerFile.length() <= 10) {
            Assert.fail("Swagger file too small");
        }
        response.close();
        client.close();
    }

    private String getServiceUrl() {
        return String.format("http://localhost:%s/v2/api-docs", port);
    }
}
