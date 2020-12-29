#define INPUT_SIZE 40
#define deviceCount 4

// sensor number
const int tempPin = A0;
const int ambientPin = A1;
const int ledWPin = 9;
const int ledRPin = 10;
const int ledBPin = 11;

int ambientValue = 0;         // the sensor value
int ambientMin = 0;        // minimum sensor value
int ambientMax = 0;           // maximum sensor value

float sensorTemperature; // temperature from sensor
float userTemp; //desired temp from user
float recomTemp = 21; // recommanded Temp used in Auto mode

int tempLedState = LOW;

// Intervals
const long servoInterval = 300;
const long tempInterval = 2000;

// for white bulb intensity
int remainder = 0;

// variables for interval
unsigned long tempLedPrevMillis = 0;
unsigned long tempPrevMillis = 0;
//unsigned long redLedPrevMillis = 0;

//light on or off
bool lightOn = false;
bool doorOn = false;
bool autoTemp = false;
bool tempOn = false;
//vending machine
bool vndM1 = false;
bool vndM2 = false;
bool vndM3 = false;
bool vndM4 = false;




void setup() {
  Serial.begin(9600);
  pinMode(ledWPin, OUTPUT);
  pinMode(ledBPin, OUTPUT);
  pinMode(ledRPin, OUTPUT);
  ambientCalibration();
}

void loop() {
  unsigned long currentMillis = millis();
  if (currentMillis - tempPrevMillis >= tempInterval) {
    tempPrevMillis = currentMillis;
    getTemp();
  }
  if (tempOn) {
    runDigitalLed(currentMillis, userTemp);
  } else {
    digitalWrite(ledRPin, LOW);
    digitalWrite(ledBPin, LOW);
  }
  parseCommand(); // get command
  if (lightOn) {
    runWhiteLed(currentMillis);
  } else if (!lightOn) {
    digitalWrite(ledWPin , LOW);
  }
}

/* function area
    Reqired functions that is used for setup and loop part will
    be attatched under here.
*/
/*run devices with interval*/
void runDigitalLed(unsigned long currentMillis, float goalTemp) {
  int ledPin = 0;
  int interval = 0;

  // Cooling
  if (sensorTemperature - goalTemp > 0) {
    interval = 500 / (sensorTemperature - goalTemp);
    ledPin = ledBPin;
    digitalWrite(ledRPin, LOW);
    // Heating
  } else if (sensorTemperature - goalTemp < 0) {
    interval = 500 / (goalTemp - sensorTemperature);
    ledPin = ledRPin;
    digitalWrite(ledBPin, LOW);
  }


  if (currentMillis - tempLedPrevMillis >= interval) {
    tempLedPrevMillis = currentMillis;
    if (tempLedState == LOW) {
      tempLedState = HIGH;
    } else {
      tempLedState = LOW;
    }
    digitalWrite(ledPin, tempLedState);
  }

} // run digital LED finished.

/* get temperature*/
void getTemp() {
  sensorTemperature = analogRead(tempPin);
  sensorTemperature *= 0.48828125;
  Serial.println(String(sensorTemperature) + "*C");

}
/*Calibration for Ambient sensor*/
void ambientCalibration() {

  // turn on LED to signal the start of the calibration period:
  pinMode(13, OUTPUT);
  digitalWrite(13, HIGH);

  // calibrate during the first 10 seconds
  while (millis() < 10000) {
    ambientValue = invertAmbientPower(analogRead(ambientPin));
    Serial.println(ambientValue);
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
void runWhiteLed(unsigned long currentMillis) {

  // read the sensor:
  ambientValue = invertAmbientPower(analogRead(ambientPin));

  // apply the calibration to the sensor reading
  // map to 0~255 it will be input for LED analog power.
  ambientValue = map(ambientValue, ambientMin, ambientMax, 1, 10);

  ambientValue = constrain(ambientValue, 1, 10);

  remainder = currentMillis % ( ambientValue + 10);
  if (remainder < ambientValue) {
    digitalWrite(ledWPin , HIGH);
  } else {
    digitalWrite(ledWPin , LOW);
  }
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
   sample data = 1:30&2:15&3:1
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
        //1 = Door open, 2=temperature, 3=Lighting
        if (sensorId == 1) {
          strcat(result, ("Door:" + String(value) + "&").c_str());
        } else if (sensorId == 2) {
          strcat(result, ("Temperature:" + String(value) + "&").c_str());
          if (value == 0) {
            tempOn = false;
          } else if (value == 1) {
            tempOn = true;
            autoTemp = true;
          } else {
            tempOn = true;
            userTemp = value;
          }
        } else if (sensorId == 3) {
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

