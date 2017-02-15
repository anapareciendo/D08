package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.StateRepository;
import domain.State;

@Component
@Transactional
public class StringToStateConverter implements Converter<String, State>{

	@Autowired
	StateRepository stateRepository;

	@Override
	public State convert(String text) {
		State result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = stateRepository.findOne(id);
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
