package com.ty.sheets.SheetsAPI.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.ty.sheets.SheetsAPI.entity.Student;

public interface GoogleSheetsService {
	List<Student> getSpreadsheetValues() throws IOException, GeneralSecurityException;
}
