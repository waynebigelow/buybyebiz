package ca.app.persistence.common;

import ca.app.model.common.HashToken;

public interface HashTokenDAO {
	public void insert(HashToken token);
	public void update(HashToken token);
	public void delete(HashToken token);
	public void deleteByUserIdTypeId(int userId, int typeId);
	
	public HashToken getByHash(String hash);
}