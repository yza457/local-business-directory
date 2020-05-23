$(function() {
	var loading = false;
	var maxItems = 999; // max number of cards allowed
	var pageSize = 2;
	var listUrl = '/o2o/frontend/listshops';
	var searchDivUrl = '/o2o/frontend/listshopspageinfo';
	var pageNum = 1;
	// get parendId from url query string
	var parentId = getQueryString('parentId');
	// boolean flag for whether a parent category is selected
	var selectedParent = false;
	if (parentId){
		selectedParent = true;
	}
	var areaId = '';
	var shopCategoryId = '';
	var shopName = '';
	// render shop category and area info
	getSearchDivData();
	// load 10 shop information
	addItems(pageSize, pageNum);

	/**
	 * get shop category and area info
	 * 
	 * @returns
	 */
	function getSearchDivData() {
		// if there is a parentId passed in, then get all categories below
		var url = searchDivUrl + '?' + 'parentId=' + parentId;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								// get shopCategoryList from backend
								var shopCategoryList = data.shopCategoryList;
								var html = '';
								html += '<a href="#" class="button" data-category-id=""> All categories  </a>';
								// loop through shop category list and combine a tags
								shopCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-category-id='
													+ item.shopCategoryId
													+ '>'
													+ item.shopCategoryName
													+ '</a>';
										});
								// put shop categories into html
								$('#shoplist-search-div').html(html);
								var selectOptions = '<option value="">All areas</option>';
								// get area list from backend
								var areaList = data.areaList;
								// loop through area list and combine a tags
								areaList.map(function(item, index) {
									selectOptions += '<option value="'
											+ item.areaId + '">'
											+ item.areaName + '</option>';
								});
								// put into html
								$('#area-search').html(selectOptions);
							}
						});
	}

	/**
	 * get shop list info page by page
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(pageSize, pageIndex) {
		// combine search query string into url
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&parentId=' + parentId + '&areaId=' + areaId
				+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		// avoid multiple loading at one time
		loading = true;
		// get shop list from backend
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				// loop through area list and make it a card
				data.shopList.map(function(item, index) {
					html += '' + '<div class="card" data-shop-id="'
							+ item.shopId + '">' + '<div class="card-header">'
							+ item.shopName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.shopImg + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.shopDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ 'last update: '
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '</p>' + '<span>View</span>' + '</div>'
							+ '</div>';
				});
				// put cards into the div
				$('.list-div').append(html);
				// get number of cards shown, including previous loads
				var total = $('.list-div .card').length;
				// stop loading from backend if total count is more than maxItems
				if (total >= maxItems) {
					$('.infinite-scroll-preloader').hide();
                    return;
				} else {
					$('.infinite-scroll-preloader').show();
				}
				// otherwise, increment pageNum and load new shops
				pageNum += 1;
				loading = false;
				// refresh to show the new shops
				$.refreshScroller();
			}
		});
	}

	// add new cards when scrolled down
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return; // do nothing if it's loading already
		addItems(pageSize, pageNum);
	});

	// route to shop detail page
	$('.shop-list').on('click', '.card', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
	});

	// refresh the page on selection of new categories
	$('#shoplist-search-div').on(
			'click',
			'.button',
			function(e) {
				if (parentId && selectedParent) { // pass in a category with parentId
					shopCategoryId = e.target.dataset.categoryId;
					// update the selected category
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						shopCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					// empty shop list and call addItems()
					$('.list-div').empty();
					pageNum = 1; // reset page num
					addItems(pageSize, pageNum);
				} else { // if parentId is null then query top level categories
					parentId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						parentId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}

			});

	// query shop based on new name input
	$('#search').on('change', function(e) {
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	// query shop based on new area selected
	$('#area-search').on('change', function() {
		areaId = $('#area-search').val();
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	// open side bar
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

	// initialize page
	$.init();
});
