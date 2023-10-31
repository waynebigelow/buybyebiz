<%@ page import="ca.app.model.application.Provider"%>
<%@ page import="ca.app.util.ProjectUtil" %>

<div id="cdnFrPayment">
<div class="form-group">
	<div class="col-xs-12">
		<select name="<%=Provider.PAYPAL.getFieldName()%>" id="<%=Provider.PAYPAL.getFieldName()%>" class="form-control">
			<option value="1 Mois">1 Mois $25.00 CAD</option>
			<option value="3 Mois">3 Mois $60.00 CAD</option>
			<option value="6 Mois">6 Mois $110.00 CAD</option>
			<option value="1 An">1 An $200.00 CAD</option>
		</select>
	</div>
</div>
<input type="hidden" name="currency_code" id="currency_code" value="CAD" />
<input type="hidden" name="hosted_button_id" value="<%=ProjectUtil.getProperty("paypal.hosted.cdn.fr.button.id")%>" />
<input type="hidden" name="lc" id="lc" value="CA" />
<input type="hidden" name="item_name" id="item_name" value="Extension d'annonce" />
<input type="hidden" name="button_subtype" value="services" />
<input type="hidden" name="bn" value="BuyByeBiz_BuyNow_WPS_CA" />
</div>