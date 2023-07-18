package com.example.recruitmenttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.recruitmenttask.Utils.XMLUserGenerator;

@SpringBootApplication
public class RecruitmenttaskApplication
{
	public static void main(String[] args)
	{
		XMLUserGenerator.generateUsersToXML(10);
		
		SpringApplication.run(RecruitmenttaskApplication.class, args);
	}
}