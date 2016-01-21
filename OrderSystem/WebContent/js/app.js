var orderSystem = angular.module('orderSystem',['ngRoute']);

orderSystem.config(['$routeProvider','$locationProvider',function ($routeProvider,$locationProvider) {
      $routeProvider
      .when('/',{
        templateUrl: './view/welcome.html',
        controller: 'welcomecontr',
        title: "welcome | order system"
      })
      .when('/search/',{
        templateUrl: './view/orderSummary.html',
        controller: 'ordersummarycontr',
        title: "order summary | order system"
      })
      .when('/detail/:id',{
        templateUrl: './view/orderDetails.html',
        controller: 'orderdetailscontr',
        title: "order details | order system"
      })
      .otherwise({
    	  redirectTo: '/'
      });
}]);

orderSystem.run(['$rootScope', '$route', function($rootScope, $route) {
    $rootScope.$on('$routeChangeSuccess', function() {
        document.title = $route.current.title;
    });
}]);



