/* StringToCardConverter.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministratorRepository;
import repositories.AuditorRepository;
import repositories.LessorRepository;
import repositories.TenantRepository;
import domain.Actor;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor> {

	@Autowired
	LessorRepository lessorRepository;
	@Autowired
	TenantRepository tenantRepository;
	@Autowired
	AdministratorRepository adminRepository;
	@Autowired
	AuditorRepository auditorRepository;
	


	@Override
	public Actor convert(String text) {
		Actor result;
		int id;

		try {
			id = Integer.valueOf(text);
			
			result = lessorRepository.findOne(id);
			
			if(result == null){
				result = tenantRepository.findOne(id);
			}
			if(result == null){
				result = adminRepository.findOne(id);
			}
			if(result == null){
				result = auditorRepository.findOne(id);
			}
			
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
