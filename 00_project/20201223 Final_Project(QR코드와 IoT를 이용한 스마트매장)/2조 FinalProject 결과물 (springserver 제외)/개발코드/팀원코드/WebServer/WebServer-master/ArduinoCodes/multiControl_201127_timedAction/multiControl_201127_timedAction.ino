#include <TimedAction.h>
#define INPUT_SIZE 40
#define deviceCount 4

//define pin number
const int tempPin = A0;
const int ambientPin = A1;
const int ledWPin = A2;
const int ledRPin = 10;
const int ledBPin = 11;

int ambientValue = 0;         // the sensor value
int ambientMin = 0;        // minimum sensor value
int ambientMax = 0;           // maximum sensor value
float temp; // temperature from sensor

// LED Interval - it will represent Heating or Cooling intensity
int redLedInterval;
int blueLedInterval;
int tempInterval; // How frenquetly get temperature from sensor

//light on or off
// The intensity will be dealt by ambientValue
bool lightOn = false;



void setup() {
  Serial.begin(9600);
  pinMode(ledWPin, OUTPUT);
  pinMode(ledBPin, OUTPUT);
  pinMode(ledRPin, OUTPUT);
  ambientCalibration();

}
void getTemp() {
  char result[20];
  strcpy(result, "TEMPERATURE = ");
  temp = analogRead(tempPin);
  // read analog volt from sensor and save to variable temp
  temp = temp * 0.48828125;
  // convert the analog volt to its temperature equivalent Celsius
  strcat(result, (String(temp)+"*C").c_str());
  Serial.println(result);
} //function end

//timed Action
TimedAction getTempAction = TimedAction(1000,getTemp); 



void loop() {
  parseCommand(); // get command
  getTempAction.check(); // get current temperature from sensor
  if (lightOn) {
    getAmbientValue();
    analogWrite(ledWPin, ambientValue);
  } else {
    analogWrite(ledWPin, 0);
  }
}

/* function area
    Reqired functions that is used for setup and loop part will
    be attatched under here.
*/
/*Calibration for Ambient sensor*/
void ambientCalibration() {

  // turn on LED to signal the start of the calibration period:
  pinMode(13, OUTPUT);
  digitalWrite(13, HIGH);

  // calibrate during the first 10 seconds
  while (millis() < 10000) {
    ambientValue = invertAmbientPower(analogRead(ambientPin));

    // record the maximum sensor value
    if (ambientValue > ambientMax) {
      ambientMax = ambientValue;
    }

    // record the minimum sensor value
    if (ambientValue < ambientMin) {
      ambientMin = ambientValue;
    }

  }
  digitalWrite(13, LOW);
}

/*Get ambient Sensor value after calibration*/
double getAmbientValue() {
  // read the sensor:
  ambientValue = invertAmbientPower(analogRead(ambientPin));

  // apply the calibration to the sensor reading
  // map to 0~255 it will be input for LED analog power.
  ambientValue = map(ambientValue, ambientMin, ambientMax, 0, 255);

  ambientValue = constrain(ambientValue, 0, 255);
}

/* the stronger the sensor data, the weaker the out put power
*/

int invertAmbientPower(int x) {
  x = 1023 - x;
  return x;
}

/*
   Parse command
   Input structure 1:90&2:80&3:180(device ID:command&...)
   sample date = 1:90&2:80&3:180&4:1
*/
void parseCommand() {
  char input[INPUT_SIZE + 1];

  if (Serial.available() > 0) {
    char result[100];
    strcpy(result, "Result-");
    byte size = Serial.readBytes(input, INPUT_SIZE); // size of input
    input[size] = 0;
    char* command = strtok(input, "&"); //split into substring

    while (command != 0) {
      // Split the command in two values
      char* separator = strchr(command, ':'); //search character
      if (separator != 0)
      {
        // Actually split the string in 2: replace ':' with 0
        *separator = 0;
        int sensorId = atoi(command); // String to int
        ++separator;
        int value = atoi(separator);

        // Do something with sensorId and value
        //1 = Door open, 2=Heating, 3=Cooling, 4=Lighting
        if (sensorId == 1) {
          strcat(result, ("Door:" + String(value) + "&").c_str());
        } else if (sensorId == 2) {
          strcat(result, ("Heating:" + String(value) + "&").c_str());
        } else if (sensorId == 3) {
          strcat(result, ("Cooling:" + String(value) + "&").c_str());
        } else if (sensorId == 4) {
          if (value == 1) {
            strcat(result, ("Lighting:" + String(value) + "&").c_str());
            lightOn = true;
          } else if (value == 0) {
            lightOn = false;
          }
        } else if (sensorId == 0) {
          Serial.println("Ready");
        }
      }
      // Find the next command in input string
      command = strtok(0, "&");
    }// while end
    result[strlen(result) - 1] = '\0'; //delete last &
    Serial.println(result);
  }// if availabe end
} //function end





