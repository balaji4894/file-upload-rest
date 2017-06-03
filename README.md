# file-upload-rest

### Description
This is a maven-web app that uses spring and apache commons fileupload to demostrate filuploads using angularjs.
  1. There are two POST rest end points one for single file upload and other for multiple
  2. The index page of the project, shows an angularJs form to upload from browser (watch the console logs for results)
  3. These fileupload endpoints can be called from postman also.

### Cloning and Running

Clone the project from your Eclipse Import menu for git Projects. (Preferably use JDK 1.8)
Run as any other maven web archetype project in eclipse.

### Using rest Endpoints in Postman

###### Single file Upload
1. URL : http://localhost:8080/file-upload-rest/uploadFile
2. RequestType : POST
3. RequestBody : form-data
4. param1 : type = String : Key = "filename" : value = <yourvalue> 
5. param2 : type = File : Choose your file
