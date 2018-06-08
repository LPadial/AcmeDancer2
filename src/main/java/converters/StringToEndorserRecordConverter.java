package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Component
@Transactional
public class StringToEndorserRecordConverter implements Converter<String, EndorserRecord>{
	
	@Autowired
	EndorserRecordRepository arRepository;
	
	@Override
	public EndorserRecord convert(String text){
		EndorserRecord result;
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
