<!DOCTYPE html>
<html lang="en">
<head>
<title>ASTRA Demo - Product list</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script
  src="https://code.jquery.com/jquery-3.5.1.js"
  integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
  crossorigin="anonymous">
</script>
<script>

$(document).ready(function(){
    $('.bnt-delete-product').click(function() {
    
   		var productName = $(this).val();
   		
        $.ajax({
            type: "DELETE",
            url: '/api/v1/product/' + productName,
            success: function (data) {
                top.location.href = '/api/v1/products';
            },
            error: function (data) {
                console.log('Error:', data);
            }   		
    	});
   	});
});
    
</script>
<style>
.container {
	margin-top: 80px;
}
.bg-dark {
	background-color: #3b8dbd !important;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-xl navbar-dark bg-primary fixed-top">
		<a class="navbar-brand mb-0 h4" href="https://www.datastax.com/">ASTRA Demo - Product list</a>
		
	</nav>
	<div class="container">

		<form class="form-inline" method="post" action="/api/v1/product">
		
			<input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0"
				id="name" name="name" placeholder="Name" />
				
			<input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0"
				id="description" name="description" placeholder="Description" />	
				
			<input type="float" class="form-control mb-2 mr-sm-2 mb-sm-0"
				id="price" name="price" placeholder="price" />										
	
		  <button type="submit" class="btn btn-primary">Add</button>
		</form>
	
		<table class="table">
			  <thead>
			    <tr>
			      <th>Id</th>
			      <th>Product name</th>
			      <th>Description</th>
			      <th>Price</th>
			      <th>Creation date</th>	      
			      <th></th>
			    </tr>
			  </thead>
			  <tbody>
			    <#list model["products"] as product>
				    <tr>
				      <th scope="row">${product.id}</th>
				      <td>${product.name}</td>
				      <td>${product.description}</td>
				      <td>${product.price}</td>
				      <td>${product.created?string("yyyy.MM.dd")}</td>	      
				      <td>
                        <button class="btn btn-sm btn-danger bnt-delete-product" 
                        value="${product.name}">Delete</button>
				      </td>
				    </tr>
			    </#list>
			  </tbody>
		</table>	

	</div>
</body>
</html>