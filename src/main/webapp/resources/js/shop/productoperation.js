$(function() {
	// get productId from url
	var productId = getQueryString('productId');
	// then use productId to get the url for that product
	var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
	// the url to get the product category set by the current shop
	var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
	// the url for updating the product
	var productPostUrl = '/o2o/shopadmin/modifyproduct';
	// flag to show if this is adding or editing product
	var isEdit = false;
	if (productId) {
		// if there is a productId then it is editing
		getInfo(productId);
		isEdit = true;
	} else {
		getCategory();
		productPostUrl = '/o2o/shopadmin/addproduct';
	}

	// get product information to be edited and put info in the form
	function getInfo(id) {
		$
				.getJSON(
						infoUrl,
						function(data) {
							if (data.success) {
								// extract product object from JSON and give the info to form
								var product = data.product;
								$('#product-name').val(product.productName);
								$('#product-desc').val(product.productDesc);
								$('#priority').val(product.priority);
								$('#point').val(product.point);
								$('#normal-price').val(product.normalPrice);
								$('#promotion-price').val(
										product.promotionPrice);
								// get the product category for this product
								// and all other categories set by the shop
								var optionHtml = '';
								var optionArr = data.productCategoryList;
								var optionSelected = product.productCategory.productCategoryId;
								// generate the list of product categories and select current category by default
								optionArr
										.map(function(item, index) {
											var isSelect = optionSelected === item.productCategoryId ? 'selected'
													: '';
											optionHtml += '<option data-value="'
													+ item.productCategoryId
													+ '"'
													+ isSelect
													+ '>'
													+ item.productCategoryName
													+ '</option>';
										});
								$('#category').html(optionHtml);
							}
						});
	}

	// retrieve all product category under current shop when adding a shop
	function getCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				var productCategoryList = data.data;
				var optionHtml = '';
				productCategoryList.map(function(item, index) {
					optionHtml += '<option data-value="'
							+ item.productCategoryId + '">'
							+ item.productCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}

	// generate a new upload control when total count of detail images is less than six
	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

	// different response for adding and editing a product
	$('#submit').click(
			function() {
				// create a json object and get the attributes from the form
				var product = {};
				product.productName = $('#product-name').val();
				product.productDesc = $('#product-desc').val();
				product.priority = $('#priority').val();
				product.point = $('#point').val();
				product.normalPrice = $('#normal-price').val();
				product.promotionPrice = $('#promotion-price').val();
				// get selected product category
				product.productCategory = {
					productCategoryId : $('#category').find('option').not(
							function() {
								return !this.selected;
							}).data('value')
				};
				product.productId = productId;

				// get file stream for thumbnail
				var thumbnail = $('#small-img')[0].files[0];
				// create a new formdata to get attributes and pass to backend
				var formData = new FormData();
				formData.append('thumbnail', thumbnail);
				// iterate the detail images and obtain the file stream
				$('.detail-img').map(
						function(index, item) {
							// check if a file is selected
							if ($('.detail-img')[index].files.length > 0) {
								// set the ith file stream to the value for key = productImgi
								formData.append('productImg' + index,
										$('.detail-img')[index].files[0]);
							}
						});
				// transfer json object to a string, make it the value for key productStr
				formData.append('productStr', JSON.stringify(product));
				// get the captcha input
				var verifyCodeActual = $('#j_captcha').val();
				if (!verifyCodeActual) {
					$.toast('Please enter captcha');
					return;
				}
				formData.append("verifyCodeActual", verifyCodeActual);
				// send data to backend
				$.ajax({
					url : productPostUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('submit success');
							$('#captcha_img').click();
						} else {
							$.toast('submit failed');
							$('#captcha_img').click();
						}
					}
				});
			});

});