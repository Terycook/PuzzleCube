import peasy.*;

// sequence diagram on the eposter, and an bastract
PeasyCam cam;

int dim = 3;

Cube[] cube = new Cube[dim * dim * dim];
String info = "Buttons: ";
String info1 = " 'f - F' rotate Green  'b - B' rotate Blue";
String info2 = " 'u - U' rotate White  'd - D' rotate Yellow";
String info3 = " 'l - L' rotate Red  'r - R' rotate Orange";

String[] allMoves= {"f", "b", "u", "d", "l", "r"};
String sequence = "";
int counter = 0;

boolean started = false;


void setup()
{
  size(1500, 1500, P3D);
  
  cam = new PeasyCam(this, 0, 0, 0, 500);
  cam.setMinimumDistance(275);
  cam.setMaximumDistance(1000);
  

 
  
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


  for (int i = 0; i < 50; i++){
    int r = int(random(allMoves.length));
    if (random(1) < 0.5) {
    sequence += allMoves[r];
    } else {
      sequence += allMoves[r].toUpperCase();
    
    }
  }
  for ( int i = sequence.length()-1; i >= 0; i--) {
     String nextMove = flipCase(sequence.charAt(i));
     sequence += nextMove;
  }
}



String flipCase(char c) { 
  String s = "" + c;
  if(s.equals(s.toLowerCase())){
    return s.toUpperCase();
  }else {
   return s.toLowerCase();
    
  }
}


void turnX(int index, int dir){
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

void turnY(int index, int dir){
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

void turnZ(int index, int dir){
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



void draw()
{
  background(30);

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
  rect(750, 1375, 1500, 350);
  fill(255,255,255);
  textSize(40);
  textAlign(CENTER, CENTER);
  text(info, 750, 1250);
  text(info1, 750, 1300);
  text(info2, 750, 1350);
  text(info3, 750, 1400);
  
  cam.endHUD();

}
