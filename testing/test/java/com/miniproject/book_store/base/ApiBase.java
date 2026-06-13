package com.miniproject.book_store.base;

//import io.restassured.RestAssured;
//import io.restassured.config.RestAssuredConfig;
//import org.testng.annotations.BeforeSuite;
//import io.restassured.config.HttpClientConfig;
//import java.util.Properties;

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.Request;
import org.testng.annotations.BeforeSuite;
import java.util.concurrent.TimeUnit;

public class ApiBase {
//    protected static final String baseUrl = "http://localhost:8081";
//        @BeforeSuite
//        public void setupApi() {
//            // ✅ FULL CLEAN of system properties (this is the real fix)
//            Properties props = System.getProperties();
//            Properties cleanProps = new Properties();
//            for (Object key : props.keySet()) {
//                if (key != null) {
//                    Object value = props.get(key);
//                    if (value != null) {
//                        cleanProps.put(key, value);
//                    }
//                }
//            }
//            System.setProperties(cleanProps);
//            // ✅ Now safe to configure RestAssured
//            RestAssured.baseURI = baseUrl;
//            RestAssured.config = RestAssuredConfig.config()
//                    .httpClient(HttpClientConfig.httpClientConfig()
//                            .reuseHttpClientInstance());
//
//            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//            System.out.println("✅ API Base URL set to: " + baseUrl);
//        }


    // Your Spring Boot runs on port 8081
    protected static final String BASE_URL = "http://localhost:8081";

    // JSON media type — tells server "I am sending JSON"
    protected static final MediaType JSON = MediaType.get("application/json");

    // OkHttpClient — shared across all test classes
    protected static OkHttpClient client;

    @BeforeSuite
    public void setupApi() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)

                // ✅ Automatically adds Content-Type to every request
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request requestWithHeader = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .build();
                    return chain.proceed(requestWithHeader);
                })

                .build();

        System.out.println("✅ OkHttp client ready");
        System.out.println("✅ Base URL: " + BASE_URL);
    }


    }




