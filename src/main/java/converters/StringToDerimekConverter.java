
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Derimek;
import repositories.DerimekRepository;

@Component
@Transactional
public class StringToDerimekConverter implements Converter<String, Derimek> {

	@Autowired
	DerimekRepository arRepository;


	@Override
	public Derimek convert(String text) {
		Derimek result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = arRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
