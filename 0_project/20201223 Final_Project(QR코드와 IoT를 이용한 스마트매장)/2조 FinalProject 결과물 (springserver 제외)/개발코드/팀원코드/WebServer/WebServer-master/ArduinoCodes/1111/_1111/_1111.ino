void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  
}

void loop() {
  // put your main code here, to run repeatedly:
  Serial.println("Ready");
  //if there is input
  if(Serial.available() > 0){
    String cmd = "";
    cmd = Serial.readString();
    Serial.println(cmd);
    if(cmd == "s"){
      Serial.println("Sensor Start...!!!");
    }else if(cmd == "t"){
      Serial.println("Sensor Stop...!!!");
    }
  }
  delay(2000);
}
