$(function(){

  chart = JSON.parse(sessionStorage.getItem('chart'));

  switch(sessionStorage.getItem('category'))
  {
    default:
    case "category1":
      data = [
        { productName: "Product 1", price: "1.99" },
        { productName: "Product 2", price: "3.30" }, 
        { productName: "Product 3", price: "4.10" }, 
        { productName: "Product 4", price: "1.50" },
      ];

      sessionStorage.setItem('categoryName', 'Category 1');
      $("#path").append('<a href="category.html"> Category 1 </a>');
      break;
    case "category2":
      data = [
        { productName: "Product 5", price: "3.99" },
        { productName: "Product 6", price: "2.99" }, 
        { productName: "Product 7", price: "1.99" }, 
        { productName: "Product 8", price: "6.00" },
      ];

      sessionStorage.setItem('categoryName', 'Category 2');
      $("#path").append('<a href="category.html"> Category 2 </a>');
      break;
    case "category3":
      data = [
        { productName: "Product 9", price: "2.99" },
        { productName: "Product 10", price: "5.49" }, 
        { productName: "Product 11", price: "1.00" }, 
        { productName: "Product 12", price: "2.90" },
      ];

      sessionStorage.setItem('categoryName', 'Category 3');
      $("#path").append('<a href="category.html"> Category 3 </a>');
      break;
  }

  loadProducts();
  if(chart != undefined && chart.length > 0) loadChart();
  updateChart();

  function loadProducts()
  {
    var html = "<ul>" 
    for(i = 0; i < data.length; i++) {   
      html += '<li class="products"> <ul>' +
      '<li onclick="getProduct(' + i + ')" id="img-"> <img src="../images/dummy.png"> </li>' + 
      '<li onclick="getProduct(' + i + ')" id="name-' + i + '"> <a href="#">' + data[i].productName + '</a></li>' + 
      '<li id="price-' + i + '"> $' + data[i].price + ' <a onclick="addProduct(' + i + ')" href="#"> [Add] </a></li>' + 
      '</ul></li>';
    }
    html += "</ul>"         
    $('#right-content').append(html);
  }

  function loadChart()
  {
    var html = "<ul>";
    for(i = 0; i < chart.length; i++) {   
      html += '<li"> <p> ' + chart[i].productName + ' <input id="input' + i +'" onchange="setQuantity('+ i +')" value="' + chart[i].quantity + '" type="number">   $' + 
      chart[i].price + '</p></li>';
    }
    html += "<li> <button onclick='checkout()' id='checkout'> Checkout </button> </li></ul>";         
    $('#chart').append(html);
  }
});

var data;
var chart; 

function getProduct(i)
{
  sessionStorage.setItem('product', JSON.stringify(data[i]));
  window.location.href = "product.html";
}

function addProduct(i)
{
  if(chart == undefined)
    chart = [{productName: data[i].productName, price: data[i].price, quantity: 1}];
  else
    chart.push({productName: data[i].productName, price: data[i].price, quantity: 1});
  sessionStorage.setItem('chart', JSON.stringify(chart));
  window.location.reload();
}

function setQuantity(i)
{
  chart[i].quantity = document.getElementById("input" + i).value;
  sessionStorage.setItem('chart', JSON.stringify(chart));
  updateChart();
  
}

function updateChart()
{
  var price = 0.0;
  for(i = 0; i < chart.length; i++) { 
    price += chart[i].quantity * parseFloat(chart[i].price);
  }
  document.getElementById("shopping-list-price").innerHTML = "Shopping List (Total:$" + price.toFixed(2) + ")";
}

function checkout()
{
  chart = [];
  sessionStorage.setItem('chart', JSON.stringify(chart));
  alert("Thank for your purchase!");
  window.location.reload();
}

function getCategory(category)
{
	sessionStorage.setItem('category', category);
	window.location.reload();
}