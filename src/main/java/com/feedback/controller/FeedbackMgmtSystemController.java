package com.feedback.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.feedback.constants.FeedbackMgmtSystemConstants;
import com.feedback.dto.EmailExcelDto;
import com.feedback.dto.EventDto;
import com.feedback.dto.FeedbackDto;
import com.feedback.dto.ReportDto;
import com.feedback.dto.ResponseDto;
import com.feedback.dto.UserDto;
import com.feedback.service.IFeedbackMgmtSystemService;

@RestController
@RequestMapping("/feedback-mgmt")
public class FeedbackMgmtSystemController {

	
	@Autowired
	private IFeedbackMgmtSystemService feedbackService;
	
	@PostMapping(value = "/login-credentials",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto getLoginDetails(@RequestBody UserDto userDto) throws SQLException{
		ResponseDto response=new ResponseDto();
		String message=feedbackService.getLoginDetails(userDto);
		response.setMessage(message);
		return response;	
	}
	@GetMapping(value="/fetch-report",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ReportDto> getReportDetails() throws Exception {
		List<ReportDto> report=feedbackService.getReportDetails();
		return report;	
	}
	
	@GetMapping(value="/fetch-event-dtls",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<EventDto> getEventDetails() throws Exception {
		List<EventDto> event=feedbackService.getEventDetails();
		return event;
	}
	
	@PostMapping(value="/save-feedback",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto saveFeedback(@RequestBody FeedbackDto feedbackDto) throws SQLException {
		ResponseDto response=new ResponseDto();
		String message=feedbackService.saveFeedback(feedbackDto);
		response.setMessage(message);
		return response;
	}
	
	@GetMapping(value="/event-excel")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void generateEventExcel(final HttpServletResponse httpServletResponse, final HttpServletRequest request)
			throws  IOException {
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			byteArrayOutputStream = feedbackService.generateEventExcel();
			final String fileName = FeedbackMgmtSystemConstants.EVENT_TEMPLATE_NAME;
			httpServletResponse.setHeader("Content-Disposition", "attachment; fileName=" + fileName.toString());
			httpServletResponse.setContentType("application/octet-stream");
			byteArrayOutputStream.writeTo(httpServletResponse.getOutputStream());
			byteArrayOutputStream.flush();
			byteArrayOutputStream.close();
		} catch (final IOException ioException) {
			final String errorMessage = "IOException occured while fetching OutputStream";
		} finally {
			IOUtils.closeQuietly(byteArrayOutputStream);
		}
		System.out.println("Data is saved in excel file.");
	}
	@GetMapping(value="/report-excel")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void generateReportExcel(final HttpServletResponse httpServletResponse, final HttpServletRequest request)
			throws  IOException {
		ByteArrayOutputStream byteArrayOutputStream = null;
		ResponseDto responseDto=null;
		try {
			final String fileName = FeedbackMgmtSystemConstants.REPORT_TEMPLATE_NAME;
			httpServletResponse.setHeader("Content-Disposition", "attachment; fileName=" + fileName.toString());
			httpServletResponse.setContentType("application/octet-stream");
			byteArrayOutputStream = feedbackService.generateReportExcel();
			httpServletResponse.setStatus(200);
			byteArrayOutputStream.writeTo(httpServletResponse.getOutputStream());
			byteArrayOutputStream.flush();
			byteArrayOutputStream.close();
		} catch (final IOException ioException) {
			final String errorMessage = "IOException occured while fetching OutputStream";
		} finally {
			IOUtils.closeQuietly(byteArrayOutputStream);
		}
		//responseDto.setMessage("Report Excel is downloaded Successfully");
		System.out.println("Data is saved in excel file.");
		//return responseDto;
	}
	
	@PostMapping(value="/export-event",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseDto exportExcel(@RequestBody EmailExcelDto emailDto) {
		ResponseDto response=new ResponseDto();
		String message=feedbackService.exportExcel(emailDto);
		response.setMessage(message);
		return response;
	}
	@PostMapping(value="/export-report",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseDto exportReport(@RequestBody EmailExcelDto emailDto) {
		ResponseDto response=new ResponseDto();
		String message=feedbackService.exportReport(emailDto);
		response.setMessage(message);
		return response;
	}
	
	
}
