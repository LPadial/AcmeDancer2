package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.RoleApplication;

@Component
@Transactional
public class RoleApplicationToStringConverter implements Converter<RoleApplication, String> {

	@Override
	public String convert(RoleApplication roleApplication) {
		String result;

		if (roleApplication == null)
			result = null;
		else
			result = roleApplication.getRoleValue();

		return result;
	}

}
