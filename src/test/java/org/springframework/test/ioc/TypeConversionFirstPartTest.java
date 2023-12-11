package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.convert.support.StringToNumberConverterFactory;
import org.springframework.test.ioc.common.StringToBooleanConverter;
import org.springframework.test.ioc.common.StringToIntegerConverter;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeConversionFirstPartTest {

    @Test
    public void testStringToIntegerConverter() throws Exception {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer num = converter.convert("778");
        assertThat(num).isEqualTo(778);
    }

    @Test
    public void testStringToNumberConverterFactory() throws Exception {
        StringToNumberConverterFactory converterFactory = new StringToNumberConverterFactory();
        Converter<String, Integer> stringToIntegerConverter = converterFactory.getConverter(Integer.class);
        Integer intNum = stringToIntegerConverter.convert("8888");
        assertThat(intNum).isEqualTo(8888);

        Converter<String, Long> stringToLongConverter = converterFactory.getConverter(Long.class);
        Long longNum = stringToLongConverter.convert("8888");
        assertThat(longNum).isEqualTo(8888L);
    }

    @Test
    public void testGenericConverter() throws Exception {
        StringToBooleanConverter converter = new StringToBooleanConverter();

        Boolean flag = (Boolean) converter.convert("true", String.class, Boolean.class);
        assertThat(flag).isTrue();
    }

    @Test
    public void testGenericConversionService() throws Exception {
        GenericConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());

        Integer intNum = conversionService.convert("8888", Integer.class);
        assertThat(conversionService.canConvert(String.class, Integer.class)).isTrue();
        assertThat(intNum).isEqualTo(8888);

        conversionService.addConverterFactory(new StringToNumberConverterFactory());
        assertThat(conversionService.canConvert(String.class, Long.class)).isTrue();
        Long longNum = conversionService.convert("8888", Long.class);
        assertThat(longNum).isEqualTo(8888L);

        assertThat(conversionService.canConvert(String.class, Integer.class)).isTrue();
        Integer intNum1 = conversionService.convert("7777", Integer.class);
        assertThat(intNum1).isEqualTo(7777);

        conversionService.addConverter(new StringToBooleanConverter());
        assertThat(conversionService.canConvert(String.class, Boolean.class)).isTrue();
        Boolean booleanValue = conversionService.convert("false", Boolean.class);
        assertThat(booleanValue).isFalse();
    }
}
