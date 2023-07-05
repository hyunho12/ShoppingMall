$(document).ready(function() {
	$(".linkMinus").on("click", function(evt) {
		evt.preventDefault();
		decreaseQuantity($(this));
	});

	$(".linkPlus").on("click", function(evt) {
		evt.preventDefault();
		increaseQuantity($(this));
	});

	$(".linkRemove").on("click", function(evt) {
    	evt.preventDefault();
    	removeProduct($(this));
    });
});

function decreaseQuantity(link) {
	productId = link.attr("pid");
	quantityInput = $("#quantity" + productId);
	newQuantity = parseInt(quantityInput.val()) - 1;

	if (newQuantity > 0) {
		quantityInput.val(newQuantity);
		updateQuantity(productId, newQuantity);
	} else {
		showWarningModal('장바구니 최소수량은 1개입니다.');
	}
}

function increaseQuantity(link) {
		productId = link.attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val()) + 1;

		if (newQuantity <= 10) {
			quantityInput.val(newQuantity);
			updateQuantity(productId, newQuantity);
		} else {
			showWarningModal('장바구니 최대수량은 10개입니다.');
		}
}

function updateQuantity(productId, quantity) {
	url = contextPath + "cart/update/" + productId + "/" + quantity;

	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(updatedSubtotal) {
		updateSubtotal(updatedSubtotal, productId);
		updateTotal();

	}).fail(function() {
		showErrorModal("Error while updating product quantity.");
	});
}

function updateSubtotal(updatedSubtotal, productId) {
	formattedSubtotal = $.number(updatedSubtotal, 0);

	$("#subtotal" + productId).text(formattedSubtotal);
}

function updateTotal(){
    total = 0.0;
  	productCount = 0;


	$(".subtotal").each(function(index, element) {
	    productCount++;
		total += parseFloat(element.innerHTML.replaceAll(",", ""));
	});

	if (productCount < 1) {
   		showEmptyShoppingCart();
    }
    else{
        formattedTotal = $.number(total, 2);
        $("#total").text(formattedTotal);
    }
}

function showEmptyShoppingCart() {
	$("#sectionTotal").hide();
	$("#sectionEmptyCartMessage").removeClass("d-none");
}

function removeProduct(link) {
	url = link.attr("href");

	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		rowNumber = link.attr("rowNumber");
		removeProductHTML(rowNumber);
		updateTotal();
		updateCountNumbers();

		showModalDialog("Shopping Cart", response);

	}).fail(function() {
		showErrorModal("Error while removing product.");
	});
}

function removeProductHTML(rowNumber) {
	$("#row" + rowNumber).remove();
	$("#blankLine" + rowNumber).remove();
}

function updateCountNumbers() {
	$(".divCount").each(function(index, element) {
		element.innerHTML = "" + (index + 1);
	});
}

