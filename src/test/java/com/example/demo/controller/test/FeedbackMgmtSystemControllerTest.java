package com.example.demo.controller.test;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.feedback.controller.FeedbackMgmtSystemController;
import com.feedback.dto.EmailExcelDto;
import com.feedback.dto.EventDto;
import com.feedback.dto.FeedbackDto;
import com.feedback.dto.ReportDto;
import com.feedback.dto.ResponseDto;
import com.feedback.dto.UserDto;
import com.feedback.service.IFeedbackMgmtSystemService;



public class FeedbackMgmtSystemControllerTest {
	@Mock
	private IFeedbackMgmtSystemService mockService;

	@InjectMocks
	private FeedbackMgmtSystemController mockController;

	@Before
	public void setUp() {
		initMocks(this);
	}
	@Test
	public void getLoginDetailsTest() throws SQLException {
		UserDto user=new UserDto();
		user.setEmail("xyz@gmail.com");
		user.setPwd("xyz");
		user.setUser("XYZ");
		user.setUserRole("Manager");
		ResponseDto resp=new ResponseDto();
		resp.setMessage("Login Successful");
		String res="Login Successful";
		//when(mockService.getLoginDetails(user)).thenReturn(res);
		// Run the test
		 mockController.getLoginDetails(user);
		
	}
	
	@Test
	public void getReportDetailsTest() throws Exception {
		List<ReportDto> list=new ArrayList<>();
		ReportDto report=new ReportDto();
		report.setBaseLocation("CBE");
		report.setBeneficiaryName("XXXX");
		report.setCouncilName("QAQ");
		list.add(report);
		when(mockService.getReportDetails()).thenReturn(list);
		List<ReportDto> val=mockController.getReportDetails();
	}
	@Test
	public void getEventDetailsTest() throws Exception {
		List<EventDto> list=new ArrayList<>();
		EventDto event=new EventDto();
		event.setBaseLocation("CAD");
		event.setBusinessUnit("MLEU");
		event.setMonth("AUG");
		when(mockService.getEventDetails()).thenReturn(list);
		List<EventDto> val=mockController.getEventDetails();
	}
	
	@Test
	public void saveFeedbackTest() throws SQLException {
		FeedbackDto feedbackDto=new FeedbackDto();
		feedbackDto.setAnswer1("Answer");
		feedbackDto.setAnswer2("Answer2");
		feedbackDto.setAnswer3("Answer3");
		ResponseDto response=new ResponseDto();
		response.setMessage("Saved Successfully in DB");
		String message="Saved Successfully in DB";
		when(mockService.saveFeedback(feedbackDto)).thenReturn(message);
		ResponseDto msg=mockController.saveFeedback(feedbackDto);
	}
	
	@Test
	public void exportExcelTest() {
		ResponseDto response=new ResponseDto(); 
		response.setMessage("Exported Event Excel");
		String msg="Exported Event Excel";
		EmailExcelDto dto=new EmailExcelDto();
		dto.setEmailId("xyz@gmail.com");
		when(mockService.exportExcel(dto)).thenReturn(msg);
		ResponseDto resp=mockController.exportExcel(dto);
		
	}
	
	@Test
	public void exportReportTest() {
		ResponseDto response=new ResponseDto(); 
		response.setMessage("Exported Report Excel");
		String msg="Exported Report Excel";
		EmailExcelDto dto=new EmailExcelDto();
		dto.setEmailId("xyz@gmail.com");
		when(mockService.exportReport(dto)).thenReturn(msg);
		ResponseDto resp=mockController.exportReport(dto);
	}
	
	@Test
	public void generateEventExcelTest() throws IOException {
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ByteArrayOutputStream byteArrayOutputStream = Mockito.mock(ByteArrayOutputStream.class);
		when(mockService.generateEventExcel()).thenReturn(byteArrayOutputStream);
		mockController.generateEventExcel(httpServletResponse, request);
	}
	@Test(expected=NullPointerException.class)
	public void generateEventExcelFalseTest() throws IOException {
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ByteArrayOutputStream byteArrayOutputStream = Mockito.mock(ByteArrayOutputStream.class);
		when(mockService.generateEventExcel()).thenThrow(NullPointerException.class);
		mockController.generateEventExcel(httpServletResponse, request);
	}
	
	@Test
	public void generateReportExcelTest() throws IOException {
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ByteArrayOutputStream byteArrayOutputStream = Mockito.mock(ByteArrayOutputStream.class);
		when(mockService.generateReportExcel()).thenReturn(byteArrayOutputStream);
		//when(mockService.generateReportExcel()).thenReturn(excelDto);
		mockController.generateReportExcel(httpServletResponse, request);
	}
	@Test(expected=NullPointerException.class)
	public void generateReportExcelFalseTest() throws IOException {
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ByteArrayOutputStream byteArrayOutputStream = Mockito.mock(ByteArrayOutputStream.class);
		when(mockService.generateReportExcel()).thenThrow(NullPointerException.class);
		mockController.generateReportExcel(httpServletResponse, request);
	}
}
