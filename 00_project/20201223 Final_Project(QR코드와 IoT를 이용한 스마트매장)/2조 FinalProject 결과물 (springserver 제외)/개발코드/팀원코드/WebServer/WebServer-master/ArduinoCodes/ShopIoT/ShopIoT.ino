#define INPUT_SIZE 40 // input string size
#include "LedControl.h" // Dot matrix library
#define dotCount 4 // number of dot matrix in a row
#define dotInterval 50
#include <Servo.h>

//declare servo object.
Servo servo;

// sensor pin number
const int tempPin = A0;
const int ambientPin = A1;
//Led pin
const int ledWPin = 9;
const int ledRPin = 10;
const int ledBPin = 11;
//servo pin
const int motor = A7;

//servo variables
int servoValue = 90; // input for servo motor

//ambient value which will be in turn used for white bulb intensity
int ambientValue = 0;         // the sensor value
int ambientMin = 0;        // minimum sensor value
int ambientMax = 0;           // maximum sensor value

//temperature variables
float sensorTemperature; // temperature from sensor
float userTemp; //desired temp from user
float recomTemp = 21; // recommanded Temp used in Auto mode

int tempLedState = LOW;

// Intervals
const long doorInterval = 1500;
const long tempInterval = 2000;

// for white bulb intensity
int remainder = 0;

// variables for interval
unsigned long tempLedPrevMillis = 0;
unsigned long tempPrevMillis = 0;
unsigned long doorMillis = 0;

//light on or off
bool lightOn = false;
bool doorOn = false;
bool autoTemp = false;
bool tempOn = false;
bool dotTurnRight = false;
bool dotTurnLeft = false;

//Delcare matrix objects
// DIN , CLK, CS
LedControl lc = LedControl(21, 22, 23, 4);
LedControl lc1 = LedControl(1, 2, 3, 4);

//set of matrix parameter
//left
int dotAddressLeft = 0;
int dotColLeft = 0;
int dotRowLeft = 7;
int arrowHeadStepLeft = 0;
unsigned long dotActMillisLeft = 0;
bool reachedTipEndLeft = false;
bool onOffDotMatrixLeft = true;
int leftDotMatrixCycle = 0;
//right
int dotAddressRight = 0;
int dotColRight = 7;
int dotRowRight = 7;
int arrowHeadStepRight = 0;
unsigned long dotActMillisRight = 0;
bool reachedTipEndRight = false;
bool onOffDotMatrixRight = true;
int rightDotMatrixCycle = 0;


void setup() {
  Serial.begin(9600);
  pinMode(ledWPin, OUTPUT);
  pinMode(ledBPin, OUTPUT);
  pinMode(ledRPin, OUTPUT);
  servo.attach(motor);
  initDotMatrix();
  ambientCalibration();
}

void loop() {
  //elapsed time
  unsigned long currentMillis = millis();

  // get commands from serial port
  parseCommand(); // get command

  //Door control
  if (doorOn) {
    servo.writeMicroseconds(servoValue);
    doorOn = false;
    doorMillis = currentMillis;
  } else if ((!doorOn)&&(currentMillis - doorMillis >= doorInterval)) {
    servo.write(90);
  }

  //check temperature and send
  if (currentMillis - tempPrevMillis >= tempInterval) {
    tempPrevMillis = currentMillis;
    getTemp();
  }

  //turn led on off which represents Heating / Cooling
  if (tempOn) {
    runDigitalLed(currentMillis, userTemp);
  } else if (!tempOn) {
    digitalWrite(ledRPin, LOW);
    digitalWrite(ledBPin, LOW);
  }

  // turn light on or off, the intensity is determined by sensor value
  if (lightOn) {
    runWhiteLed(currentMillis);
  } else if (!lightOn) {
    digitalWrite(ledWPin , LOW);
  }

  //dot matrix part
  if (dotTurnLeft) {
    //turn on turn left Dot Matrix
    if (leftDotMatrixCycle < 6) {
      //on off 3 times each
      if (leftDotMatrixCycle % 2 == 0) {
        dotMatrixOnOffLeft(true, currentMillis);
      } else {
        dotMatrixOnOffLeft(false, currentMillis);
      }
    } else {
      //initialize paramter
      dotTurnLeft = false;
      leftDotMatrixCycle = 0;
    }
  } else if (dotTurnRight) {
    //turn on turn left Dot Matrix
    if (rightDotMatrixCycle < 6) {
      //on off 3 times each
      if (rightDotMatrixCycle % 2 == 0) {
        dotMatrixOnOffRight(true, currentMillis);
      } else {
        dotMatrixOnOffRight(false, currentMillis);
      }
    } else {
      //initialize parameter
      dotTurnRight = false;
      rightDotMatrixCycle = 0;
    }
  }// dot Matrix finished
}

/* function area
    Reqired functions that is used for setup and loop part will
    be attatched under here.
*/

//initialize dot matrix
void initDotMatrix() {
  for (int dotAddressLeft = 0; dotAddressLeft < dotCount; dotAddressLeft++) {
    /*The MAX72XX is in power-saving mode on startup*/
    lc.shutdown(dotAddressLeft, false);
    /* Set the brightness to a medium values */
    lc.setIntensity(dotAddressLeft, 8);
    /* and clear the display */
    lc.clearDisplay(dotAddressLeft);
    lc1.shutdown(dotAddressLeft, false);
    lc1.setIntensity(dotAddressLeft, 8);
    lc1.clearDisplay(dotAddressLeft);
  }
}

//on or off left dot LED
void dotMatrixOnOffLeft(bool modeOnOff, unsigned long currentMillis) {
  //Run only switch for DotMatrix is on.
  if (onOffDotMatrixLeft) {
    //Check interval.
    if (currentMillis - dotActMillisLeft >= dotInterval ) {
      //record last activated time
      dotActMillisLeft = currentMillis;

      // Run Only when it is needed
      if (dotAddressLeft < dotCount) {
        // on #1~3
        if (dotAddressLeft != dotCount - 1) {
          if (dotRowLeft >= 0) {
            // turn led one by one in vertical direction
            lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft, modeOnOff);
            dotRowLeft --;
            if (dotRowLeft == -1) {
              //move on to next matrix
              dotAddressLeft++;
              // Initialize the number
              dotRowLeft = 7;
            }
          }
        } else { //something to do on last matrix
          //before drawing arrow head
          if (!reachedTipEndLeft) {
            //draw vertical
            if (dotRowLeft > 2) {
              lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft, modeOnOff);
              dotRowLeft--;
            } else {
              //draw horizontal
              if (dotColLeft < 7) {
                lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft, modeOnOff);
                dotColLeft ++;
              } else if (dotColLeft == 7) {
                // reached the end of L shape
                lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft, modeOnOff);
                reachedTipEndLeft = true;
              }
            } // horizontal draw finished
          } else {
            //draw arrow head
            // draw head.
            arrowHeadStepLeft++;
            dotColLeft--;
            lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft + arrowHeadStepLeft, modeOnOff);
            lc.setLed(dotAddressLeft, dotColLeft, dotRowLeft - arrowHeadStepLeft, modeOnOff);
            if (arrowHeadStepLeft == 2) {

              leftDotMatrixCycle++;
              dotAddressLeft = 0;
              dotColLeft = 0;
              dotRowLeft = 7;
              arrowHeadStepLeft = 0;
              reachedTipEndLeft = false;
              onOffDotMatrixLeft = true;
            }
          }// draw head finished
        } // last matrix finished
      }
    }//interval finished
  } // onOffDotMatrixLeft Finished
}

//on or off right dot LED
void dotMatrixOnOffRight(bool modeOnOff, unsigned long currentMillis) {
  //Run only switch for DotMatrix is on.
  if (onOffDotMatrixLeft) {
    //Check interval.
    if (currentMillis - dotActMillisRight >= dotInterval ) {
      //record last activated time
      dotActMillisRight = currentMillis;

      // Run Only when it is needed
      if (dotAddressRight < dotCount) {
        // on #1~3
        if (dotAddressRight != dotCount - 1) {
          if (dotRowRight >= 0) {
            // turn led one by one in vertical direction
            lc1.setLed(dotAddressRight, dotColRight, dotRowRight, modeOnOff);
            dotRowRight --;
            if (dotRowRight == -1) {
              //move on to next matrix
              dotAddressRight++;
              // Initialize the number
              dotRowRight = 7;
            }
          }
        } else { //something to do on last matrix
          //Draw horiozontal on last matrix
          if (!reachedTipEndRight) {
            //draw vertical
            if (dotRowRight > 2) {
              lc1.setLed(dotAddressRight, dotColRight, dotRowRight, modeOnOff);
              dotRowRight--;
            } else {
              //draw horizontal
              if (dotColRight > 0) {
                lc1.setLed(dotAddressRight, dotColRight, dotRowRight, modeOnOff);
                dotColRight--;
              } else if (dotColRight == 0) {
                // reached the end of L shape
                lc1.setLed(dotAddressRight, dotColRight, dotRowRight, modeOnOff);
                reachedTipEndRight = true;
              }
            } // horizontal draw finished
          } else {
            //draw arrow head
            // draw head.
            arrowHeadStepRight++;
            dotColRight++;
            lc1.setLed(dotAddressRight, dotColRight, dotRowRight + arrowHeadStepRight, modeOnOff);
            lc1.setLed(dotAddressRight, dotColRight, dotRowRight - arrowHeadStepRight, modeOnOff);
            if (arrowHeadStepRight == 2) {
              //when drawing head is finished, initialize
              rightDotMatrixCycle++;
              dotAddressRight = 0;
              dotColRight = 7;
              dotRowRight = 7;
              arrowHeadStepRight = 0;
              reachedTipEndRight = false;
              onOffDotMatrixRight = true;
            }
          }// draw head finished
        } // last matrix finished
      }
    }//interval finished
  } // onOffDotMatrixLeft Finished
}//function finished

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
   sample data = 1:1900&2:21&3:1&4:1
   to off 1:1150&2:15&3:0&4:0
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
        //0 = onoff check, 1 = Door open, 2=temperature, 3=Lighting, 4=DotMatrix
        if (sensorId == 1) {
          if (value > 1500) {
            strcat(result, "Door:Opens&");
          } else {
            strcat(result, "Door:Closes&");
          }
          servoValue = value;
          doorOn = true;
        } else if (sensorId == 2) {
          strcat(result, ("Temperature:" + String(value) + "&").c_str());
          if (value == 0) {
            tempOn = false;
          } else if (value == 1) {
            tempOn = true;
            autoTemp = true;
          } else {
            // if the input is just number, turn on air conditioning
            tempOn = true;
            userTemp = value;
          }
        } else if (sensorId == 3) {
          if (value == 1) {
            strcat(result, "Lighting:On&");
            lightOn = true;
          } else if (value == 0) {
            strcat(result, "Lighting:Off&");
            lightOn = false;
          }
        } else if (sensorId == 0) {
          //on off check
          Serial.println("Ready");
        } else if (sensorId == 4) {
          // Dot matrix
          if (value == 0) {
            //turn left
            dotTurnLeft = true;
            strcat(result, "DotMatrix:Left&");
          } else if (value == 1) {
            //turn right
            dotTurnRight = true;
            strcat(result, "DotMatrix:Right&");
          }
        }
      }
      // Find the next command in input string
      command = strtok(0, "&");
    }// while end
    result[strlen(result) - 1] = '\0'; //delete last &
    Serial.println(result);
  }// if availabe end
} //function end

