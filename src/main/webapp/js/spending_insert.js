function autocompleteCategory(request, response) {
	$.ajax("#springUrl('/view/spending_insert')", {
		type : 'POST',
		data : {
			type : 'category',
			term : $('#inputcategory').val()
		},
		success : response,
		dataType : 'json'
	});
};

function autocompleteSubcategory(request, response) {
	$.ajax("#springUrl('/view/spending_insert')", {
		type : 'POST',
		data : {
			type : 'subcategory',
			category : $('#inputcategory').val(),
			term : $('#inputsubcategory').val()
		},
		success : response,
		dataType : 'json'

	});
};

$(function() {
	$('#inputcategory').focus().select();

	$("#inputcategory").autocomplete({
		source : autocompleteCategory,
		select : function(event, ui) {
			$('#inputcategory').val(ui.item.category);
			$('#inputsubcategory').val(ui.item.subcategory);
			return false;
		}
	}).data("autocomplete")._renderItem = function(ul, item) {
		$('<li>')
			.data("item.autocomplete", item)
			.append("<a>" + item.category + " / " + item.subcategory + "</a>")
			.appendTo(ul);
	};

	$("#inputsubcategory").autocomplete({
		source : autocompleteSubcategory,
		select : function(event, ui) {
			$('#inputcategory').val(ui.item.category);
			$('#inputsubcategory').val(ui.item.subcategory);
			return false;
		}
	}).data("autocomplete")._renderItem = function(ul, item) {
		$('<li>')
		.data("item.autocomplete", item)
		.append("<a>" + item.category + " / " + item.subcategory + "</a>")
		.appendTo(ul);
	};

	$("#inputname").autocomplete({
		source : "#springUrl('/view/spending_insert')"
	});
});