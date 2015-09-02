package com.fanxing.server.io;

/**
 * Interface describing basic configuration-holding entity.
 *
 * @since 1.0.0
 */
public interface ConfigurationHolder {
	
    /**
     * Returns string configuration value.
     *
     * @param key
     * @return required value or null
     */
    String getConfigurationValue(String key);
    /**
     * Returns string configuration value or default value if asked value is not present.
     *
     * @param key
     * @return required value or defaultValue
     */
    String getConfigurationValue(String key, String defaultValue);
    
}
