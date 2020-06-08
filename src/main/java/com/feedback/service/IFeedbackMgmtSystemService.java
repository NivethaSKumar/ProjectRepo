package com.feedback.service;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.List;

import com.feedback.dto.EmailExcelDto;
import com.feedback.dto.EventDto;
import com.feedback.dto.FeedbackDto;
import com.feedback.dto.ReportDto;
import com.feedback.dto.UserDto;

public interface IFeedbackMgmtSystemService {

	String getLoginDetails(UserDto userDto) throws SQLException;

	List<ReportDto> getReportDetails() throws Exception;

	List<EventDto> getEventDetails() throws Exception;

	String saveFeedback(FeedbackDto feedbackDto) throws SQLException;

	ByteArrayOutputStream generateEventExcel();

	ByteArrayOutputStream generateReportExcel();

	String exportExcel(EmailExcelDto emailDto);

	String exportReport(EmailExcelDto emailDto);

}
