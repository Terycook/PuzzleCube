class Cube {
  PMatrix3D matrix;
  int x =0;
  int y =0;
  int z =0;
  color c;
  Face[] faces = new Face[6];
  
  Cube(PMatrix3D m, int x, int y, int z) {
    this.matrix = m;
    this.x = x;
    this.y = y;
    this.z = z;
    c = color(255);
    
    faces[0] = new Face(new PVector(0,1,0), color(255,255,255));//white
    faces[1] = new Face(new PVector(0,-1,0), color(255,255,0));//yellow
    faces[2] = new Face(new PVector(0,0,1), color(0,255,0));//green
    faces[3] = new Face(new PVector(0,0,-1), color(0,0,255));//blue
    faces[4] = new Face(new PVector(1,0,0), color(255,150,0));//orange
    faces[5] = new Face(new PVector(-1,0,0), color(255,0,0));//red

  }
  
  void turnFacesZ(int dir){
    for (Face f: faces){
      f.turnZ(dir*HALF_PI);
    }
    
  }
  
   void turnFacesY(int dir){
    for (Face f: faces){
      f.turnY(dir*HALF_PI);
    }
    
  }
   void turnFacesX(int dir){
    for (Face f: faces){
      f.turnX(dir*HALF_PI);
    }
    
  }
  
  void update(int x, int y, int z) { 
   matrix.reset();
   matrix.translate(x, y, z);
    this.x = x;
    this.y = y;
    this.z = z;
    
  }
  
  void show(){
    noFill();
    stroke(0);
    strokeWeight(0.15);
    pushMatrix();
    applyMatrix(matrix);
    box(1);
    for (Face f: faces){
     f.show(); 
    }
    popMatrix();
  }
  
}
