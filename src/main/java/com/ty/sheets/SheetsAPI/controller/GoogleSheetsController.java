package com.ty.sheets.SheetsAPI.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ty.sheets.SheetsAPI.entity.Student;
import com.ty.sheets.SheetsAPI.service.GoogleSheetsService;
import com.ty.sheets.SheetsAPI.service.GoogleSheetsServiceImpl;

@Controller
@RequestMapping()
public class GoogleSheetsController {

	@Autowired
	private GoogleSheetsService googleSheetsService;

	@Autowired
	private GoogleSheetsServiceImpl googleSheetsServiceImpl;

	@GetMapping("/get")
	public String getSpreadsheetValues(Model model) throws IOException, GeneralSecurityException {
		List<Student> s = googleSheetsService.getSpreadsheetValues();
		s.remove(0);
		model.addAttribute("Students", s);
		return "display";
	}

	@GetMapping("/index")
	public String htmlpage() {
		return "index";
	}

//	@GetMapping("/write")
//	public void write() throws IOException, GeneralSecurityException {
//		googleSheetsServiceImpl.write();
//	}

//	@GetMapping("/add")
//	public void add() throws IOException, GeneralSecurityException {
//		Student student = new Student();
//		student.setName("Rahul");
//		student.setAge("21");
//		student.setBloodGroup("b+");
//		student.setPhone("465647");
//		googleSheetsServiceImpl.updateStudent(student);
//	}

	@PostMapping("/save")
	public String save(@ModelAttribute("student") Student student) throws IOException, GeneralSecurityException {
		System.out.println("student");
		googleSheetsServiceImpl.saveStudent(student);
		return "show";
	}

	@GetMapping("/form")
	public String submitForm(@ModelAttribute("student") Student student) {
		return "form";
	}

	@PostMapping("/show")
	public String show(@ModelAttribute("student") Student student) {
		return "show";
	}
	
//	@GetMapping("/name")
//	public String getByName(@RequestParam  String name,Model model) throws IOException, GeneralSecurityException {
////		model.addAttribute("student", googleSheetsServiceImpl.getByName(name));
//		
//		return "display";
//	}
}
