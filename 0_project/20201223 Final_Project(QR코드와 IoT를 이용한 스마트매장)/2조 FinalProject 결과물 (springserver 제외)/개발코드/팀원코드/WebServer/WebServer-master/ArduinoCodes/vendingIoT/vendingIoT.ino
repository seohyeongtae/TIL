#include <Servo.h>
#define INPUT_SIZE 80 // input string size

Servo servo1;
Servo servo2;
Servo servo3;
Servo servo4;

//interval for each servo motor
int servo1Interval = 0;
int servo2Interval = 0;
int servo3Interval = 0;
int servo4Interval = 0;
// servo power
int servo1Power = 0;
int servo2Power = 0;
int servo3Power = 0;
int servo4Power = 0;

//pin number
const int servo1Pin = 9;
const int servo2Pin = 10;
const int servo3Pin = 11;
const int servo4Pin = 3;

//on off
boolean servo1On = false;
boolean servo2On = false;
boolean servo3On = false;
boolean servo4On = false;

unsigned long servo1LastSec = 0;
unsigned long servo2LastSec = 0;
unsigned long servo3LastSec = 0;
unsigned long servo4LastSec = 0;


void setup() {
  Serial.begin(9600);
  servo1.attach(servo1Pin);
  servo2.attach(servo2Pin);
  servo3.attach(servo3Pin);
  servo4.attach(servo4Pin);
}

void loop() {
  unsigned long currentMillis = millis();
  parseCommand();

  if (servo1On) {
    servo1.writeMicroseconds(servo1Power);
    servo1On = false;
    servo1LastSec = currentMillis;
  } else if (currentMillis - servo1LastSec >= servo1Interval) {
    servo1.write(90);
  }

  if (servo2On) {
    servo2.writeMicroseconds(servo2Power);
    servo2On = false;
    servo2LastSec = currentMillis;
  } else if (currentMillis - servo2LastSec >= servo2Interval) {
    servo2.write(90);
  }
  if (servo3On) {
    servo3.writeMicroseconds(servo3Power);
    servo3On = false;
    servo3LastSec = currentMillis;
  } else if (currentMillis - servo3LastSec >= servo3Interval) {
    servo3.write(90);
  }
  if (servo4On) {
    servo4.writeMicroseconds(servo4Power);
    servo4On = false;
    servo4LastSec = currentMillis;
  } else if (currentMillis - servo4LastSec >= servo4Interval) {
    servo4.write(90);
  }

}

void parseCommand() {
  char input[INPUT_SIZE + 1];

  if (Serial.available() > 0) {
    char result[150];
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
        float value = atof(separator);
        int sec = value * 1000;

        separator = strchr(separator + 1, ':');
        separator++;
        int power = atoi(separator);

        // Do something with sensorId and value
        //0 = onoff check, 1 = Door open, 2=temperature, 3=Lighting, 4=DotMatrix
        // 5 = servo1, 6 = servo2, 7=servo3, 8=servo4
        //example data 5:1.5:1000&6:2.5:2000&7:3.5:1000&8:4.5:1000
        if (sensorId == 5) {
          strcat(result, ("Motor1_Power:" + String(power) + "_" +
                          "Interval:" + String(sec) + "-").c_str());
          servo1Interval = sec;
          servo1Power = power;
          servo1On = true;
        } else if (sensorId == 6) {
          strcat(result, ("Motor2_Power:" + String(power) + "_" +
                          "Interval:" + String(sec) + "-").c_str());
          servo2Interval = sec;
          servo2Power = power;
          servo2On = true;
        } else if (sensorId == 7) {
          strcat(result, ("Motor3_Power:" + String(power) + "_" +
                          "Interval:" + String(sec) + "-").c_str());
          servo3Interval = sec;
          servo3Power = power;
          servo3On = true;
        } else if (sensorId == 8) {
          strcat(result, ("Motor4_Power:" + String(power) + "_" +
                          "Interval:" + String(sec) + "-").c_str());
          servo4Interval = sec;
          servo4Power = power;
          servo4On = true;
        }

      }
      // Find the next command in input string
      command = strtok(0, "&");
    }// while end
    result[strlen(result) - 1] = '\0'; //delete last &
    Serial.println(result);

  }// if availabe end
} //function end
