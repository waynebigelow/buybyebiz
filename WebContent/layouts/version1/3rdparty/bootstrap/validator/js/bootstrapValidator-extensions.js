(function($) {
	$.fn.bootstrapValidator.i18n.greaterThanTime = $.extend($.fn.bootstrapValidator.i18n.greaterThanTime || {}, {
		'default': 'The end time must be later than the start time.'
	});

	$.fn.bootstrapValidator.validators.greaterThanTime = {
		html5Attributes: {
			message: 'message',
			field: 'field'
		},

		/**
		 * Check if the time of day specified by the input value is greater than the time of day
		 * specified by the other configured field.
		 *
		 * @param {BootstrapValidator} validator The validator plugin instance
		 * @param {jQuery} $field Field element
		 * @param {Object} options Consists of the following key:
		 * - field: The name of field that will be used to compare with current one
		 * @returns {Boolean}
		 */
		validate: function(validator, $field, options) {
			var value = $field.val();
			if (value === '') {
				return true;
			}

			var compareWith = validator.getFieldElements(options.field);
			if (compareWith === null || compareWith.length === 0) {
				return true;
			}
			
			if (compareWith.val() === '') {
				return true;
			}

			var endDateInMs = Date.parse('01/01/1970 ' + value);
			var startDateInMs = Date.parse('01/01/1970 ' + compareWith.val());
			if (startDateInMs < endDateInMs) {
				validator.updateStatus(options.field, validator.STATUS_VALID, 'greaterThanTime');
				return true;
			} 
				
			return false;
		}
	};
}(window.jQuery));