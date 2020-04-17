import java.util.Arrays;
public class Song{

//all of the variables declared
private String name;
private int year;
private int numberOfWriters;
private String[] writers=new String[50];
private int rating;

//Constructor
Song(String name)
{
this.name=name;
numberOfWriters=0;
}

public void setName(String name)
{
this.name=name;
}

public String getName()
{
return name;
}

public void setYear(int year){
  this.year=year;
}

public int getYear()
{
return year;
}
//setter for rating
public void setRating(int rating)
{
this.rating=rating;
}
//getter for rating
public int getRating()
{
return rating;
}
//addWriter adds the writer
public void addWriter(String writerName)
{
writers[numberOfWriters]=writerName;
numberOfWriters=numberOfWriters+1;
}
//returns the writers list in array
public String[] getWriters(){
return writers;
}
//gets number of writers
public int getNumberOfWriters(){
return numberOfWriters;
}
//returns the writer at particular index
public String getWriterAtIndex(int index){
  if(index<0){
    return null;
  }else if (writers[index] == null || writers[index] == ""){
    return null;
  }else{
return writers[index];
}
}

public String toString(){
return name+","+year+","+rating;
}


public boolean equals(Object other){
  int count = 0;
  if(other instanceof Song){
    Song song = (Song) other;
    if(!(this.getName().equals(song.getName()))){
        return false;
  }else if (this.getYear() != song.getYear()){
  return false;
  }else{
  if(this.getNumberOfWriters() != song.getNumberOfWriters()){
    return false;
  }else{
    for(int i = 0; i<this.getNumberOfWriters(); i++){
      for(int j = 0; j< song.getNumberOfWriters(); j++){
        if((this.getWriterAtIndex(i).equals(song.getWriterAtIndex(j)))){
          count++;
        }
      }
    }
    if(this.getNumberOfWriters() != count){
      return false;
    }else{
      return true;
    }
  }
}
}else{
  return false;
}
}



public int compareTo (Song other){
  if(this.getName().compareTo(other.getName()) == 0){
    return 0;
  }else if (this.getName().compareTo(other.getName()) > 0){
    return 1;
  }else{
    return -1;
  }
}
}
