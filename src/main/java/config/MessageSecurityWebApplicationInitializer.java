package config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by Admin on 13.07.2014.
 */
@Order(2)
public class MessageSecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}

