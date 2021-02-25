import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import peasy.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class PuzzleCube extends PApplet {




PeasyCam cam;

int dim = 3;

Cube[] cube = new Cube[dim * dim * dim];
String info0 = "Space Bar to Randomize";
String info1 = "Buttons: 'f - F' rotate Green  'b - B' rotate Blue";
String info2 = "Buttons: 'u - U' rotate White  'd - D' rotate Yellow";
String info3 = "Buttons: 'l - L' rotate Red  'r - R' rotate Orange";

String[] charMoves= {"f", "b", "u", "d", "l", "r"};
String sequence = "";
int counter = 0;

boolean started = false;


public void setup()
{
  
  
  cam = new PeasyCam(this, 0, 0, 0, 500);
  cam.setMinimumDistance(250);
  cam.setMaximumDistance(2000);
  

 
  
  int index = 0;
  for (int x = -1; x <= 1; x++) {
    for (int y = -1; y <= 1; y++) {
      for (int z = -1; z <= 1; z++) {
        PMatrix3D matrix = new PMatrix3D();
        matrix.translate(x, y, z);
        cube[index] = new Cube( matrix, x, y, z);
        index++;
        
      }
    }
    

   
  }


  for (int i = 0; i < 100; i++){
    int r = PApplet.parseInt(random(charMoves.length));
    if (random(1) < 0.3f) {
    sequence += charMoves[r];
    } else {
      sequence += charMoves[r].toUpperCase();
    
    }
  }

}


/*
String flipCase(char c) { 
  String s = "" + c;
  if(s.equals(s.toLowerCase())){
    return s.toUpperCase();
  }else {
   return s.toLowerCase();
    
  }
}
*/


public void turnX(int index, int dir){
  for (int i = 0; i < cube.length; i++){
    Cube qb = cube[i];
    if (qb.x == index){
      PMatrix2D matrix = new PMatrix2D();
      matrix.rotate(dir*HALF_PI);
      matrix.translate(qb.y, qb.z);
      qb.update(qb.x, round(matrix.m02), round(matrix.m12));
      qb.turnFacesX(dir);
    }
    
  }
  
}

public void turnY(int index, int dir){
  for (int i = 0; i < cube.length; i++){
    Cube qb = cube[i];
    if (qb.y == index){
      PMatrix2D matrix = new PMatrix2D();
      matrix.rotate(dir*HALF_PI);
      matrix.translate(qb.x, qb.z);
      qb.update(round(matrix.m02),qb.y, round(matrix.m12));
      qb.turnFacesY(dir);
    }
    
  }
  
}

public void turnZ(int index, int dir){
  for (int i = 0; i < cube.length; i++){
    Cube qb = cube[i];
    if (qb.z == index){
      PMatrix2D matrix = new PMatrix2D();
      matrix.rotate(dir*HALF_PI);
      matrix.translate(qb.x, qb.y);
      qb.update(round(matrix.m02), round(matrix.m12), round(qb.z));
      qb.turnFacesZ(dir);
    }
    
  }
  
}



public void draw()
{
  background(50);

  if(started){
    if( frameCount % 1 == 0){
      if (counter < sequence.length()){
        char move = sequence.charAt(counter);
        applyMove(move);
        counter++;
  
      } 
    }
   
 }

 


  scale(50);
    for (int i = 0; i < cube.length; i++) {
        cube[i].show();
        
      }
   
  cam.beginHUD();
  
  fill(105,105,105);
  rect(750, 1325, 1500, 350);
  fill(255,255,255);
  textSize(40);
  textAlign(CENTER, CENTER);
  text(info0, 750, 1250);
  text(info1, 750, 1300);
  text(info2, 750, 1350);
  text(info3, 750, 1400);
  
  cam.endHUD();

}
public void keyPressed(){
  if (key == ' ') {
    started = true; 
  }
  applyMove(key); 
}

public void applyMove(char move) {
  switch (move) {
  case 'f': 
    turnZ(1, 1);
    break;
  case 'F': 
    turnZ(1, -1);
    break;  
  case 'b': 
    turnZ(-1, 1);
    break;
  case 'B': 
    turnZ(-1, -1);
    break;
  case 'u': 
    turnY(1, 1);
    break;
  case 'U': 
    turnY(1, -1);
    break;
  case 'd': 
    turnY(-1, 1);
    break;
  case 'D': 
    turnY(-1, -1);
    break;
  case 'l': 
    turnX(-1, 1);
    break;
  case 'L': 
    turnX(-1, -1);
    break;
  case 'r': 
    turnX(1, 1);
    break;
  case 'R': 
    turnX(1, -1);
    break;
  }
}
class Cube {
  PMatrix3D matrix;
  int x =0;
  int y =0;
  int z =0;
  int c;
  Face[] faces = new Face[6];
  
  Cube(PMatrix3D m, int x, int y, int z) {
    this.matrix = m;
    this.x = x;
    this.y = y;
    this.z = z;
    c = color(255);
    
    faces[0] = new Face(new PVector(0,0,-1), color(0,0,255));//blue
    faces[1] = new Face(new PVector(0,0,1), color(0,255,0));//green
    faces[2] = new Face(new PVector(0,1,0), color(255,255,255));//
    faces[3] = new Face(new PVector(0,-1,0), color(255,255,0));
    faces[4] = new Face(new PVector(1,0,0), color(255,150,0));
    faces[5] = new Face(new PVector(-1,0,0), color(255,0,0));

  }
  
  public void turnFacesZ(int dir){
    for (Face f: faces){
      f.turnZ(dir*HALF_PI);
    }
    
  }
  
   public void turnFacesY(int dir){
    for (Face f: faces){
      f.turnY(dir*HALF_PI);
    }
    
  }
   public void turnFacesX(int dir){
    for (Face f: faces){
      f.turnX(dir*HALF_PI);
    }
    
  }
  
  public void update(int x, int y, int z) { 
   matrix.reset();
   matrix.translate(x, y, z);
    this.x = x;
    this.y = y;
    this.z = z;
    
  }
  
  public void show(){
   
    //fill(c); 
    noFill();
    stroke(0);
    strokeWeight(0.1f);
    pushMatrix();
    applyMatrix(matrix);
    box(1);
    for (Face f: faces){
     f.show(); 
    }
    popMatrix();
  }
  
}
class Face{
  PVector normal;
  int c;
  
  Face (PVector normal, int c) {
   this.normal = normal;
   this.c = c;
    
  }
  
  public void turnZ(float angle){
    PVector v2 = new PVector();
    v2.x = round(normal.x * cos(angle) - normal.y * sin(angle));
    v2.y = round(normal.x * sin(angle) + normal.y * cos(angle));
    v2.z = round(normal.z);
    normal = v2;
  }
  
  public void turnY(float angle){
    PVector v2 = new PVector();
    v2.x = round(normal.x * cos(angle) - normal.z * sin(angle));
    v2.z = round(normal.x * sin(angle) + normal.z * cos(angle));
    v2.y = round(normal.y);
    normal = v2;    
    
  }
  
  
  public void turnX(float angle){
    PVector v2 = new PVector();
    v2.y = round(normal.y * cos(angle) - normal.z * sin(angle));
    v2.z = round(normal.y * sin(angle) + normal.z * cos(angle));
    v2.x = round(normal.x);
    normal = v2;    
    
  }
  
  public void show(){
    pushMatrix();
    fill(c);
    noStroke();
    rectMode(CENTER);
    translate(0.5f*normal.x, 0.5f*normal.y, 0.5f*normal.z);
    if (abs(normal.x) > 0){
     rotateY(HALF_PI); 
    } else if(abs(normal.y) > 0) {
     rotateX(HALF_PI); 
    }
    
   
    square(0,0,1);
    popMatrix();
  }
}
  public void settings() {  size(1500, 1500, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PuzzleCube" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
