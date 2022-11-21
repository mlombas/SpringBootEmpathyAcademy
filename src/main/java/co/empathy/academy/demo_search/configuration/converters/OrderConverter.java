package co.empathy.academy.demo_search.configuration.converters;

import co.empathy.academy.demo_search.ports.order.POrderBuilder;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.Set;

public class OrderConverter implements ConditionalGenericConverter {

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return targetType.getType().equals(POrderBuilder.Order.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class , POrderBuilder.Order.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return POrderBuilder.Order.valueOf(((String) source).toUpperCase());
    }
}

