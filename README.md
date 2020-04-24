# Inspiring people Kotlin application with fragments
Basic Kotlin application about inspiring people implemented with Fragment Manager

This application contains two fragments. First fragment has listView, and a second one a form for add/edit inspiring person.
Fragments are added with Fragment Manager, and a data is loaded from PeopleRepository.
Click on image on list item will trigger listener and show random quote connected with person that is clicked. When you long click on "Description" about inspiring person it will show edit form with fields loaded from repository. Short click will trigger alert dialog with question about deleting clicked item. To add a new item to list view, press float action button which will lead to second fragment with form for adding new inspiring person 

Every fragment is added/replaced to xml with supportFragmentManager, and based on number of fragments when click on back button it will go to previous fragment.

## Application images
### First Fragment
<img src="https://user-images.githubusercontent.com/52075105/80262738-b77b2380-868e-11ea-85a7-267294a8940c.png" height="600" width="300">

### Second Fragment - Add
<img src="https://user-images.githubusercontent.com/52075105/80262766-d5488880-868e-11ea-9151-69f8af355ebb.png" height="600" width="300">

### Second Fragment - Edit
<img src="https://user-images.githubusercontent.com/52075105/80262837-fe691900-868e-11ea-86ca-8f479b30a153.png" height="600" width="300">

### Dialog
<img src="https://user-images.githubusercontent.com/52075105/80262801-e5606800-868e-11ea-9d64-a7f08a45ce3d.png" height="600" width="300">
