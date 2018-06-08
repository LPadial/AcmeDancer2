package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.StageCourse;

@Component
@Transactional
public class StageCourseToStringConverter implements Converter<StageCourse, String> {

	@Override
	public String convert(StageCourse stageCourse) {
		String result;

		if (stageCourse == null)
			result = null;
		else
			result = stageCourse.getCourseValue();

		return result;
	}

}
