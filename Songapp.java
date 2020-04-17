/SongApp.java

public class SongApp {

private Song[] items; // keep Songs in an unsorted array

private int numberOfItems; // number of Songs in the array





/*

* Default constructor creates array with capacity for 5 Songs

*/

SongApp () {

// ADD YOUR CODE HERE

items = new Song[5];

numberOfItems = 0;

}



/*

* One argument constructor creates array with user defines capacity

*

* @param capacity defines the capacity of the Song library

*/

SongApp (int capacity) {

// ADD YOUR CODE HERE

items = new Song[capacity];

numberOfItems = 0;

}

/*


/*

* Add a Song into the library (unsorted array)

*

* If the library is full (there is not enough space to add another Song)

* - create a new array with double the size of the current array.

* - copy all current Songs into new array

* - add new Song

*

* @param m The Song to be added to the libary

*/

public void addSong (Song m) {

// ADD YOUR CODE HERE
if(numberOfItems < items.length){
items[numberOfItems] = m;
this.numberOfItems++;
}
else{
Song[] newList = new Song[items.length * 2];
int i;
for (i=0; i<this.numberOfItems; i++) {
  if(items[i] != null){
newList[i] = items[i];
}
}
newList[i] = m;
this.numberOfItems++;
items = newList;
}
}



/*

* Removes a Song from the library. Returns true if Song is removed, false

* otherwise.

* The array should not become sparse after a remove, that is, all Songs

* should be in consecutive indexes in the array.

*

* @param m The Song to be removed

*

*/

public boolean removeSong (Song m) {
for (int i=0; i<this.numberOfItems; i++){
  if(items[i].equals(m)){
    for(int j = i; j < this.numberOfItems-1;j++){
      items[j]=items[j+1];
    }
    items[this.numberOfItems-1] = null;
    this.numberOfItems--;
    return true;
  }
}

return false;
}



/*

* Returns the library of songs

*

* @return The array of Songs

*/

public Song[] getSongs () {

// ADD YOUR CODE HERE

return items;

}



/*

* Returns the current number of Songs in the library

*

* @return The number of items in the array

*/

public int getNumberOfItems () {


return this.numberOfItems;

}



/*

* Update the rating of Song @m to @rating

*

* @param @m The Song to have its ratings updated

* @param @rating The new rating of @m

* @return true if update is successful, false otherwise

*

*/

public boolean updateRating (Song m, int rating) {

for(int i=0; i<this.numberOfItems; i++){

if( items[i].getName() == m.getName() ){

items[i].setRating(rating);

return true;

}

}

return false;

}



/*

* Prints all Songs

*/

public void print () {

// ADD YOUR CODE HERE

System.out.println(" Name - Year - Rating - Writers ");

for(int i=0; i < this.numberOfItems; i++){

System.out.println("Song " + (i+1) + " " + items[i].toString());

}

}



/*

* Return all the Songs by @songwriter. The array size should be the

* number of Songs by @songwriter.

*

* @param songwriter The songwriter's name

* @param An array of all the Songs by @songwriter

*

*/

public Song[] getSongsBySongwriter (String songwriter) {

Song[] songList;

int count = 0;
for(int i=0; i<this.numberOfItems; i++){
for (int j=0; j < items[i].getNumberOfWriters(); j++ ) {
if( items[i].getWriterAtIndex(j) == songwriter){
count++;
break;
}
}
}
songList = new Song[count];
for(int i=0, k=0 ; i<this.numberOfItems; i++){
for (int j=0 ; j < items[i].getNumberOfWriters(); j++ ) {
if( items[i].getWriterAtIndex(j) == songwriter){
songList[k] = items[i];
k++;
break;
}
}
}
return songList;
}



/*

* Return all the Songs released in @year. The array size should be the

* number of Songs made in @year.

*

* @param year The year the Songs were made

* @return An array of all the Songs made in @year

*

*/

public Song[] getSongsByYear (int year) {

// ADD YOUR CODE HERE

Song[] songList;
int count = 0;
for(int i=0; i<this.numberOfItems; i++){
if( items[i].getYear() == year){
count++;
}
}
songList = new Song[count];
for(int i=0, j=0; i<this.numberOfItems; i++){
if( items[i].getYear() == year ){
songList[j] = items[i];
j++;

}
}

return songList;

}



/*

* Return all the Songs with ratings greater then @rating

*

* @param rating

* @return An array of all Songs with rating greater than @rating

*

*/

public Song[] getSongsWithRatingsGreaterThan (int rating) {

// ADD YOUR CODE HERE

Song[] songList;
int count = 0;
for(int i=0; i<this.numberOfItems; i++){
if( items[i].getRating() > rating){
count++;
}

}
songList = new Song[count];
for(int i=0, j=0; i<this.numberOfItems; i++){
if( items[i].getRating() > rating ){
songList[j] = items[i];
j++;
}
}
return songList;

}



/*

* Search for Song name @name using binary search algorithm.

* Assumes items are sorted

*/

public Song searchSongByName (String name) {

// ADD YOUR CODE HERE

int l = 0;
int r = this.numberOfItems - 1;
while (l <= r){
int m = l + (r-l)/2;
// Check if name is present at mid
if (items[m].getName() == name)
return items[m];
// If name greater, ignore left half
if (items[m].getName().compareTo(name) < 0)
l = m + 1;
// If name is smaller, ignore right half
else
r = m - 1;
}
return null;

}



/*

* Sorts Songs by year using insertion sort

*/

public void sortByYear () {

// ADD YOUR CODE HERE

int n = items.length;

for (int i=1; i<n; ++i){

Song key = items[i];

int j = i-1;



/* Move elements of arr[0..i-1], that are

greater than key, to one position ahead

of their current position */

while (j>=0 && items[j].getYear() > key.getYear()){

items[j+1] = items[j];

j = j-1;

}

items[j+1] = key;

}

}



/*

* Sorts array of Songs by name using selection sort

*/

public void sortByName () {

// ADD YOUR CODE HERE

int n = items.length;
// One by one move boundary of unsorted subarray

for (int i = 0; i < n-1; i++){

// Find the minimum element in unsorted array
int min_index = i;
for (int j = i+1; j < n; j++)
if (items[j].compareTo(items[min_index]) < 0) // s1 < s2
min_index = j;

// Swap the found minimum element with the first element

Song temp = items[min_index];
items[min_index] = items[i];
items[i] = temp;
}
}



/*

* Search for Song name using recursive linear search

* @param name The name of the song to search

* @param Songs The array containing all songs

* @param l The left index

* @param r The right index

* @return The song with name @name or null if song is not found

*/

public static Song searchSongByName (String name, Song[] Songs, int l, int r) {

if (r < l)

return null;

if (Songs[l].getName() == name)

return Songs[l];

if (Songs[r].getName() == name)

return Songs[r];

return searchSongByName(name,Songs, l+1, r-1);

}

}
