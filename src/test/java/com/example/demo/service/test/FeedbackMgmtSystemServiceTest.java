package com.example.demo.service.test;

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

import com.feedback.domain.IFeedbackMgmtSystemDomain;
import com.feedback.dto.EmailExcelDto;
import com.feedback.dto.EventDto;
import com.feedback.dto.FeedbackDto;
import com.feedback.dto.ReportDto;
import com.feedback.dto.ResponseDto;
import com.feedback.dto.UserDto;
import com.feedback.service.FeedbackMgmtSystemService;

public class FeedbackMgmtSystemServiceTest {
	@Mock
	private IFeedbackMgmtSystemDomain mockDomain;

	@InjectMocks
	private FeedbackMgmtSystemService mockService;

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
		user.getEmail();
		user.getPwd();
		user.getUser();
		user.getUserRole();
		ResponseDto resp=new ResponseDto();
		resp.setMessage("Login Successful");
		String res="Login Successful";
		when(mockDomain.getLoginDetails(user)).thenReturn(res);
		// Run the test
		mockService.getLoginDetails(user);
	}
	
	@Test
	public void getReportDetailsTest() throws Exception {
		List<ReportDto> list=new ArrayList<>();
		ReportDto report=new ReportDto();
		report.setBaseLocation("CBE");
		report.setBeneficiaryName("XXXX");
		report.setCouncilName("QAQ");
		report.setEventId("EVNT0001");
		report.setBusinessUnit("QE&A");
		report.setProjectName("XXYA");
		report.getBaseLocation();report.getBeneficiaryName();report.getBusinessUnit();
		report.getCouncilName();report.getEventId();
		report.getProjectName();
		list.add(report);
		when(mockDomain.getReportDetails()).thenReturn(list);
		List<ReportDto> val=mockService.getReportDetails();
	}
	@Test
	public void getEventDetailsTest() throws Exception {
		List<EventDto> list=new ArrayList<>();
		EventDto event=new EventDto();
		event.setBaseLocation("CAD");
		event.setBusinessUnit("MLEU");
		event.setMonth("AUG");
		event.setCouncilName("CouncilNm");event.setEventId("EVNT00032");
		event.setEventName("Joy of Helping"); event.setTotalHours(23);event.setBeneficiaryName("Benf.Name");
		event.setStatus("Approved");
		event.setTotalVolunteers(100);event.getBaseLocation();event.getBeneficiaryName();
		event.getBusinessUnit();
		event.getCouncilName(); event.getEventId(); event.getEventName();
		event.getMonth(); event.getStatus(); event.getTotalHours();
		event.getTotalVolunteers();
		when(mockDomain.getEventDetails()).thenReturn(list);
		List<EventDto> val=mockService.getEventDetails();
	}
	@Test
	public void saveFeedbackTest() throws SQLException {
		FeedbackDto feedbackDto=new FeedbackDto();
		feedbackDto.setAnswer1("Answer");
		feedbackDto.setAnswer2("Answer2");
		feedbackDto.setAnswer3("Answer3");
		feedbackDto.getAnswer1();feedbackDto.getAnswer2();
		feedbackDto.getAnswer3();
		ResponseDto response=new ResponseDto();
		response.setMessage("Saved Successfully in DB");
		String message="Saved Successfully in DB";
		when(mockDomain.saveFeedback(feedbackDto)).thenReturn(message);
		String msg=mockService.saveFeedback(feedbackDto);
	}
	
	@Test
	public void exportExcelTest() {
		ResponseDto response=new ResponseDto(); 
		response.setMessage("Exported Event Excel");
		String msg="Exported Event Excel";
		EmailExcelDto dto=new EmailExcelDto();
		dto.setEmailId("xyz@gmail.com");
		when(mockDomain.exportExcel(dto)).thenReturn(msg);
		String resp=mockService.exportExcel(dto);
		
	}
	
	@Test
	public void exportReportTest() {
		ResponseDto response=new ResponseDto(); 
		response.setMessage("Exported Report Excel");
		response.getMessage();
		String msg="Exported Report Excel";
		EmailExcelDto dto=new EmailExcelDto();
		dto.setEmailId("xyz@gmail.com");
		dto.getEmailId();
		when(mockDomain.exportReport(dto)).thenReturn(msg);
		String resp=mockService.exportReport(dto);
	}
	@Test
	public void generateEventExcelTest() throws IOException {
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ByteArrayOutputStream byteArrayOutputStream = Mockito.mock(ByteArrayOutputStream.class);
		when(mockDomain.generateEventExcel()).thenReturn(byteArrayOutputStream);
		mockService.generateEventExcel();
	}
	@Test
	public void generateReportExcelTest() throws IOException {
		HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ByteArrayOutputStream byteArrayOutputStream = Mockito.mock(ByteArrayOutputStream.class);
		when(mockDomain.generateReportExcel()).thenReturn(byteArrayOutputStream);
		//when(mockDomain.generateReportExcel()).thenReturn(excelDto);
		mockService.generateReportExcel();
	}
}
