
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Derimek;

@Component
@Transactional
public class DerimekToStringConverter implements Converter<Derimek, String> {

	@Override
	public String convert(Derimek ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
