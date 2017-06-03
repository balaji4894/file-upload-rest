<html>
<head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.14/angular.min.js"></script>
<script>
var app = angular.module('myApp',[]);
app.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          
          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
 }]);
app.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl, filename){
       var fd = new FormData();
       fd.append('file', file);
       fd.append('filename', filename);	
       $http.post(uploadUrl, fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined}
       })
    
       .success(function(response){
    	   console.log( "File successfully uploaded");
    	   console.log(response);
       })
    
       .error(function(errorresponse){
    	   console.log("File upload failed ");
    	   console.log(errorresponse);
       });
    }
 }]);
app.controller('myAppController',function($scope, $http, fileUpload){
	$scope.uploadFile = function(){
        var file = $scope.myFile;
        
        console.log('file is ' );
        console.dir(file);
        
        var uploadUrl = "/file-upload-rest/uploadFile";
        fileUpload.uploadFileToUrl(file, uploadUrl, $scope.filename);
     };
});
</script>
</head>
<body ng-app="myApp">
<h2>Single File upload angular JS</h2>
<div ng-controller="myAppController">
	<form enctype="multipart/form-data">
		<table>
			<tr style="text-align: center;">
				<h3>Upload File</h3>
			</tr>
			<tr>
				<td>Upload File :</td>
				<input type = "file" file-model = "myFile"/>
			</tr>
			<tr>
				<td>File Name :</td>
				<td><input type="text" name="name" ng-model="filename" ng-required></td>
			</tr>
			<tr>
				<td>upload file</td>
				<td><input type="submit" ng-click="uploadFile()"></td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
