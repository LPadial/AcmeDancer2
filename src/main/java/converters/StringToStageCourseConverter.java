package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.StageCourse;

@Component
@Transactional
public class StringToStageCourseConverter implements Converter<String, StageCourse> {

	@Override
	public StageCourse convert(String text) {
		StageCourse result;

		try {
			result = new StageCourse();
			result.setCourseValue(text);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
