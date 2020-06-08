package com.feedback.service;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback.domain.IFeedbackMgmtSystemDomain;
import com.feedback.dto.EmailExcelDto;
import com.feedback.dto.EventDto;
import com.feedback.dto.FeedbackDto;
import com.feedback.dto.ReportDto;
import com.feedback.dto.UserDto;

@Service
public class FeedbackMgmtSystemService implements IFeedbackMgmtSystemService{

	@Autowired
	private IFeedbackMgmtSystemDomain feedbackDomain;
	
	@Override
	public String getLoginDetails(UserDto userDto) throws SQLException {
		String message=feedbackDomain.getLoginDetails(userDto);
		return message;
	}

	@Override
	public List<ReportDto> getReportDetails() throws Exception {
		List<ReportDto> report=feedbackDomain.getReportDetails();
		return report;
	}

	@Override
	public List<EventDto> getEventDetails() throws Exception {
		List<EventDto> event=feedbackDomain.getEventDetails();
		return event;
	}

	@Override
	public String saveFeedback(FeedbackDto feedbackDto) throws SQLException {
		String message=feedbackDomain.saveFeedback(feedbackDto);
		return message;
	}

	@Override
	public ByteArrayOutputStream generateEventExcel() {
		ByteArrayOutputStream os=feedbackDomain.generateEventExcel();
		return os;
	}

	@Override
	public ByteArrayOutputStream generateReportExcel() {
		//DownloadExcelDto excelDto=feedbackDomain.generateReportExcel();
		ByteArrayOutputStream os = feedbackDomain.generateReportExcel();
		return os;
	}

	@Override
	public String exportExcel(EmailExcelDto emailDto) {
		String message=feedbackDomain.exportExcel(emailDto);
		return message;
	}

	@Override
	public String exportReport(EmailExcelDto emailDto) {
		String message=feedbackDomain.exportReport(emailDto);
		return message;
	}
	

}
