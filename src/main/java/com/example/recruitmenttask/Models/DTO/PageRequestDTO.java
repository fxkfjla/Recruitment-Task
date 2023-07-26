package com.example.recruitmenttask.Models.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

public class PageRequestDTO
{
	// Getters, setters
	public int getPage() { return page; }
	public int getSize() { return size; }
	public String getDirection() { return direction; }
	public String getField() { return field; }
	public void setPage(int page) { this.page = page; }
	public void setSize(int size) { this.size = size; }
	public void setDirection(String direction) { this.direction = direction; }
	public void setField(String field) { this.field = field; }
	
	@NotNull
	@Min(0)
	private int page;
	
	@NotNull
	@Min(1)
	private int size;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "ASC|DESC", flags = Flag.CASE_INSENSITIVE)
	private String direction;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "id|name|surname|login", flags = Flag.CASE_INSENSITIVE)
	private String field;
}
