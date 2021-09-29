 String cmd ="";
 String temp;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println("Start");
    
}

void loop() {
  delay(1000);
 
    cmd = Serial.readString();
    if(cmd == ""){
      temp = "s";
    }
 Serial.println("--"+cmd+"--");
    if(temp == "s"){
      Serial.println("START");
    }else{
      Serial.println("STOP");
    }
  
}
