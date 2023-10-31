// The original concept was found here: http://ethaizone.github.io/Bootstrap-Confirmation/
// It was coded to work with Bootstrap 2.3, so I used the Popover class from Bootstrap 3.2
// as my starting point and integrated the important stuff from the original developer's work.
// ... Steph
//
!function ($) {
	'use strict';

	// CONFIRMATION PUBLIC CLASS DEFINITION
	// ===============================

	var Confirmation = function (element, options) {
		this.init('confirmation', element, options);
		
		var that = this;
		
		// There are 2 elements to keep track of. The base element that you include in your page which is clicked, 
		// hovered, etc by your user, and the popover element that is created by Bootstrap. You can find one
		// from the other because Bootstrap generates a unique id which is written into both the aria-describedby
		// attribute of the base element and the id attribute of the popover element.
		
		this.$element.on('show.bs.confirmation', function(e) {
			// Clicking anything else that requires confirmation will close current visible confirmation
			if(that.options.singleton) {
				$("[id^=confirmation]").hide();
			}
		});
	
		$(element).on('shown.bs.confirmation', function(e) {
			// Clicking anywhere else in the window will close current visible confirmation
			var event_body = false;
			var all = that.options.all_selector;
			if(that.isPopout()) {
				if(!event_body) {
					
					// Register click handler on the document body
					event_body = $('body').on('click', function (e) {
						
						// Determine if the click event should result in a confirmation being shown
						var clickEventRequiresConfirmation = false;
						if($(all).is(e.target)) clickEventRequiresConfirmation = true;
						if($(all).has(e.target).length) clickEventRequiresConfirmation = true;
						if($(all).next('div').has(e.target).length) clickEventRequiresConfirmation = true;
						
						if (clickEventRequiresConfirmation) {
							// Hide all confirmations except for the one that was just creatd
							var popoverElId = "#"+$(e.target).attr('aria-describedby');
							$("[id^=confirmation]").not(popoverElId).hide();
						} else {
							// Hide all confirmations
							$("[id^=confirmation]").hide();
							
						}
						
						// Unbind the current click handler
						$('body').unbind(e);
						event_body = false;
						return;
					});
				}
			}
		});
	
	};

	if (!$.fn.tooltip) throw new Error('Confirmation requires tooltip.js');

	Confirmation.VERSION	= '1.0.0'; // Compatible with Bootstrap 3.2.0

	Confirmation.DEFAULTS = $.extend({}, $.fn.tooltip.Constructor.DEFAULTS, {
		placement: 'left',
		trigger: 'click',
		title: 'Are you sure?',
		template: 
			'<div class="popover" role="tooltip">' +
				'<div class="arrow"></div>' +
				'<h3 class="popover-title"></h3>' +
				'<div class="popover-content text-center">' +
					'<div>' +
						'<button class="btn btn-small" data-dismiss="confirmation"></button>&nbsp;' +
						'<button class="btn btn-small"></button>' +
					'</div>' +
				'</div>' +
			'</div>',
		btnOkClass:	'btn-primary',
		btnCancelClass:	'btn-default',
		btnOkLabel: '<span class="glyphicon glyphicon-ok"></span> Ok',
		btnCancelLabel: '<span class="glyphicon glyphicon-remove"></span> Cancel',
		onConfirm: function(){ alert('yes'); },
		onCancel: function(){},
		singleton: false,
		popout: false
	});


	// NOTE: CONFIRMATION EXTENDS tooltip.js
	// ================================

	Confirmation.prototype = $.extend({}, $.fn.tooltip.Constructor.prototype);

	Confirmation.prototype.constructor = Confirmation;

	Confirmation.prototype.getDefaults = function () {
		return Confirmation.DEFAULTS;
	};

	Confirmation.prototype.getId = function () {
		var id
			, $e = this.$element;

		id = $e.attr('id');;

		return id;
	};
	
	Confirmation.prototype.setContent = function () {
		var $tip = this.tip()
			, $e = this.$element
			, title = this.getTitle()
			, $btnOk = this.btnOk()
			, $btnCancel = this.btnCancel()
			, btnOkClass = this.getBtnOkClass()
			, btnCancelClass = this.getBtnCancelClass()
			, btnOkLabel = this.getBtnOkLabel()
			, btnCancelLabel = this.getBtnCancelLabel()
			, onConfirm = this.getOnConfirm()
			, onCancel = this.getOnCancel()
			, o = this.options;
		
		$tip.find('.popover-title').text(title);
	
		$btnOk.addClass(btnOkClass).html(btnOkLabel).on('click', onConfirm);
		$btnCancel.addClass(btnCancelClass).html(btnCancelLabel).on('click', onCancel);
	
		$tip.removeClass('fade top bottom left right in');
	
		// IE8 doesn't accept hiding via the `:empty` pseudo selector, we have to do
		// this manually by checking the contents.
		if (!$tip.find('.popover-title').html()) $tip.find('.popover-title').hide();
	};

	Confirmation.prototype.hasContent = function () {
		return this.getTitle();
	};

	Confirmation.prototype.getContent = function () {
		return '';
	};
	
	Confirmation.prototype.isPopout = function () {
		var popout
			, $e = this.$element
			, o = this.options;

		popout = $e.attr('data-popout') || (typeof o.popout == 'function' ? o.popout.call($e[0]) :	o.popout);

		if(popout == 'false') popout = false;

		return popout;
	};
	
	Confirmation.prototype.getBtnOkClass = function () {
		var btnOkClass
			, $e = this.$element
			, o = this.options;

		btnOkClass = $e.attr('data-btnOkClass') || (typeof o.btnOkClass == 'function' ? o.btnOkClass.call($e[0]) :	o.btnOkClass);

		return btnOkClass;
	};
	
	Confirmation.prototype.getBtnCancelClass = function () {
		var btnCancelClass
			, $e = this.$element
			, o = this.options;

		btnCancelClass = $e.attr('data-btnCancelClass') || (typeof o.btnCancelClass == 'function' ? o.btnCancelClass.call($e[0]) :	o.btnCancelClass);

		return btnCancelClass;
	};
	
	Confirmation.prototype.getBtnOkLabel = function () {
		var btnOkLabel
			, $e = this.$element
			, o = this.options;

		btnOkLabel = $e.attr('data-btnOkLabel') || (typeof o.btnOkLabel == 'function' ? o.btnOkLabel.call($e[0]) :	o.btnOkLabel);

		return btnOkLabel;
	};
	
	Confirmation.prototype.getBtnCancelLabel = function () {
		var btnCancelLabel
			, $e = this.$element
			, o = this.options;

		btnCancelLabel = $e.attr('data-btnCancelLabel') || (typeof o.btnCancelLabel == 'function' ? o.btnCancelLabel.call($e[0]) :	o.btnCancelLabel);

		return btnCancelLabel;
	};
	
	Confirmation.prototype.getOnConfirm = function () {
		var onConfirm
			, $tip = this.tip()
			, $e = this.$element
			, o = this.options;

		onConfirm = $e.attr('data-onConfirm') || o.onConfirm;
		onConfirm = (typeof onConfirm == 'function') ? onConfirm : new Function(onConfirm);

		return function() {
			$tip.hide();
			onConfirm.call($e[0]);
		};
	};
	
	Confirmation.prototype.getOnCancel = function () {
		var onCancel
			, $tip = this.tip()
			, $e = this.$element
			, o = this.options;

		onCancel = $e.attr('data-onCancel') || o.onCancel;
		onCancel = (typeof onCancel == 'function') ? onCancel : new Function(onCancel);

		return function() {
			$tip.hide();
			onCancel.call($e[0]);
		};
	};

	Confirmation.prototype.arrow = function () {
		return (this.$arrow = this.$arrow || this.tip().find('.arrow'));
	};

	Confirmation.prototype.tip = function () {
		if (!this.$tip) this.$tip = $(this.options.template);
		return this.$tip;
	};

	Confirmation.prototype.btnOk = function () {
		var $tip = this.tip();
		return $tip.find('.popover-content > div > button:not([data-dismiss="confirmation"])');
	};

	Confirmation.prototype.btnCancel = function () {
		var $tip = this.tip();
		return $tip.find('.popover-content > div > button[data-dismiss="confirmation"]');
	};

	Confirmation.prototype.hide = function () {
		var $tip = this.tip()
			, $btnOk = this.btnOk()
			, $btnCancel = this.btnCancel();
		
		$tip.removeClass('in');
		
		$btnOk.off('click');
		$btnCancel.off('click');

		return this;
	};

	Confirmation.prototype.destroy = function () {
		this.hide().$element.off('.' + this.type).removeData(this.type);
	};


	// POPOVER PLUGIN DEFINITION
	// =========================

	function Plugin(option) {
		var that = this;
		return this.each(function () {
			var $this	= $(this);
			var data	= $this.data('confirmation');
			var options = typeof option == 'object' && option;
	
			options = options || {};
			options.all_selector = that.selector;
			
			if (!data && option == 'destroy') return;
			if (!data) $this.data('confirmation', (data = new Confirmation(this, options)));
			if (typeof option == 'string') data[option]();
		});
	}

	var old = $.fn.confirmation;

	$.fn.confirmation				= Plugin;
	$.fn.confirmation.Constructor 	= Confirmation;


	// POPOVER NO CONFLICT
	// ===================

	$.fn.confirmation.noConflict = function () {
		$.fn.confirmation = old;
		return this;
	};

}(jQuery);