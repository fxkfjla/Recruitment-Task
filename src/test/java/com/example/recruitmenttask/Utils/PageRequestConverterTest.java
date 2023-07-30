package com.example.recruitmenttask.Utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.recruitmenttask.Models.DTO.PageRequestDTO;

public class PageRequestConverterTest
{
	@Test
	public void Converts_PageRequestDTO_to_PageRequest()
	{
		// Given
		int page = 1;
		int size = 13;
		String direction = "asc";
		String field = "id";
		
		PageRequestDTO pageRequestDTO = new PageRequestDTO(page, size, direction, field);
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), field));

		// When
		PageRequest converted = converter.convert(pageRequestDTO);
		
		// Then
		assertThat(converted).isEqualTo(pageRequest);
	}
	
	private PageRequestDTOToPageRequestConverter converter = new PageRequestDTOToPageRequestConverter();
}
