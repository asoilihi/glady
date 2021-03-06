package com.backend.as.glady.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author asoilihi
 *
 */
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/api/**")).build().apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
		return new ApiInfo("Glady API", "Glady API", "1.0.0", "Terms of Service",
				new Contact("SA", "https://github.com/lhabdou", "soilihi.abdoulhalim@outlook.fr"),
				"Apache License Version 2.0", " http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
	}

}
