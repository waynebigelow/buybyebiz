package ca.app.persistence.application;

import java.util.List;

import ca.app.model.application.Application;


public interface ApplicationDAO {

	public Application getByApplicationId(int applicationId);
	public Application getApplicationByListingId(int listingId);
	public List<Application> getAllApplications();
	public void saveOrUpdate(Application application);
	public void deleteByApplicationId(int applicationId);
	public Application getApplicationForHostName(String hostName);
}