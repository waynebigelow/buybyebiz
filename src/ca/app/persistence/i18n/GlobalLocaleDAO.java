package ca.app.persistence.i18n;

import java.util.List;

import ca.app.model.i18n.GlobalLocale;

public interface GlobalLocaleDAO {
	public List<GlobalLocale> getAllGlobalLocales();
}