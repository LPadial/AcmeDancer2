package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CustomRecord;

@Component
@Transactional
public class CustomRecordToStringConverter implements Converter<CustomRecord, String> {

	@Override
	public String convert(CustomRecord ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}