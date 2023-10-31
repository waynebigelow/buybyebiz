package ca.app.persistence.application;

import ca.app.model.application.ProviderPackageLink;

public interface ProviderPackageLinkDAO {
	public ProviderPackageLink getByLinkId(int linkId);
	public void saveOrUpdate(ProviderPackageLink providerPackageLink);
	public void delete(ProviderPackageLink providerPackageLink);
	public void deleteByLinkId(int linkId);
}