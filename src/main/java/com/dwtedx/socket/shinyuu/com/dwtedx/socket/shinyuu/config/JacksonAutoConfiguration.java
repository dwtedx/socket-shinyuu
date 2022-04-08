package com.dwtedx.socket.shinyuu.com.dwtedx.socket.shinyuu.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

@Configuration
@ConditionalOnClass({Jackson2ObjectMapperBuilder.class})
public class JacksonAutoConfiguration {

	@Bean
	@Primary
	//@ConditionalOnMissingBean(ObjectMapper.class)
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder)
	{
		ObjectMapper objectmapper = builder.createXmlMapper(false).build();

		// 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
		// include.include.always 默认
		// include.non_default 属性为默认值不序列化
		// include.non_empty 属性为 空（""） 或者为 null 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
		// include.non_null 属性为null 不序列化
		objectmapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		objectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 允许出现特殊字符和转义符
		objectmapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		// 允许出现单引号
		objectmapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// 字段保留，将null值转为""
		objectmapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
		{
			@Override
			public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
				jsonGenerator.writeString("");
			}

		});
		return objectmapper;
	}


}
