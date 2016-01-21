	var firstname = [];
	var lastname = []; 
    var firstnameSet = $("#firstname").kendoAutoComplete({
        dataSource: firstname,
        placeholder: "First Name",
        dataTextField: "name",
    });
    firstnameSet.css("width","30%");
    var lastnameSet = $("#lastname").kendoAutoComplete({
        dataSource: lastname,
        placeholder: "Last Name",
        dataTextField: "name",
    });
    lastnameSet.css("width","30%");
    $("#firstname, #lastname").keyup(function(e){
    	var keyCode = e.keyCode;
    	if(keyCode==37||keyCode==40||keyCode==39||keyCode==38||keyCode==13)
    		return;
		firstname = [];
		lastname = [];
		var dataSet = "";
		var firstorlast = $(this).attr("name");
		if(firstorlast=="firstname"){
			dataSet = "{\"name\":\""+$(this).val()+"\",\"flag\":\"first\",\"nameother\":\""+$("#lastname").val()+"\"}";
		}else{
			dataSet = "{\"name\":\""+$(this).val()+"\",\"flag\":\"last\",\"nameother\":\""+$("#firstname").val()+"\"}";
		}
    	$.ajax({
    		type: "POST",
		    url: "./autoCompleteServlet",
		    dataType: "json",
		    data: dataSet,
		    success: function(res){
		    	var dataSource = new kendo.data.DataSource({
		    		  data: res
		    	});
	    		if(firstorlast=="firstname"){
	    	    	var auto = firstnameSet.data("kendoAutoComplete");
	    	    	auto.setDataSource(dataSource);
	    		}else{
	    	    	var auto = lastnameSet.data("kendoAutoComplete");
	    	    	auto.setDataSource(dataSource);
	    		}
		    }
    	});
    });
    $("#searchbuttonid").click(function(){
    	if($("#firstname").val().trim()==""&&$("#lastname").val().trim()==""){
    		window.kendoAlert = (function() {
    		  var win = $("<div>").kendoWindow({
    		    modal: true 
    		  }).getKendoWindow();
    		  return function(msg) {
    			win.title("Notice:");
    		    win.content(msg);
    		    win.center().open();
    		  };
    		}());
    		kendoAlert("Please type valid first name or last name!");
    		return;
    	}
		var dataSet = "{\"firstname\":\""+$("#firstname").val()+"\",\"lastname\":\""+$("#lastname").val()+"\"}";
		$.ajax({
    		type: "POST",
		    url: "./searchServlet",
		    dataType: "json",
		    data: dataSet,
		    success: function(res){
		    	console.log("service search");
		    	sessionStorage.setItem("temp",JSON.stringify(res));
		    	var url = './#/search';
		    	$(location).attr('href',url);
		    	location.reload(true);
		    }
		});
    });