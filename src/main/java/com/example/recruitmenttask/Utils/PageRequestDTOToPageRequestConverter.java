package com.example.recruitmenttask.Utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.recruitmenttask.Models.DTO.PageRequestDTO;

@Component
public class PageRequestDTOToPageRequestConverter implements Converter<PageRequestDTO, PageRequest>
{
	@Override
	public PageRequest convert(PageRequestDTO source)
	{
		int page = source.getPage();
        int size = source.getSize();
        String direction = source.getDirection();
        String field = source.getField();
        Sort sort = Sort.by(Sort.Direction.fromString(direction), field);
        
        return PageRequest.of(page, size, sort);
	}
}