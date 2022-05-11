package no.ntnu.idata.shiporganizer.shiporganizerservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Class used to set up the spaces for image upload on Digital Ocean
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "do.spaces.access")
public class DoSpaceConfig {
  private String key;
  private String secret;
  private String endpoint;
  private String region;
  private String bucket;

  /**
   * Instantiates a new Do space config.
   */
  public DoSpaceConfig() {
  }

  /**
   * Gets the client connection using the credentials.
   * @return AmazonS3 Client connection with the space
   */
  @Bean
  public AmazonS3 getCredentials() {
    BasicAWSCredentials credentials = new BasicAWSCredentials(key, secret);
    return AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
        .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
  }

  /**
   * Gets Space Credential key.
   *
   * @return the Space Credential key
   */
  public String getKey() {
    return key;
  }

  /**
   * Sets new Space Credential key.
   *
   * @param key the key
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * Gets Space Credential secret.
   *
   * @return the  Space Credential secret
   */
  public String getSecret() {
    return secret;
  }

  /**
   * Sets new Space Credential secret.
   *
   * @param secret the  Space Credential secret
   */
  public void setSecret(String secret) {
    this.secret = secret;
  }

  /**
   * Gets  client connection endpoint.
   *
   * @return the  client connection endpoint
   */
  public String getEndpoint() {
    return endpoint;
  }

  /**
   * Sets endpoint.
   *
   * @param endpoint the new endpoint
   */
  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  /**
   * Gets the client connection region.
   *
   * @return the client connection  region
   */
  public String getRegion() {
    return region;
  }

  /**
   * Sets client connection  region.
   *
   * @param region the new region
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * Gets client connection bucket.
   *
   * @return the  client connection bucket
   */
  public String getBucket() {
    return bucket;
  }

  /**
   * Sets  client connection bucket.
   *
   * @param bucket the new bucket
   */
  public void setBucket(String bucket) {
    this.bucket = bucket;
  }
}
