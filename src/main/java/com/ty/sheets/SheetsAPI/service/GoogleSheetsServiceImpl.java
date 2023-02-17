package com.ty.sheets.SheetsAPI.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.catalina.valves.StuckThreadDetectionValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.ty.sheets.SheetsAPI.config.GoogleAuthorizationConfig;
import com.ty.sheets.SheetsAPI.entity.Student;

@Service
public class GoogleSheetsServiceImpl implements GoogleSheetsService {

//    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetsServiceImpl.class);

	@Value("${spreadsheet.id}")
	private String spreadsheetId;

	@Autowired
	private GoogleAuthorizationConfig googleAuthorizationConfig;

	@Override
	public List<Student> getSpreadsheetValues() throws IOException, GeneralSecurityException {
		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
		Sheets.Spreadsheets.Values.BatchGet request = sheetsService.spreadsheets().values().batchGet(spreadsheetId);
		request.setRanges(getSpreadSheetRange());
		request.setMajorDimension("ROWS");
		BatchGetValuesResponse response = request.execute();

		List<List<Object>> spreadSheetValues = response.getValueRanges().get(0).getValues();
		List<Student> student = new ArrayList<Student>();

		for (List<Object> row : spreadSheetValues) {
			int i = 0;
			Student s = new Student();
			s.setName(row.get(i++) + "");
			s.setAge(row.get(i++) + "");
			s.setPhone(row.get(i++) + "");
			s.setBloodGroup(row.get(i) + "");
			student.add(s);
		}
		return student;
	}

	private List<String> getSpreadSheetRange() throws IOException, GeneralSecurityException {
		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
		Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
		Spreadsheet spreadsheet = request.execute();
		Sheet sheet = spreadsheet.getSheets().get(0);
		int row = sheet.getProperties().getGridProperties().getRowCount();
		int col = sheet.getProperties().getGridProperties().getColumnCount();
		return Collections.singletonList("R1C1:R".concat(String.valueOf(row)).concat("C").concat(String.valueOf(col)));
	}

//	public void write() throws IOException, GeneralSecurityException {
//		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
//		String range = "Sheet1!A2:B2";
//		String sID = spreadsheetId;
//		List<List<Object>> values = Arrays.asList(Arrays.asList("John", "Doe"), Arrays.asList("Jane", "Smith"));
//		ValueRange requestBody = new ValueRange();
//		requestBody.setValues(values);
//		UpdateValuesResponse response = sheetsService.spreadsheets().values().update(sID, range, requestBody)
//				.setValueInputOption("USER_ENTERED").execute();
//	}

	public void saveStudent(Student student) throws IOException, GeneralSecurityException {
		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
		String range = "";
		ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, "A:D").execute();
		ValueRange appendBody = new ValueRange().setValues(Arrays.asList(
				Arrays.asList(student.getName(), student.getAge(), student.getPhone(), student.getBloodGroup())));

		AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
				.append(spreadsheetId, "A:D", appendBody).setValueInputOption("USER_ENTERED")
				.setInsertDataOption("INSERT_ROWS").setIncludeValuesInResponse(true).execute();
	}

	public void updateStudent(Student student) throws IOException, GeneralSecurityException {
		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
		ValueRange requestBody = new ValueRange();
		requestBody.setValues(Arrays.asList(
				Arrays.asList(student.getName(), student.getAge(), student.getPhone(), student.getBloodGroup())));
		UpdateValuesResponse response = sheetsService.spreadsheets().values()
				.update(spreadsheetId, "A2:D2", requestBody).setValueInputOption("RAW").execute();
	}

//	public List<Object> getByName(String name) throws IOException, GeneralSecurityException {
//
////		name = "priya";
//
//		Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
//		String range = "A:Z";
//
//		ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
//
//		List<List<Object>> rows = response.getValues();
//
//		int rowIndex = -1;
//		for (int i = 0; i < rows.size(); i++) {
//			List<Object> row = rows.get(i);
//			if (row.size() > 0 && row.get(0).toString().equals(name)) {
//				rowIndex = i;
//				break;
//			}
//		}
//		if (rowIndex != -1) {
//			List<Object> rowData = rows.get(rowIndex);
//			String value1 = rowData.get(0).toString();
//			String value2 = rowData.get(1).toString();
//			System.out.println(rowData);
//			return rowData;
//
//		} else {
//			return null;
//		}
//	}
}
