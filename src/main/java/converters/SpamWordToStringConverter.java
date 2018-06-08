package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SpamWord;

@Component
@Transactional
public class SpamWordToStringConverter implements Converter<SpamWord,String>{
	
	public String convert(SpamWord ar){
		String res;
		if(ar == null){
			res = null;
		}else{
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
