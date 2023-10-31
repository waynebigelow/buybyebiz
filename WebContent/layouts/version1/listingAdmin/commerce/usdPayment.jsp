<%@ page import="ca.app.model.application.Provider"%>
<%@ page import="ca.app.util.ProjectUtil" %>

<div id="usPayment">
<div class="form-group">
	<div class="col-xs-12">
		<select name="<%=Provider.PAYPAL.getFieldName()%>" id="<%=Provider.PAYPAL.getFieldName()%>" class="form-control">
			<option value="1 Month">1 Month $15.00 USD</option>
			<option value="3 Months">3 Months $40.00 USD</option>
			<option value="6 Months">6 Months $70.00 USD</option>
			<option value="1 Year">1 Year $130.00 USD</option>
		</select>
	</div>
</div>
<input type="hidden" name="currency_code" id="currency_code" value="USD" />
<input type="hidden" name="hosted_button_id" value="<%=ProjectUtil.getProperty("paypal.hosted.us.button.id")%>" />
<input type="hidden" name="lc" id="lc" value="US" />
<input type="hidden" name="item_name" id="item_name" value="Listing Extension" />
<input type="hidden" name="button_subtype" value="services" />
<input type="hidden" name="bn" value="BuyByeBiz_BuyNow_WPS_US" />
</div>