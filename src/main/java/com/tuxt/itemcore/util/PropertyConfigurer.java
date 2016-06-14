package com.tuxt.itemcore.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author: shilei6@asiainfo.com
 * @version: 2011-9-16
 */
public class PropertyConfigurer extends
		org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);
		try {
			String password = props.getProperty("password");
			String decryPassword = new String(EncryptionUtil.decode(EncryptionUtil.hex2byte(password), Constants.DBKEYEES.getBytes()));
			props.setProperty("password", decryPassword);
			String o_password = props.getProperty("o_password");
			String decryoPassword = new String(EncryptionUtil.decode(EncryptionUtil.hex2byte(o_password), Constants.DBKEYEES.getBytes()));
			props.setProperty("o_password", decryoPassword);
		} catch (Exception e) {
			logger.error("decode password in properties error!", e);
		}
	}
}