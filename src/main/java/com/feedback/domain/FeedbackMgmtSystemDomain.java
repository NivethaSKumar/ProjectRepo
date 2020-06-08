package com.feedback.domain;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.feedback.dto.EmailExcelDto;
import com.feedback.dto.EventDto;
import com.feedback.dto.FeedbackDto;
import com.feedback.dto.ReportDto;
import com.feedback.dto.UserDto;

@Component
public class FeedbackMgmtSystemDomain implements IFeedbackMgmtSystemDomain{
	@Autowired
	private JavaMailSender javaMailSender;
	@Override
	public String getLoginDetails(UserDto userDto) throws SQLException {
		String message = "";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn=getConnection();
		
		String query = "select * from user where EmailAddress=? and Pwd=? ";
		ps=conn.prepareStatement(query);
		ps.setString(1, userDto.getEmail());
		ps.setString(2, userDto.getPwd());
		ResultSet rs = ps.executeQuery();
		System.out.println("result :: "+rs.getRow());
		if(rs.next()) {
			message="Valid User.Login successful";
		}else {
			message="Invalid User";
		}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new SQLException();
			}
		}
		return message;
	}

	private Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://127.0.0.1:3306/sys","SYSDB","root");  
		return con;
	}

	@Override
	public List<ReportDto> getReportDetails() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		List<ReportDto> result=new ArrayList<>();
		try {
			conn=getConnection();
		
		String query = "select EventId,BeneficiaryName,BaseLocation,CouncilNm,ProjectNm,BusinessUnit "
				+ " from report ";
		ps=conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ReportDto report=new ReportDto();
			report.setEventId(rs.getString(1));
			report.setBeneficiaryName(rs.getString(2));
			report.setBaseLocation(rs.getString(3));
			report.setCouncilName(rs.getString(4));
			report.setProjectName(rs.getString(5));
			report.setBusinessUnit(rs.getString(6));
			result.add(report);
		}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception();
		}finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public List<EventDto> getEventDetails() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		List<EventDto> result=new ArrayList<>();
		try {
			conn=getConnection();
		
		String query = " select EventId,Month,BaseLocation,CouncilNm,BeneficiaryName, "
				+ " EventNm,BusinessUnit,Status,TotalVolunteers,TotalHours from event ";
		ps=conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			EventDto event=new EventDto();
			event.setEventId(rs.getString(1));
			event.setMonth(rs.getString(2));
			event.setBaseLocation(rs.getString(3));
			event.setCouncilName(rs.getString(4));
			event.setBeneficiaryName(rs.getString(5));
			event.setEventName(rs.getString(6));
			event.setBusinessUnit(rs.getString(7));
			event.setStatus(rs.getString(8));
			event.setTotalVolunteers(rs.getInt(9));
			event.setTotalHours(rs.getInt(10));
			result.add(event);
		}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception();
		}finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public String saveFeedback(FeedbackDto feedbackDto) throws SQLException {
		String message="";
		String query="insert into feedbackdtls(Answer1,Answer2,Answer3) values(?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn=getConnection();
			ps = conn.prepareStatement(query);	
				ps.setString(1, feedbackDto.getAnswer1());
				ps.setString(2, feedbackDto.getAnswer2());
				ps.setString(3, feedbackDto.getAnswer3());
		ps.executeUpdate();
		message="Feedback successfully saved in database";
		}catch(Exception e) {
			e.printStackTrace();
			message="Feedback is not saved successfully in database";
		}finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new SQLException();
			}
		}
		//return "Feedback Successfully saved in database";
		return message;
	}



	@Override
	public ByteArrayOutputStream generateEventExcel() {
		Connection con=null;
		PreparedStatement ps=null;
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		try {
			con=getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select EventId,Month,BaseLocation,CouncilNm,BeneficiaryName,EventNm,BusinessUnit,Status,TotalVolunteers,TotalHours from event ");

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN); 
			style.setBorderTop(BorderStyle.THIN); 
			style.setBorderRight(BorderStyle.THIN); 
			style.setBorderLeft(BorderStyle.THIN); 
			HSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short)12);
			font.setBold(true);
			style.setFont(font);
			rowhead.createCell((short) 0).setCellValue("EVENT ID");
			rowhead.createCell((short) 1).setCellValue("MONTH");
			rowhead.createCell((short) 2).setCellValue("BASE LOCATION");
			rowhead.createCell((short) 3).setCellValue("COUNCIL NAME");
			rowhead.createCell((short) 4).setCellValue("BENEFICIARY NAME");
			rowhead.createCell((short) 5).setCellValue("EVENT NAME");
			rowhead.createCell((short) 6).setCellValue("BUSINESS UNIT");
			rowhead.createCell((short) 7).setCellValue("STATUS");
			rowhead.createCell((short) 8).setCellValue("TOTAL VOLUNTEERS");
			rowhead.createCell((short) 9).setCellValue("TOTAL HOURS");
			for(int j = 0; j<=9; j++)
				rowhead.getCell(j).setCellStyle(style);
			int index = 1;
			while (rs.next()) {

				HSSFRow row = sheet.createRow((short) index);
				row.createCell((short) 0).setCellValue(rs.getString(1));
				row.createCell((short) 1).setCellValue(rs.getString(2));
				row.createCell((short) 2).setCellValue(rs.getString(3));
				row.createCell((short) 3).setCellValue(rs.getString(4));
				row.createCell((short) 4).setCellValue(rs.getString(5));
				row.createCell((short) 5).setCellValue(rs.getString(6));
				row.createCell((short) 6).setCellValue(rs.getString(7));
				row.createCell((short) 7).setCellValue(rs.getString(8));
				row.createCell((short) 8).setCellValue(rs.getInt(9));
				row.createCell((short) 9).setCellValue(rs.getInt(10));
				index++;
			}
			for (int columnIndex = 0; columnIndex < 15; columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
            }
			FileOutputStream fileOut = new FileOutputStream("C://Template/Event.xls");
			wb.write(os);
			wb.write(fileOut);
			fileOut.close();
			System.out.println("Data is saved in excel file.");
			rs.close();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return os;
	}

	@Override
	public ByteArrayOutputStream generateReportExcel() {
		Connection con=null;
		PreparedStatement ps=null;
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		try {
			con=getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select EventId,BeneficiaryName,BaseLocation,CouncilNm,ProjectNm,BusinessUnit from report ");

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN); 
			style.setBorderTop(BorderStyle.THIN); 
			style.setBorderRight(BorderStyle.THIN); 
			style.setBorderLeft(BorderStyle.THIN); 
			HSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short)12);
			font.setBold(true);
			style.setFont(font);
			rowhead.createCell((short) 0).setCellValue("EVENT ID");
			rowhead.createCell((short) 1).setCellValue("BENEFICIARY NAME");
			rowhead.createCell((short) 2).setCellValue("BASE LOCATION");
			rowhead.createCell((short) 3).setCellValue("COUNCIL NAME");
			rowhead.createCell((short) 4).setCellValue("PROJECT NAME");
			rowhead.createCell((short) 5).setCellValue("BUSINESS UNIT");
			for(int j = 0; j<=5; j++)
				rowhead.getCell(j).setCellStyle(style);
			int index = 1;
			while (rs.next()) {

				HSSFRow row = sheet.createRow((short) index);
				row.createCell((short) 0).setCellValue(rs.getString(1));
				row.createCell((short) 1).setCellValue(rs.getString(2));
				row.createCell((short) 2).setCellValue(rs.getString(3));
				row.createCell((short) 3).setCellValue(rs.getString(4));
				row.createCell((short) 4).setCellValue(rs.getString(5));
				row.createCell((short) 5).setCellValue(rs.getString(6));
				index++;
			}
			for (int columnIndex = 0; columnIndex < 15; columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
            }
			
			  FileOutputStream fileOut = new FileOutputStream("C://Template/Report.xls");
			wb.write(os);
			wb.write(fileOut);
			fileOut.close();
			System.out.println("Data is saved in excel file.");
			rs.close();
			con.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return os;
	}

	@Override
	public String exportExcel(EmailExcelDto emailDto) {
		String message="";
		Connection con=null;
		Statement st = null;
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		try {
			con=getConnection();
			st=con.createStatement();
			ResultSet rs = st.executeQuery("select EventId,Month,BaseLocation,CouncilNm,BeneficiaryName,"
					+ " EventNm,BusinessUnit,Status,TotalVolunteers,TotalHours from event ");

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN); 
			style.setBorderTop(BorderStyle.THIN); 
			style.setBorderRight(BorderStyle.THIN); 
			style.setBorderLeft(BorderStyle.THIN); 
			HSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short)12);
			font.setBold(true);
			style.setFont(font);
			rowhead.createCell((short) 0).setCellValue("EVENT ID");
			rowhead.createCell((short) 1).setCellValue("MONTH");
			rowhead.createCell((short) 2).setCellValue("BASE LOCATION");
			rowhead.createCell((short) 3).setCellValue("COUNCIL NAME");
			rowhead.createCell((short) 4).setCellValue("BENEFICIARY NAME");
			rowhead.createCell((short) 5).setCellValue("EVENT NAME");
			rowhead.createCell((short) 6).setCellValue("BUSINESS UNIT");
			rowhead.createCell((short) 7).setCellValue("STATUS");
			rowhead.createCell((short) 8).setCellValue("TOTAL VOLUNTEERS");
			rowhead.createCell((short) 9).setCellValue("TOTAL HOURS");
			for(int j = 0; j<=9; j++)
				rowhead.getCell(j).setCellStyle(style);
			int index = 1;
			while (rs.next()) {

				HSSFRow row = sheet.createRow((short) index);
				row.createCell((short) 0).setCellValue(rs.getString(1));
				row.createCell((short) 1).setCellValue(rs.getString(2));
				row.createCell((short) 2).setCellValue(rs.getString(3));
				row.createCell((short) 3).setCellValue(rs.getString(4));
				row.createCell((short) 4).setCellValue(rs.getString(5));
				row.createCell((short) 5).setCellValue(rs.getString(6));
				row.createCell((short) 6).setCellValue(rs.getString(7));
				row.createCell((short) 7).setCellValue(rs.getString(8));
				row.createCell((short) 8).setCellValue(rs.getInt(9));
				row.createCell((short) 9).setCellValue(rs.getInt(10));
				index++;
			}
			for (int columnIndex = 0; columnIndex < 15; columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
            }
			File file=new File("C://Template/Event.xls");
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(os);
			fileOut.write(os.toByteArray());
			MimeMessage msg = javaMailSender.createMimeMessage();

			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(msg, true);

				helper.setTo(emailDto.getEmailId());

				helper.setSubject("Event details");
				helper.setText("<h1>Event details</h1>", true);

				// hard coded a file path
				FileSystemResource file1 = new FileSystemResource(file);

				helper.addAttachment("event_dtl.xls", file1);

				javaMailSender.send(msg);
				fileOut.close();
				wb.close();
				System.out.print("Mail has been sent successfully");
				message="Mail has been sent successfully";
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message="Mail has not been sent";
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			message="Mail has not been sent";
		}finally {
			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
	}

	@Override
	public String exportReport(EmailExcelDto emailDto) {
		String message="";
		Connection con=null;
		Statement st = null;
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		try {
			con=getConnection();
			st=con.createStatement();
			ResultSet rs = st.executeQuery("select EventId,BeneficiaryName,BaseLocation,CouncilNm,ProjectNm,BusinessUnit from report ");

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Sheet");
			HSSFRow rowhead = sheet.createRow((short) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN); 
			style.setBorderTop(BorderStyle.THIN); 
			style.setBorderRight(BorderStyle.THIN); 
			style.setBorderLeft(BorderStyle.THIN); 
			HSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short)12);
			font.setBold(true);
			style.setFont(font);
			rowhead.createCell((short) 0).setCellValue("EVENT ID");
			rowhead.createCell((short) 1).setCellValue("BENEFICIARY NAME");
			rowhead.createCell((short) 2).setCellValue("BASE LOCATION");
			rowhead.createCell((short) 3).setCellValue("COUNCIL NAME");
			rowhead.createCell((short) 4).setCellValue("PROJECT NAME");
			rowhead.createCell((short) 5).setCellValue("BUSINESS UNIT");
			for(int j = 0; j<=5; j++)
				rowhead.getCell(j).setCellStyle(style);
			int index = 1;
			while (rs.next()) {

				HSSFRow row = sheet.createRow((short) index);
				row.createCell((short) 0).setCellValue(rs.getString(1));
				row.createCell((short) 1).setCellValue(rs.getString(2));
				row.createCell((short) 2).setCellValue(rs.getString(3));
				row.createCell((short) 3).setCellValue(rs.getString(4));
				row.createCell((short) 4).setCellValue(rs.getString(5));
				row.createCell((short) 5).setCellValue(rs.getString(6));
				index++;
			}
			for (int columnIndex = 0; columnIndex < 15; columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
            }
			File file=new File("C://Template/Report.xls");
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(os);
			fileOut.write(os.toByteArray());
			MimeMessage msg = javaMailSender.createMimeMessage();

			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(msg, true);

				helper.setTo(emailDto.getEmailId());

				helper.setSubject(" Report details");
				helper.setText("<h1>Report details</h1>", true);

				// hard coded a file path
				FileSystemResource file1 = new FileSystemResource(file);

				helper.addAttachment("report_dtl.xls", file1);

				javaMailSender.send(msg);
				fileOut.close();
				wb.close();
				System.out.print("Mail has been sent successfully");
				message="Mail has been sent successfully";
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message="Mail has not been sent";
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			message="Mail has not been sent";
		}finally {
			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
	}

}
