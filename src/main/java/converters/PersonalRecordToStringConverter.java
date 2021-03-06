package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PersonalRecord;

@Component
@Transactional
public class PersonalRecordToStringConverter implements Converter<PersonalRecord, String> {

	@Override
	public String convert(PersonalRecord ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
