package com.feedback.dto;

public class ReportDto {
public ReportDto(){
	
}
private String eventId;
private String  beneficiaryName;
private String baseLocation;
private String councilName;
private String projectName;
private String businessUnit;
public String getEventId() {
	return eventId;
}
public void setEventId(String eventId) {
	this.eventId = eventId;
}
public String getBeneficiaryName() {
	return beneficiaryName;
}
public void setBeneficiaryName(String beneficiaryName) {
	this.beneficiaryName = beneficiaryName;
}
public String getBaseLocation() {
	return baseLocation;
}
public void setBaseLocation(String baseLocation) {
	this.baseLocation = baseLocation;
}
public String getCouncilName() {
	return councilName;
}
public void setCouncilName(String councilName) {
	this.councilName = councilName;
}
public String getProjectName() {
	return projectName;
}
public void setProjectName(String projectName) {
	this.projectName = projectName;
}
public String getBusinessUnit() {
	return businessUnit;
}
public void setBusinessUnit(String businessUnit) {
	this.businessUnit = businessUnit;
}






}
