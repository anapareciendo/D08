package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ValueRepository;
import domain.Value;

@Component
@Transactional
public class StringToValueConverter implements Converter<String, Value>{

	@Autowired
	ValueRepository valueRepository;

	@Override
	public Value convert(String text) {
		Value result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = valueRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
