package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.RoleApplication;

@Component
@Transactional
public class StringToRoleApplicationConverter implements Converter<String, RoleApplication> {

	@Override
	public RoleApplication convert(String text) {
		RoleApplication result;

		try {
			result = new RoleApplication();
			result.setRoleValue(text);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
