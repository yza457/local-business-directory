$(function() {
	// the url to manage list of products
	var listUrl = '/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
	// the url to change product status
	var statusUrl = '/o2o/shopadmin/modifyproduct';
	getList();

	function getList() {
		// get list of products from backend
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				var tempHtml = '';
				// add possible actions to each product: list / unlist, modify and preview
				// all actions include productId
				productList.map(function(item, index) {
					var textOp = "unlist";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						// list the previously unlisted product to the shop
						textOp = "list";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					// make the row for each product
					tempHtml += '' + '<div class="row row-product">'
							+ '<div class="col-33">'
							+ item.productName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.point
							+ '</div>'
							+ '<div class="col-40">'
							+ '<a href="#" class="edit" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">Modify</a>'
							+ '<a href="#" class="status" data-id="'
							+ item.productId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">Preview</a>'
							+ '</div>'
							+ '</div>';
				});
				// put the row item into html
				$('.product-wrap').html(tempHtml);
			}
		});
	}
	// bind routes to the actions in row item
	$('.product-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							window.location.href = '/o2o/shopadmin/productoperation?productId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('status')) {
							changeItemStatus(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							window.location.href = '/o2o/frontend/productdetail?productId='
									+ e.currentTarget.dataset.id;
						}
					});

	function changeItemStatus(id, enableStatus) {
		// product object includes productId and enableStatus
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('Confirm?', function() {
			// list / unlist the product
			$.ajax({
				url : statusUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('operation success');
						getList();
					} else {
						$.toast('operation failed');
					}
				}
			});
		});
	}
});