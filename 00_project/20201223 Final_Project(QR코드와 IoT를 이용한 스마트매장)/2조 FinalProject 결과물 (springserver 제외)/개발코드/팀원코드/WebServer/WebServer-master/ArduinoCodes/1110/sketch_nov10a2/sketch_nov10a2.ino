void setup() {
  Serial.begin(9600);
}

void loop() {
  Serial.println("Ready");
  if(Serial.available()>0){
    char cmd ="";
    cmd = Serial.read();
    Serial.println(cmd);
    if(cmd =="s"){
      Serial.println("Sensor Start...!!!!");
    }else if(cmd == "t"){
      Serial.println("Sensor Stop...!!!!");
    }
    }
  
  delay(2000);
}
