package ca.app.persistence.common;

import ca.app.model.common.SupportIssue;

public interface SupportIssueDAO {
	public void insert(SupportIssue issue);
	public void update(SupportIssue issue);
	public void delete(SupportIssue issue);
}