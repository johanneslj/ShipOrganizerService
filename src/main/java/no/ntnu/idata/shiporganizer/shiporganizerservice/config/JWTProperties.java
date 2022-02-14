package no.ntnu.idata.shiporganizer.shiporganizerservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt.properties")
@Configuration
public class JWTProperties {

  private String secretCode;
  private int expirationTime;
  private String tokenPrefix;
  private String headerString;

  public String getSecretCode() {
    return secretCode;
  }

  public void setSecretCode(String secretCode) {
    this.secretCode = secretCode;
  }

  /**
   * Gets the JWT token's expiration time in milliseconds.
   *
   * @return Expiration time in milliseconds.
   */
  public int getExpirationTime() {
    return expirationTime;
  }

  /**
   * Set expiration time for JWT token in milliseconds.
   *
   * @param expirationTime Expiration time in milliseconds.
   */
  public void setExpirationTime(int expirationTime) {
    this.expirationTime = expirationTime;
  }

  /**
   * Gets the prefix sent with the token value string.
   *
   * @return Token prefix string.
   */
  public String getTokenPrefix() {
    return tokenPrefix;
  }

  /**
   * Sets the prefix string sent with the token.
   *
   * @param tokenPrefix Prefix string.
   */
  public void setTokenPrefix(String tokenPrefix) {
    this.tokenPrefix = tokenPrefix;
  }

  /**
   * Gets the string for the header key that the token is passed in.
   *
   * @return Header key string.
   */
  public String getHeaderString() {
    return headerString;
  }

  /**
   * Sets the key string for the header the token is passed in.
   *
   * @param headerString Header key string.
   */
  public void setHeaderString(String headerString) {
    this.headerString = headerString;
  }
}
