package org.tool.server.io;

/**
 * Interface describing basic configuration-holding entity.
 *
 * @since 1.0.0
 */
public interface IConfigurationHolder {
	
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
    
    default String getConfigValue(String configFileName, String key){
    	return null;
    }
	
	default String getConfigValue(String configFileName, String key, String defaultValue){
		return null;
	}
    
}
