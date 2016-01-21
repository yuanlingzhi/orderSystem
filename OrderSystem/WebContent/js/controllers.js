orderSystem.controller('welcomecontr',function($scope,$routeParams ,$http){
	$scope.text = "wecome | orderSystem";
	var initit = ["Ann","Alex"];
	var dataSet = {"name":"Carine","flag":"first","nameother":"Schmitt"};
	var firstnameSet = $("#firstname").kendoAutoComplete({
	  dataSource: initit,
	  placeholder: "First Name",
	  dataTextField: "name",
	});
	var lastnameSet = $("#lastname").kendoAutoComplete({
	  dataSource: initit,
	  placeholder: "Last Name",
	  dataTextField: "name",
	});
	$http.post("./autoCompleteServlet",dataSet).success(function(res){
		var dataSource = new kendo.data.DataSource({
			  data: res
		});
		var auto1 = firstnameSet.data("kendoAutoComplete");
		auto1.setDataSource(dataSource);
		var auto2 = lastnameSet.data("kendoAutoComplete");
		auto2.setDataSource(dataSource);

	});
	$scope.test = function(){
		console.log("test function");
	}
    $(".searchbutton").kendoButton();
});

orderSystem.controller('ordersummarycontr',function($scope,$routeParams ,$http){
	$scope.text = "summary | orderSystem";
	$scope.message="No Result";
	$scope.showsummary=true;
	console.log("order summary");
	if($scope.data===undefined){
		console.log("undefine data");
		var str=sessionStorage.getItem("temp");
		if(str!="[{}]" && str!=null){
			$scope.message="";
		}
		str = JSON.parse(str);
		$scope.data = str;
	}
	if($scope.message=="No Result"){
		$scope.showsummary = false;
	}
});
orderSystem.controller('orderdetailscontr',function($scope,$routeParams ,$http){
	$scope.text = "detail | orderSystem";
	var id=$routeParams.id;
	$scope.showDetail = true;
	$scope.message = "No Result";
	if(isNaN(id)){
		$scope.warning = "Please use valid order number!";
		$scope.message = "";
		$scope.showDetail = false;
		return;
	}
	$http.get("./ShowOrderDetailServlet?qid="+id).success(function(res){
		if(res.length==1 && res.name == undefined){
			$scope.showDetail = false;
		}else{
			$scope.data = res;
			$scope.message = "";
		}
	});
});
//$scope.nameAutoHint = function(e){
//firstname = [];
//lastname = [];
//var dataSet = "";
//var firstorlast = e.target.name;
//if(firstorlast=="firstname"){
//	dataSet = "{\"name\":\""+$("#firstname").val()+"\",\"flag\":\"first\",\"nameother\":\""+$("#lastname").val()+"\"}";
//}else{
//	dataSet = "{\"name\":\""+$("#lastname").val()+"\",\"flag\":\"last\",\"nameother\":\""+$("#firstname").val()+"\"}";
//}	
//$http.post("./autoCompleteServlet",dataSet).success(function(res){
//	console.log(JSON.stringify(res));
//	var ds = new kendo.data.DataSource({
//		data:res
//	});
//	console.log(ds);
//	if(firstorlast=="firstname"){
////    	var auto = firstnameSet.data("kendoAutoComplete");
////    	console.log(auto);
////		firstnameSet.data("kendoAutoComplete").setDataSource(ds);
//		$('#firstname').attr("ng-keyup","nameAutoHint($event)");
////    	auto.setDataSource(ds);
//	}
////	else{
////    	var auto = lastnameSet.data("kendoAutoComplete");
////    	auto.setDataSource(ds);
////	}
//});
//}
