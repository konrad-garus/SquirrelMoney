function autocompleteCategory(request, response) {
//	var z = $.ajax("#springUrl('/view/spending_insert')", {
//		async : false,
//		data : {
//			type : 'category',
//			term : $('#inputcategory').val()
//		}
//	});
//	//alert(JSON.stringify(z.responseText));
//	return z;
	
	$.ajax("#springUrl('/view/spending_insert')", {
		type: 'POST',
		data : {
			type : 'category',
			term : $('#inputcategory').val()
		},
		success: response,
		dataType: 'json'
	});
};

function autocompleteSubcategory(request, response) {
//	z = $.ajax("#springUrl('/view/spending_insert')", {
//		async : false,
//		data : {
//			type : 'subcategory',
//			category : $('#inputcategory').val(),
//			term : $('#inputsubcategory').val()
//		}
//	});
//	response(JSON.parse(z.responseText));
	
	$.ajax("#springUrl('/view/spending_insert')", {
		type: 'POST',
		data : {
			type : 'subcategory',
			category : $('#inputcategory').val(),
			term : $('#inputsubcategory').val()
		},
		success: response,
		dataType: 'json'
		
	});
//	response(JSON.parse(z.responseText));
};

$(function() {
	$('#inputcategory').focus().select();
	var availableTags = [ "ActionScript", "AppleScript", "Asp", "BASIC", "C",
			"C++", "Clojure", "COBOL", "ColdFusion", "Erlang", "Fortran",
			"Groovy", "Haskell", "Java", "JavaScript", "Lisp", "Perl", "PHP",
			"Python", "Ruby", "Scala", "Scheme" ];

	$("#inputcategory").autocomplete({
		source : autocompleteCategory,
		select : function(event, ui) {
			$('#inputcategory').val(ui.item.category);
			$('#inputsubcategory').val(ui.item.subcategory);
			return false;
		}
	}).data("autocomplete")._renderItem = function(ul, item) {
//		alert('zz');
		$('<li>').data("item.autocomplete",item).append("<a>" + item.category + " / " + item.subcategory + "</a>").appendTo(ul);
		//ul.append("<li>"+item.category+"</li>");
		//return $('<li>fafa</li>');
//		alert(item);
//		return $('<li></li>').data("item.autocomplete", item).append(
//				"<a>" + item.category + "</a>").appendTo(ul);
	};

	$("#inputsubcategory").autocomplete({
		source : autocompleteSubcategory
	});

	$("#inputname").autocomplete({
		source : "#springUrl('/view/spending_insert')"
	});
});