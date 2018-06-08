package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.StyleRecord;

@Component
@Transactional
public class StyleRecordToStringConverter implements Converter<StyleRecord, String> {

	@Override
	public String convert(StyleRecord ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}