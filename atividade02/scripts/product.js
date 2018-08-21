$(function(){

  var description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.Fusce feugiat tristique nibh vehicula aliquet. Nunc volutpat varius placerat." +
  "Vivamus pretium congue lobortis. Aenean ultrices tortor et tellus hendrerit ultricies." +
  "Donec a lacus ac leo pretium molestie. Nullam feugiat feugiat maximus." +
  "Etiam quis ante non libero sodales interdum. Vivamus rutrum finibus ornare." +
  "Integer pulvinar faucibus tellus, in dapibus ex molestie ac. Suspendisse rutrum ut massa id molestie." +
  "Nulla ut commodo nisl. Sed aliquam libero in erat semper scelerisque. In eget massa ligula." +
  "Nullam auctor turpis a est dignissim, eu faucibus eros commodo. Fusce et leo erat." +
  "Nulla facilisi. Donec a ex nisl. Fusce ornare augue eu nisi iaculis, sit amet ornare nibh laoreet." + 
  "Mauris ut mi vel purus auctor consequat. Integer in erat venenatis, cursus sapien a, aliquet augue." +
  "Maecenas fermentum erat sit amet venenatis blandit. Aliquam volutpat congue vulputate.";

  var category = sessionStorage.getItem('categoryName');
  product = JSON.parse(sessionStorage.getItem('product'));
  chart = JSON.parse(sessionStorage.getItem('chart'));

  $("#path").append('<a href="category.html" > ' + category +'</a> <a> > ' + product.productName + '</a>');

  loadProduct();
  if(chart != undefined && chart.length > 0) loadChart();
  updateChart();

  function loadProduct()
  {
    var html = "<ul>";
    html += '<li class="product"> <img src="../images/dummy.png"> </li>' + 
    '<li id="description" class="product"> <h3>' + product.productName + '</h3>' +
    '<p> $' + product.price + '<a onclick="addProduct()" href="#"> [Add] </a> </p>' +
    '<p>' + description + '</p>' +
    '</ul>';
    
    html += "</ul>";        
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

var data, chart, product;

function addProduct()
{
  if(chart == undefined)
    chart = [{productName: product.productName, price: product.price, quantity: 1}];
  else
    chart.push({productName: product.productName, price: product.price, quantity: 1});

  sessionStorage.setItem('chart', JSON.stringify(chart));
  updateChart();
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
  window.location.href = "category.html";
}