

const int ledWPin = A2;
const int sensorPin = A1;
const int ledRPin = A0;
const int ledBPin = 11;
#define INPUT_SIZE 30

// variables:
int sensorValue = 0;         // the sensor value
int sensorMin = 0;        // minimum sensor value
int sensorMax = 0;           // maximum sensor value

void setup() {
  Serial.begin(9600);
  pinMode(ledWPin, OUTPUT); // Represent indoor light
  pinMode(ledRPin, OUTPUT); // Represent heating
  pinMode(ledBPin, OUTPUT); // Represent cooling
}

void loop() {
  Serial.println("Ready");
  // if there is input
  if(Serial.available() > 0){
    String cmd = "";
    cmd = Serial.readString();
    if(cmd == "s"){
      digitalWrite(ledWPin, HIGH);
      Serial.println("LED_Turned_on");
    }else if(cmd == "t"){
      digitalWrite(ledWPin, LOW);
      Serial.println("LED_Turned_off");
    }
    
  }
  delay(2000);
}
