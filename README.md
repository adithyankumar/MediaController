# MediaController
MediaController give two different user access control for the files like **Super User** and **Normal User**.
MediaController helps user to fetch, display, add and delete differnt media file **( Image, Video, Audio and Document )** from internal and external memory to app. 
**Super User** can fetch , display, add and delete file in our app.
**Normal User** can fetch and display file in our app.
Also provides built in view to perform the fuctionalities with basic UI.
    
# Download 
You can download project from Github's [release page](https://github.com/adithyankumar/MediaController/releases/tag/1.0.0-beta)
    
or use Gradle :
    
Add it in your root build.gradle at the end of repositories:
    
```
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}
     
```
    
Add the dependency
    
```
dependencies {
    implementation 'com.github.adithyankumar:MediaController:1.0.0-beta'
    }
```
    
or Maven :
    	
Add the JitPack repository to your build file
    	
    ```
    <repositories>
    		<repository>
    		    <id>jitpack.io</id>
    		    <url>https://jitpack.io</url>
    		</repository>
    </repositories>
    
    ```		
Add the dependency
    	
    ```
       <dependency>
       	    <groupId>com.github.adithyankumar</groupId>
       	    <artifactId>MediaController</artifactId>
       	    <version>1.0.0-beta</version>
       	</dependency>
    ```
    
# How to use MediaController ?
    
MediaController gives two different user access controller 
     
**Super User:**
    To use Super user functionality , use the below code 
        
    ```
        // create instance of SuperUserManager
        SuperUserManager superUserManager = SuperUserManager.getInstance(this);
        
        // Create media list of video files to be added 
         List<Media> mediaListToAdd = new ArrayList<>();
         Media media = new Media(context, uriString , MediaTypeEnum.IMAGE.toString());
         mediaListToAdd.add(media);
         superUserManager.addImageList(mediaListToAdd, new ImageAddListener() {
         
            @Override
            public void onImageAddSuccess(List<Media> mediaList, String message) {
        
            }
        
            @Override
            public void onImageAddFailure(String errorMessage) {
        
            }
         });
        
         // Create media list of image files to be deleted 
         List<Media> mediaListToDelete= new ArrayList<>();
         Media media = new Media(context, uriString , MediaTypeEnum.IMAGE.toString());
         mediaListToDelete.add(media);
         superUserManager.deleteImageList(mediaListToDelete, new ImageDeleteListener() {
            @Override
            public void onImageDeleteSuccess(List<Media> mediaList, String message) {
        
            }
        
            @Override
            public void onImageDeleteFailure(String errorMessage) {
        
            }
          });
        
          // to fetch image files
          superUserManager.getImageList(new ImageFetchListener() {
            @Override
            public void onImageFetchSuccess(List<Media> imageList) {
        
            }
        
            @Override
            public void onImageFetchFailure(String errorMessage) {
        
            }
          });
        
    ```
        
**Normal User :**
    To use Normal user functionality , use the below code     
        
```     
    // create instance of NormalUserManager
                NormalUserManager userManager = NormalUserManager.getInstance(this);
                //fetch image file
                userManager.getImageList(new ImageFetchListener() {
                    @Override
                    public void onImageFetchSuccess(List<Media> imageList) {
        
                    }
        
                    @Override
                    public void onImageFetchFailure(String errorMessage) {
        
                    }
                });
                //fetch audio file
                userManager.getAudioList(new AudioFetchListener() {
                    @Override
                    public void onAudioFetchSuccess(List<Media> imageList) {
        
                    }
        
                    @Override
                    public void onAudioFetchFailure(String errorMessage) {
        
                    }
                });
                //fetch video file
                userManager.getVideoList(new VideoFetchListener() {
                    @Override
                    public void onVideoFetchSuccess(List<Media> imageList) {
        
                    }
        
                    @Override
                    public void onVideoFetchFailure(String errorMessage) {
        
                    }
                });
                //fetch doc file
                userManager.getDocList(new DocFetchListener() {
                    @Override
                    public void onDocFetchSuccess(List<Media> imageList) {
        
                    }
        
                    @Override
                    public void onDocFetchFailure(String errorMessage) {
        
                    }
                });
         
         
```
         
         
**Integrate default fragment 

 In your MainActivity , inside your onCreate add below code

   
`
   //pass isSuperUser - true/false based on user access control
   //pass Media type ->  MediaTypeEnum.IMAGE / MediaTypeEnum.VIDEO /MediaTypeEnum.AUDIO /MediaTypeEnum.DOC
    getSupportFragmentManager()
    .beginTransaction()
    .add(R.id.container, MediaFragment.newInstance(isSuperUser, MediaTypeEnum.IMAGE))
    .commit();
`
 Also override onActivityResult in MainActivity and below code inside
 
`
    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            for (Fragment fragment : getSupportFragmentManager().getFragments()){
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
`
Also can customize theme style by adding in your colors.xml . Can your favourite colors for **PrimaryColor** ,
**PrimaryColorDark** and **ColorAccent**
 
`
<resources>
    <color name="colorPrimary">#e9b53f57</color>
    <color name="colorPrimaryDark">#b53f47</color>
    <color name="colorAccent">#40a9ff</color>
</resources>

` 

