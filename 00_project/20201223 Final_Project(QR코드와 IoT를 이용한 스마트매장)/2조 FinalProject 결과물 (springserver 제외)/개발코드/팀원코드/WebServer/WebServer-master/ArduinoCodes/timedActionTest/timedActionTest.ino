#include <TimedAction.h>

const int tempPin = A0;
float temp; // temperature from sensor

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

TimedAction getTempAction = TimedAction(1000,getTemp); 

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  getTempAction.check();

}
