package ca.app.persistence.listing;

import java.util.List;

import ca.app.model.listing.Purchase;

public interface PurchaseDAO {
	public void insert(Purchase purchase);
	public void saveOrUpdate(Purchase purchase);
	public void delete(Purchase purchase);
	public void deleteByPurchaseId(int purchaseId);
	
	public Purchase getByPurchaseId(int purchaseId);
	
	public List<Purchase> getAllPurchases();
}