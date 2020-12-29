#define INPUT_SIZE 30
const int ledWPin = 11;
const int sensorPin = A1;
const int ledRPin = A0;
const int ledBPin = A2;

// variables:
int sensorValue = 0;         // the sensor value
int sensorMin = 0;        // minimum sensor value
int sensorMax = 0;           // maximum sensor value

// LED power
int powerRLed = 0;
int powerBLed = 0;
int powerWLED = 0;

void setup() {
  Serial.begin(9600);
  pinMode(ledWPin, OUTPUT); // Represent indoor light
  pinMode(ledRPin, OUTPUT); // Represent heating
  pinMode(ledBPin, OUTPUT); // Represent cooling

  /*Calibration for Ambient sensor*/
  // turn on LED to signal the start of the calibration period:
  pinMode(13, OUTPUT);
  digitalWrite(13, HIGH);

  // calibrate during the first five seconds
  while (millis() < 10000) {
    sensorValue = analogRead(sensorPin);
    //Make an Inverse proportion relation. 
    // The Smaller the signal is, the brighter the light is.
    if (sensorValue <0){
      sensorValue = 1;
    }
    sensorValue = 1000/sensorValue;
    // record the maximum sensor value
    if (sensorValue > sensorMax) {
      sensorMax = sensorValue;
    }

    // record the minimum sensor value
    if (sensorValue < sensorMin) {
      sensorMin = sensorValue;
    }
  }

  // signal the end of the calibration period
  digitalWrite(13, LOW);
  /*Calibration Finisehd*/

}
//input = 1:90&2:80&3:180
// parse into servoId : Position & servoId : Position & servoId : Position
void loop() {
  char input[INPUT_SIZE + 1];
  if(Serial.available() > 0){
    byte size = Serial.readBytes(input, INPUT_SIZE); // size of input
    input[size] = 0;
    Serial.println(size); 
    char* command = strtok(input, "&"); //split into substring
    Serial.println(command);
    while (command != 0)
    {
        // Split the command in two values
        char* separator = strchr(command, ':'); //search character
        Serial.println(separator);
        if (separator != 0)
        {
            // Actually split the string in 2: replace ':' with 0
            *separator = 0;
            int sensorId = atoi(command); // String to int
            ++separator;
            double value = atoi(separator);
    
            // Do something with sensorId and value
            //1 = Door open, 2=Heating, 3=Cooling, 4=Lighting
            if (sensorId == 1){
              Serial.println("Door");
            }else if(sensorId == 2){
              Serial.println("Heating");
              powerRLed = value;
            }else if(sensorId == 3){
              Serial.println("Cooling");
              powerBLed = value;
            }else if(sensorId == 4){
              Serial.println("Lighting");
              
            }else if(sensorId ==0){
              Serial.println("Ready");
            }
        }
        // Find the next command in input string
        command = strtok(0, "&");
    }
 
    
  }// end if
    //Handle LED power
    digitalWrite( ledRPin, HIGH );
    delay( powerRLed );
    digitalWrite( ledRPin, LOW );
    delay( 10-powerRLed );

    //Handle LED power
    digitalWrite( ledBPin, HIGH );
    delay( powerBLed );
    digitalWrite( ledBPin, LOW );
    delay( 10-powerBLed );
}
