#include <Servo.h>
#define INPUT_SIZE 40 // input string size

//Declare servo object, in this case only one
Servo servo;
const int motor = A7; // pin number
int servoValue = 90;
// Intervals
const long doorInterval = 1500;
unsigned long doorMillis = 0;
bool doorOn = false;

void setup() {
  // put your setup code here, to run once:
  servo.attach(motor);
  Serial.begin(9600);
}

void loop() {
  unsigned long currentMillis = millis();
  // put your main code here, to run repeatedly:
  // 1:1900 -> -90(anti)
  // 1:1150 -> +90(clock)
  parseCommand();
  // value 1500 = stop, 1000 full-clock, 2000 full-anticlock
  if (doorOn) {
    servo.writeMicroseconds(servoValue);
    doorOn = false;
    doorMillis = currentMillis;
  } else if (currentMillis - doorMillis >=doorInterval){
    servo.write(90);
  }
  
  

}

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
      if (separator != 0) {
        // Actually split the string in 2: replace ':' with 0
        *separator = 0;
        int sensorId = atoi(command); // String to int
        ++separator;
        int value = atoi(separator);

        // Do something with sensorId and value
        //0 = onoff check, 1 = Door open, 2=temperature, 3=Lighting, 4=DotMatrix
        if (sensorId == 1) {
          strcat(result, ("Door:" + String(value) + "&").c_str());
          servoValue = value;
          doorOn = true;
        }

      }
      // Find the next command in input string
      command = strtok(0, "&");
    }// while end
    result[strlen(result) - 1] = '\0'; //delete last &
    Serial.println(result);
  }// if availabe end
} //function end
